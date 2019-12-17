package com.example.salestracker;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {


    private String[] keywords;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        final EditText SearchPlain = findViewById(R.id.searchPlain);
        final Button SearchBtn = findViewById( R.id.searchBtn );
        final TextView test = findViewById(R.id.textTest);


        SearchBtn.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String keywordsInput = SearchPlain.getText().toString();
                keywords = keywordsInput.split( " ");

                for(int i = 0; i < keywords.length; i ++){
                    test.append(keywords[i] + " ");
                }
            }
        } );
    }
}
