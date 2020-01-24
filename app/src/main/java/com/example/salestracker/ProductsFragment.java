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

import java.lang.ref.WeakReference;
import java.util.ArrayList;

public class ProductsFragment extends Fragment {

    private ArrayList<GsonProduct.item> itemList =  new ArrayList<>();
    private static WeakReference<Activity> mActivityRef;
    private static ListView listProducts;

    public static void updateActivity(Activity activity){
        mActivityRef = new WeakReference<>(activity);
    }

    public ProductsFragment() {

    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate( R.layout.activity_request, container, false );


        if (getArguments() != null) {
            itemList = getArguments().getParcelableArrayList("Json");
        }
        Log.d("Dennis", String.valueOf(itemList) );


        if(itemList.size() != 0) {
            listProducts = view.findViewById( R.id.productsList );
            final SearchResultsAdapter myAdapter = new SearchResultsAdapter( mActivityRef.get(), R.layout.custom_row, itemList );
            listProducts.setAdapter( myAdapter );
            listProducts.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    new PopupClass().showPopup(itemList, view, position, myAdapter);
                }
            });
            registerForContextMenu(listProducts);
        }else{
            TextView titleResult = view.findViewById( R.id.titleResult);
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
        FavsProduct fp = new FavsProduct();
        fp.setUserId(MainActivity.loggedInUser.getId());
        fp.setProductId(itemList.get(info.position).getItemId().get(0));
        fp.setProductTitle(itemList.get(info.position).getTitle(0));
        fp.setProductSeller(itemList.get(info.position).getSellerInfo().get(0).getSellerUsername(0));
        fp.setProductPrice(itemList.get(info.position).getSellingStatus().get(0).getPriceDetails().get(0).get__value__());
        fp.setProductUrl(itemList.get(info.position).getGalleryURL(0));
        fp.setCondition(itemList.get(info.position).getCondition().get(0).getConditionDisplayName(0));
        fp.setMinPrice(SearchFragment.min);
        fp.setMaxPrice(SearchFragment.max);
        fp.setSearchKeyword(SearchFragment.keywords);
        fp.setFreeShipping(itemList.get(info.position).getShippingInfo().get(0).getShippingType(0).equals("Free"));

        switch(item.getItemId()) {
            case R.id.context_add:
                if(((CheckBox)listProducts.getChildAt(info.position).findViewById(R.id.favsCheckBox)).isChecked()) {
                    Snackbar.make(listProducts, "This product is already in your favourites list",
                            Snackbar.LENGTH_LONG).show();
                }
                else {
                    dbHelper.addProductToFavs(fp);
                    MainActivity.favourites.add(fp);
                }
                break;
            case R.id.context_delete:
                if(((CheckBox)listProducts.getChildAt(info.position).findViewById(R.id.favsCheckBox)).isChecked()) {
                    dbHelper.deleteProductFromFavs(fp);
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
