package com.example.salestracker;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;

public class MainActivity extends AppCompatActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        final EditText SearchPlain = findViewById(R.id.searchPlain);
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
        } );

    }
}
