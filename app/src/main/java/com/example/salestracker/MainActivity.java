package com.example.salestracker;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.material.navigation.NavigationView;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private DrawerLayout drawer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_toolbar);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        drawer = findViewById(R.id.draw_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);


        ActionBarDrawerToggle toogle = new ActionBarDrawerToggle(this, drawer, toolbar,
                R.string.navigation_drawer_open, R.string.navigation_drawer_close);

        drawer.addDrawerListener( toogle );
        toogle.syncState();



        //When we rotate the device we will not return back to the first fragment.
        if(savedInstanceState == null) {
            //Open the search layout immediately when the app starts.
            getSupportFragmentManager().beginTransaction().replace( R.id.fragment_container,
                    new SearchFragment() ).commit();
            navigationView.setCheckedItem( R.id.nav_search );
        }


        /*final EditText SearchPlain = findViewById(R.id.searchPlain);
        final Button SearchBtn = findViewById( R.id.searchBtn );
        final CheckBox newBox = findViewById(R.id.newBox);
        final CheckBox usedBox = findViewById(R.id.usedBox);
        final CheckBox freeShippingBox = findViewById(R.id.freeShippingBox);
        final CheckBox paymentBox = findViewById(R.id.paymentBox);
        final Spinner currencySpinner = findViewById(R.id.currenciesSpinner );
        final EditText minPlain = findViewById(R.id.minPlain);
        final EditText maxPlain = findViewById(R.id.maxPlain);



        SearchBtn.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String keywordsInput = SearchPlain.getText().toString(); //Take input from user
                boolean newProduct = newBox.isChecked();
                Log.d("Dennis", String.valueOf(newProduct));
                boolean usedProduct = usedBox.isChecked();
                boolean freeShipping = freeShippingBox.isChecked();
                boolean payment = paymentBox.isChecked();
                String min = minPlain.getText().toString();
                String max = maxPlain.getText().toString();
                String currency = (String) currencySpinner.getSelectedItem();


                //Transfer user's input to the RequestActivity
                Intent intent = new Intent( MainActivity.this, RequestActivity.class);
                intent.putExtra("keywords", keywordsInput);
                intent.putExtra("new", newProduct);
                intent.putExtra("used", usedProduct);
                intent.putExtra("freeShipping", freeShipping);
                intent.putExtra("payment", payment);
                intent.putExtra("min", min);
                intent.putExtra("max", max);
                intent.putExtra("currency", currency);

                startActivity(intent);
            }
        } );*/

    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        switch (menuItem.getItemId()){
            case R.id.nav_search:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new SearchFragment()).commit();
                break;
            case R.id.nav_favourites:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new FavouritesFragment()).commit();
                break;
        }

        drawer.closeDrawer(GravityCompat.START);

        return true;
    }

    /*When back button is pressed the activity doesn't close immediately,
    * instead if the navigation is open it close's first. */
    @Override
    public void onBackPressed() {
        if(drawer.isDrawerOpen(GravityCompat.START )){
            drawer.closeDrawer(GravityCompat.START);
        }
        else{
            super.onBackPressed();
        }

    }

    public void ReceiveJson(GsonProduct item){
        //Intent incIntent = intent;
        //GsonProduct item = (GsonProduct) incIntent.getSerializableExtra("item");

        Bundle bundle = new Bundle();
        if(item !=null)
            bundle.putString("json", item.getFindItemsByKeywordsResponse().get(0).getSearchResult().get(0).getItem().get(0).getTitle( 0 ));

        Log.d("Dennis", "ReceiveJson accessed successfully");

        ProductsFragment fragObj = new ProductsFragment();
        fragObj.setArguments(bundle);
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                fragObj).commit();
    }
}
