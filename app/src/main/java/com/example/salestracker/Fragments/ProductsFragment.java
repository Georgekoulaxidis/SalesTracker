package com.example.salestracker.Fragments;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.example.salestracker.FetchProductsTask;
import com.example.salestracker.GsonParsing.GsonProduct;
import com.example.salestracker.MainActivity;
import com.example.salestracker.PopupClass;
import com.example.salestracker.R;
import com.example.salestracker.Adapter.SearchResultsAdapter;
import com.example.salestracker.db.DatabaseHelper;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

public class ProductsFragment extends Fragment {

    private ArrayList<GsonProduct.item> itemList =  new ArrayList<>();
    private TextView titleResult;
    private static ListView listProducts;
    private String pageNumber = "1";
    private String numberOfPages;
    private SearchResultsAdapter myAdapter;

    private View footerView;
    private LinearLayout ll;
    private Button[] btns;

    public ProductsFragment(String numberOfPages) {
        this.numberOfPages = numberOfPages;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate( R.layout.activity_request, container, false );


        if (getArguments() != null) {
            itemList = getArguments().getParcelableArrayList("Json");
        }
        Log.d("Dennis", String.valueOf(itemList) );

        titleResult = view.findViewById( R.id.titleResult);
        titleResult.setText("Products Result(Page " + pageNumber + " of " + numberOfPages + ")");

        listProducts = view.findViewById( R.id.productsList );
        myAdapter = new SearchResultsAdapter( getActivity(), R.layout.custom_row, itemList );
        myAdapter.setFavs(false);
        listProducts.setAdapter( myAdapter );
        listProducts.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    new PopupClass().showPopup(itemList, view, position, myAdapter); }
            });
        registerForContextMenu(listProducts);

        footerView = ((LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.listview_footer_layout, null, false);
        ll = footerView.findViewById(R.id.btnLay);
        btns = new Button[Integer.parseInt(numberOfPages)];

        btns[0] = new Button(getActivity());
        btns[0].setBackgroundResource(R.drawable.round_button);
        btns[0].setText(""+1);
        ll.addView(btns[0]);
        btns[0].setOnClickListener(new View.OnClickListener() {

            public void onClick(View v)
            {
                loadListOfProducts(1);
                for(int k = 0; k < btns.length; k++) {
                    if (btns[k] != null) {
                        if (k == 0)
                            btns[k].setPressed(true);
                        else
                            btns[k].setPressed(false);
                    }
                    else {
                        break;
                    }
                }
            }
        });
        btns[0].setPressed(true);

        footerView = ConstructFooterView();
        listProducts.addFooterView(footerView);

        return view;
    }

    private View ConstructFooterView() {
        for(int i = Integer.parseInt(pageNumber); i < ((Integer.parseInt(pageNumber)+5>=Integer.parseInt(numberOfPages))?
                Integer.parseInt(numberOfPages) : 5+Integer.parseInt(pageNumber)); i++) {
            btns[i] = new Button(getActivity());
            btns[i].setBackgroundResource(R.drawable.round_button);
            btns[i].setText(""+(i+1));

            final int j = i;
            btns[j].setOnClickListener(new View.OnClickListener() {

                public void onClick(View v)
                {
                    loadListOfProducts(j+1);
                    for(int k = 0; k < btns.length; k++) {
                        if (btns[k] != null) {
                            if (k == j)
                                btns[k].setPressed(true);
                            else
                                btns[k].setPressed(false);
                        }
                        else {
                            break;
                        }
                    }
                }
            });

            ll.addView(btns[i]);

            if(pageNumber.equals("1"))
                ll.removeView(btns[5]);

        }

        return footerView;
    }

    private void loadListOfProducts(int pageNumber) {
        FetchProductsTask task = new FetchProductsTask(getActivity(), FetchProductsTask.keyword, FetchProductsTask.newProduct,
                FetchProductsTask.usedProduct, Boolean.parseBoolean(FetchProductsTask.freeShipping), FetchProductsTask.min, FetchProductsTask.max,
                FetchProductsTask.country, pageNumber);
        try {
            itemList = (ArrayList<GsonProduct.item>) task.execute().get();
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }

        myAdapter = new SearchResultsAdapter(getActivity(), R.layout.custom_row, itemList);
        listProducts.setAdapter(myAdapter);

        this.pageNumber = String.valueOf(pageNumber);
        titleResult.setText("Products Result(Page " + pageNumber + " of " + numberOfPages + ")");
        if (pageNumber % 5 == 0) {
            listProducts.removeFooterView(footerView);
            listProducts.addFooterView(ConstructFooterView());
        }
    }

    @Override
    public void onCreateContextMenu(@NonNull ContextMenu menu, @NonNull View v, @Nullable ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);

        getActivity().getMenuInflater().inflate(R.menu.context_menu, menu);
        menu.setHeaderTitle("Choose an option");
    }

    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        DatabaseHelper dbHelper = new DatabaseHelper(getActivity().getBaseContext());
        GsonProduct.item fp = itemList.get(info.position);

        switch(item.getItemId()) {
            case R.id.context_add:
                if(((CheckBox)listProducts.getChildAt(info.position).findViewById(R.id.favsCheckBox)).isChecked()) {
                    Snackbar.make(listProducts, "This product is already in your favourites list",
                            Snackbar.LENGTH_LONG).show();
                }
                else {
                    dbHelper.addProductToFavs(MainActivity.loggedInUser.getId(), fp);
                    MainActivity.favourites.add(fp);
                }
                break;
            case R.id.context_delete:
                if(((CheckBox)listProducts.getChildAt(info.position).findViewById(R.id.favsCheckBox)).isChecked()) {
                    dbHelper.deleteProductFromFavs(MainActivity.loggedInUser.getId(), fp.getItemId().get(0));
                    MainActivity.favourites.remove(fp);
                }
                else {
                    Snackbar.make(listProducts,"This product is not in your favourites list",
                            Snackbar.LENGTH_LONG).show();
                }
                break;
        }
        myAdapter.notifyDataSetChanged();
        return super.onContextItemSelected(item);
    }
}
