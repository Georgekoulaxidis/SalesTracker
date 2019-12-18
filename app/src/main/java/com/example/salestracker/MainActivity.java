package com.example.salestracker;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        final EditText SearchPlain = findViewById(R.id.searchPlain);
        final Button SearchBtn = findViewById( R.id.searchBtn );
        final TextView test = findViewById(R.id.textTest); //Just a view for testing


        SearchBtn.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String keywordsInput = SearchPlain.getText().toString(); //Take input from user


                //Transfer user's input to the RequestActivity
                Intent intent = new Intent( MainActivity.this, RequestEbay.class);
                intent.putExtra("keywords", keywordsInput);

                startActivity(intent);
            }
        } );


    }
}
