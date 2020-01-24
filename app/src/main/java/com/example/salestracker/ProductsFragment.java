package com.example.salestracker;

import android.app.Activity;
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
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.TextView;

import com.google.android.material.snackbar.Snackbar;
import com.google.gson.Gson;

import java.lang.ref.WeakReference;
import java.util.ArrayList;

public class ProductsFragment extends Fragment {

    private ArrayList<GsonProduct.item> itemList =  new ArrayList<>();
    private static ListView listProducts;

    public ProductsFragment() {

    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate( R.layout.activity_request, container, false );


        if (getArguments() != null) {
            itemList = getArguments().getParcelableArrayList("Json");
        }
        Log.d("Dennis", String.valueOf(itemList) );

        Gson gson = new Gson();
        String json = gson.toJson(itemList.get(0));
        System.out.println("json = " + json);
        GsonProduct.item product = gson.fromJson(json, GsonProduct.item.class);
        System.out.println(product.getItemId().get(0));

        TextView titleResult = view.findViewById( R.id.titleResult);
        if(itemList.size() != 0) {
            titleResult.setText("Products Result");

            listProducts = view.findViewById( R.id.productsList );
            final SearchResultsAdapter myAdapter = new SearchResultsAdapter( getActivity(), R.layout.custom_row, itemList );
            myAdapter.setFavs(false);
            listProducts.setAdapter( myAdapter );
            listProducts.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    new PopupClass().showPopup(itemList, view, position, myAdapter);
                }
            });
            registerForContextMenu(listProducts);
        }else{
            titleResult.setText("We didn't find any product");
        }

        return view;
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
        return super.onContextItemSelected(item);
    }
}
