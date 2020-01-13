package com.example.salestracker;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.ScrollView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener {

    private ScrollView rootView;
    private TextInputLayout textInputLayoutEmail;
    private TextInputLayout textInputLayoutPassword;
    private TextInputLayout textInputLayoutName;
    private TextInputLayout textInputLayoutConfPswd;
    private TextInputEditText textInputEditTextEmail;
    private TextInputEditText textInputEditTextPassword;
    private TextInputEditText textInputEditTextName;
    private TextInputEditText textInputEditTextConfPswd;
    private Button registerBtn;
    private TextView loginTextView;

    private DatabaseHelper dbHelper;
    private InputValidator validator;
    private User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        rootView = findViewById(R.id.scrollView);

        textInputLayoutEmail = findViewById(R.id.txtInputLayoutEmail2);
        textInputLayoutPassword = findViewById(R.id.txtInputLayoutPassword2);
        textInputLayoutName = findViewById(R.id.txtInputLayoutName);
        textInputLayoutConfPswd = findViewById(R.id.txtInputLayoutConfPswd);

        textInputEditTextEmail = findViewById(R.id.txtInputEditTextEmail2);
        textInputEditTextPassword = findViewById(R.id.txtInputEditTextPassword2);
        textInputEditTextName = findViewById(R.id.txtInputEditTextName);
        textInputEditTextConfPswd = findViewById(R.id.txtInputEditTextConfPswd);

        registerBtn = findViewById(R.id.registerBtn);
        registerBtn.setOnClickListener(this);
        loginTextView = findViewById(R.id.linkLoginTextView);
        loginTextView.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.registerBtn:
                Log.d("dennis", "Login button clicked");
                dbHelper = new DatabaseHelper(RegisterActivity.this);
                validator = new InputValidator(RegisterActivity.this);
                user = new User();

                if(validator.isInputEditTextFilled(textInputEditTextEmail, textInputLayoutEmail, "Entering email is mandatory") &&
                        validator.isInputEditTextFilled(textInputEditTextPassword, textInputLayoutPassword, "Entering password is mandatory") &&
                        validator.isInputEditTextFilled(textInputEditTextName, textInputLayoutName, "Entering name is mandatory") &&
                        validator.isInputEditTextFilled(textInputEditTextConfPswd, textInputLayoutConfPswd, "Entering password confirmation is mandatory") &&
                        validator.isInputEditTextEmail(textInputEditTextEmail, textInputLayoutEmail, "Enter a valid email address") &&
                                validator.isInputEditTextPassword(textInputEditTextPassword, textInputLayoutPassword, "Password must contain 1 lowercase, 1 uppercase, 1 number, 1 special character, at least 8 characters") &&
                                validator.isInputEditTextMatches(textInputEditTextPassword, textInputEditTextConfPswd, textInputLayoutConfPswd, "Passwords must match")) {

                    if(!dbHelper.checkUser(textInputEditTextEmail.getText().toString().trim())) {
                        user.setName(textInputEditTextName.getText().toString().trim());
                        user.setEmail(textInputEditTextEmail.getText().toString().trim());
                        user.setPassword(textInputEditTextPassword.getText().toString().trim());

                        dbHelper.addUser(user);
                        Snackbar.make(rootView, "User registered successfully", Snackbar.LENGTH_LONG).show();
                        emptyInputEditText();

                        Intent intent = new Intent(RegisterActivity.this, MainActivity.class);
                        intent.putExtra("user", user);
                        startActivity(intent);
                    }
                }
                final InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(v.getRootView().getWindowToken(), 0);
                break;

            case R.id.linkLoginTextView:
                Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                startActivity(intent);
                break;
        }
    }

    private void emptyInputEditText() {
        textInputEditTextName.setText(null);
        textInputEditTextEmail.setText(null);
        textInputEditTextPassword.setText(null);
        textInputEditTextConfPswd.setText(null);
    }
}
