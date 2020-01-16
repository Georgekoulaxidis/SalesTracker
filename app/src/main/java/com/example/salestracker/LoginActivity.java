package com.example.salestracker;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
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

    public static final String myPreferences = "loginPrefs";
    public static final String loggedIn = "loggedIn";
    public static final String Email = "email";
    public static final String Password = "password";
    private SharedPreferences sp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        rootLayout = findViewById(R.id.loginRootLayout);
        textInputLayoutEmail = findViewById(R.id.txtInputLayoutEmail);
        textInputLayoutPassword = findViewById(R.id.txtInputLayoutPassword);
        textInputEditTextEmail = findViewById(R.id.txtInputEditTextEmail);
        textInputEditTextPassword = findViewById(R.id.txtInputEditTextPassword);
        loginBtn = findViewById(R.id.loginBtn);
        loginBtn.setOnClickListener(this);
        registerTextView = findViewById(R.id.linkRegisterTextView);
        registerTextView.setOnClickListener(this);

        dbHelper = new DatabaseHelper(LoginActivity.this);
        validator = new InputValidator(LoginActivity.this);

        sp = getSharedPreferences(myPreferences, Context.MODE_PRIVATE);
        if(sp.getBoolean(loggedIn, false)) {
            goToMainActivity(dbHelper
                    .checkUser(sp.getString(Email, null), sp.getString(Password, null)));
        }

    }

    @Override
    public void onBackPressed() {
        Intent a = new Intent(Intent.ACTION_MAIN);
        a.addCategory(Intent.CATEGORY_HOME);
        a.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(a);
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()) {
            case R.id.loginBtn:
                Log.d("dennis", "Login button clicked");

                if(validator.isInputEditTextFilled(textInputEditTextEmail, textInputLayoutEmail, "Entering email is mandatory") &&
                        validator.isInputEditTextFilled(textInputEditTextPassword, textInputLayoutPassword, "Entering password is mandatory") &&
                        validator.isInputEditTextEmail(textInputEditTextEmail, textInputLayoutEmail, "Enter a valid email address")) {

                    String email = textInputEditTextEmail.getText().toString();
                    String password = textInputEditTextPassword.getText().toString();
                    if(dbHelper.checkUser(email, password) != null) {
                        emptyInputEditText();

                        SharedPreferences.Editor editor = sp.edit();
                        editor.putBoolean(loggedIn, true);
                        editor.putString(Email, email);
                        editor.putString(Password, password);
                        editor.commit();

                        goToMainActivity(dbHelper.checkUser(email, password));
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
                final InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(v.getRootView().getWindowToken(), 0);
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

    private void goToMainActivity(User user) {
        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
        intent.putExtra("user", user);
        startActivity(intent);
        finish();
    }
}
