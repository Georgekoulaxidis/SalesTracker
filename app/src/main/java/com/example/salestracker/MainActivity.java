package com.example.salestracker;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;

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

    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        switch (menuItem.getItemId()){
            case R.id.nav_search:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new SearchFragment()).addToBackStack(null).commit();
                break;
            case R.id.nav_favourites:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new FavouritesFragment()).addToBackStack(null).commit();
                break;
            case R.id.nav_logout:
                SharedPreferences sp = getSharedPreferences(LoginActivity.myPreferences, Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sp.edit();
                editor.clear();
                editor.commit();

                Intent intent  = new Intent(MainActivity.this, LoginActivity.class);
                intent.putExtra("backDisabled", true);
                startActivity(intent);
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

    public void ReceiveJson(ArrayList<GsonProduct.item> itemList){
        //Intent incIntent = intent;
        //GsonProduct item = (GsonProduct) incIntent.getSerializableExtra("item");
        Log.v("Dennis", String.valueOf(itemList));
        Bundle bundle = new Bundle();
        ProductsFragment.updateActivity(this);
        if(itemList != null)
            bundle.putParcelableArrayList("Json", itemList);

        Log.d("Dennis", "ReceiveJson accessed successfully");

        ProductsFragment fragObj = new ProductsFragment();
        fragObj.setArguments(bundle);
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                fragObj).addToBackStack(null).commit();
    }


}
