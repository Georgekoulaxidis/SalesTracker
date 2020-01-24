package com.example.salestracker;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private DrawerLayout drawer;

    private DatabaseHelper dbHelper;
    public static User loggedInUser;
    //public static List<FavsProduct> favourites;
    public static List<GsonProduct.item> favourites;

    public static Context getContext() {
        return context;
    }

    private static Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_toolbar);

        loggedInUser = ((User)getIntent().getSerializableExtra("user"));
        loggedInUser.setImage(LoginActivity.userImage);
        dbHelper = new DatabaseHelper(MainActivity.this);
        favourites = dbHelper.getFavs(loggedInUser.getId());

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        drawer = findViewById(R.id.draw_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        loadDrawersContent(navigationView);
        navigationView.setNavigationItemSelectedListener(this);


        ActionBarDrawerToggle toogle = new ActionBarDrawerToggle(this, drawer, toolbar,
                R.string.navigation_drawer_open, R.string.navigation_drawer_close) {
            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                NavigationView navigationView = findViewById(R.id.nav_view);
                loadDrawersContent(navigationView);
            }
        };
        drawer.addDrawerListener( toogle );
        toogle.syncState();


        String favouriteFragment = getIntent().getStringExtra("favourite");
        Log.v("MainActivity", String.valueOf(favouriteFragment));


        //When we rotate the device we will not return back to the first fragment.
        if(savedInstanceState == null && favouriteFragment == null) {
            //Open the search layout immediately when the app starts.
            getSupportFragmentManager().beginTransaction().replace( R.id.fragment_container,
                    new SearchFragment() ).commit();
            navigationView.setCheckedItem( R.id.nav_search );
        }
        else{
            if(favouriteFragment != null){
                if(favouriteFragment.equals("favouriteMenu")){
                    getSupportFragmentManager().beginTransaction().replace( R.id.fragment_container,
                            new FavouritesFragment()).commit();
                    navigationView.setCheckedItem( R.id.nav_favourites );
                }
                else if(favouriteFragment.equals("popUp")){
                    getSupportFragmentManager().beginTransaction().replace( R.id.fragment_container,
                            new FavouritesFragment()).commit();
                    navigationView.setCheckedItem( R.id.nav_favourites );
                }
            }
        }

    }

    private void loadDrawersContent (NavigationView navigationView) {
        LinearLayout headerView = (LinearLayout) navigationView.getHeaderView(0);
        ImageView usrImgView = headerView.findViewById(R.id.usrImgView);

        usrImgView.setImageBitmap(BitmapFactory.decodeByteArray(MainActivity
                .loggedInUser.getImage(), 0, MainActivity.loggedInUser.getImage().length));
        TextView userName = headerView.findViewById(R.id.userNameTxt);
        TextView userEmail = headerView.findViewById(R.id.userEmailTxt);
        userName.setText(loggedInUser.getName());
        userEmail.setText(loggedInUser.getEmail());
    }

    @Override
    public void onOptionsMenuClosed(Menu menu) {
        super.onOptionsMenuClosed(menu);


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
            case R.id.nav_about:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new AboutFragment()).addToBackStack(null).commit();
                break;
            case R.id.nav_details:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new UserDetailsFragment()).addToBackStack(null).commit();
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

    @Override
    protected void onNewIntent(Intent intent){
        super.onNewIntent(intent);
        setIntent(intent);
    }

    @Override
    protected void onResume(){
        super.onResume();


        String favouriteFragment = getIntent().getStringExtra("favourite");
        Log.v("Dennis", String.valueOf(favouriteFragment));

        if(favouriteFragment != null){
            if(favouriteFragment.equals("favouriteMenu")){
                getSupportFragmentManager().beginTransaction().replace( R.id.fragment_container,
                        new FavouritesFragment()).commit();

            }
        }


    }

    public void ReceiveJson(ArrayList<GsonProduct.item> itemList){
        Log.v("Dennis", String.valueOf(itemList));
        Bundle bundle = new Bundle();
        if(itemList != null)
            bundle.putParcelableArrayList("Json", itemList);

        Log.d("Dennis", "ReceiveJson accessed successfully");

        ProductsFragment fragObj = new ProductsFragment();
        fragObj.setArguments(bundle);
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                fragObj).addToBackStack(null).commit();
    }


}
