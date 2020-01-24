package com.example.salestracker;

import android.os.Bundle;
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

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.material.snackbar.Snackbar;
import com.google.gson.Gson;

public class FavouritesFragment extends Fragment {

    private SearchResultsAdapter myAdapter;
    private ListView listFavouriteProducts;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate( R.layout.activity_request, container, false );

        TextView titleResult = view.findViewById( R.id.titleResult);
        if(MainActivity.favourites.size() != 0) {
            titleResult.setText("Favourites");

            listFavouriteProducts = view.findViewById( R.id.productsList );
            myAdapter = new SearchResultsAdapter( getActivity(), R.layout.custom_row, MainActivity.favourites );
            myAdapter.setFavs(true);
            listFavouriteProducts.setAdapter( myAdapter );
            listFavouriteProducts.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    new PopupClass().showPopup(MainActivity.favourites, view, position, myAdapter);
                }
            });
            registerForContextMenu(listFavouriteProducts);
        }else{
            titleResult.setText("No products are added to your favourites yet");
        }

        return view;
    }

    @Override
    public void onCreateContextMenu(@NonNull ContextMenu menu, @NonNull View v, @Nullable ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);

        getActivity().getMenuInflater().inflate(R.menu.context_menu, menu);
        menu.setHeaderTitle("Choose an option");
        menu.findItem(R.id.context_add).setEnabled(false);
    }

    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        DatabaseHelper dbHelper = new DatabaseHelper(getActivity().getBaseContext());
        GsonProduct.item fp = MainActivity.favourites.get(info.position);

        dbHelper.deleteProductFromFavs(MainActivity.loggedInUser.getId(), fp.getItemId().get(0));
        MainActivity.favourites.remove(fp);

        myAdapter.notifyDataSetChanged();

        Snackbar.make(listFavouriteProducts,"Product deleted successfully",
                            Snackbar.LENGTH_LONG).show();

        return super.onContextItemSelected(item);
    }
}
