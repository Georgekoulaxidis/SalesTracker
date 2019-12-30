package com.example.salestracker;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    private LinearLayout rootLayout;
    private TextInputLayout textInputLayoutEmail;
    private TextInputLayout textInputLayoutPassword;
    private TextInputEditText textInputEditTextEmail;
    private TextInputEditText textInputEditTextPassword;
    private Button loginBtn;
    private TextView registerTextView;

    private DatabaseHelper dbHelper;
    private InputValidator validator;
    private User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        rootLayout = findViewById(R.id.rootLayout);
        textInputLayoutEmail = findViewById(R.id.txtInputLayoutEmail);
        textInputLayoutPassword = findViewById(R.id.txtInputLayoutPassword);
        textInputEditTextEmail = findViewById(R.id.txtInputEditTextEmail);
        textInputEditTextPassword = findViewById(R.id.txtInputEditTextPassword);
        loginBtn = findViewById(R.id.loginBtn);
        loginBtn.setOnClickListener(this);
        registerTextView = findViewById(R.id.linkRegisterTextView);
        registerTextView.setOnClickListener(this);



    }

    @Override
    public void onClick(View v) {
        switch(v.getId()) {
            case R.id.loginBtn:
                Log.d("dennis", "Login button clicked");
                dbHelper = new DatabaseHelper(LoginActivity.this);
                validator = new InputValidator(LoginActivity.this);
                user = new User();

                if(validator.isInputEditTextFilled(textInputEditTextEmail, textInputLayoutEmail, "Entering email is mandatory") &&
                        validator.isInputEditTextFilled(textInputEditTextPassword, textInputLayoutPassword, "Entering password is mandatory") &&
                        validator.isInputEditTextEmail(textInputEditTextEmail, textInputLayoutEmail, "Enter a valid email address")) {

                    String email = textInputEditTextEmail.getText().toString();
                    String password = textInputEditTextPassword.getText().toString();
                    if(dbHelper.checkUser(email, password)) {
                        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                        emptyInputEditText();
                        intent.putExtra("user", user);
                        startActivity(intent);
                    }
                    else {
                        Log.d("dennis", "Login failed");
                        Snackbar.make(rootLayout, getString(R.string.login_failed), Snackbar.LENGTH_LONG).show();
                    }

                    /*String[] selectionArgs = {textInputEditTextEmail.getText().toString(), textInputEditTextPassword.getText().toString()};
                    DatabaseTask task  = new DatabaseTask(3, "User", null, dbHelper, selectionArgs);
                    if(αυτο που επιστρεφει το task ειναι true) {
                        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                        emptyInputEditText();
                        startActivity(intent);
                    }*/
                }
                break;
            case R.id.linkRegisterTextView:
                // Navigate to RegisterActivity
                Intent intentRegister = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(intentRegister);
                break;
        }
    }

    private void emptyInputEditText() {
        textInputEditTextEmail.setText(null);
        textInputEditTextPassword.setText(null);
    }
}
