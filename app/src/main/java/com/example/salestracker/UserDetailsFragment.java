package com.example.salestracker;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.squareup.picasso.Picasso;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.net.URI;

import static android.app.Activity.RESULT_OK;

public class UserDetailsFragment extends Fragment implements View.OnClickListener {

    private ScrollView rootView;
    private TextInputLayout textInputLayoutEmail;
    private TextInputLayout textInputLayoutPassword;
    private TextInputLayout textInputLayoutName;
    private TextInputEditText textInputEditTextEmail;
    private TextInputEditText textInputEditTextPassword;
    private TextInputEditText textInputEditTextName;
    private Button saveChangesBtn;
    private TextView addImageView;
    private ImageView userImgView;

    private DatabaseHelper dbHelper;
    private InputValidator validator;

    private static final int PICK_FROM_GALLERY = 1;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_userdetails, container, false);

        rootView = (ScrollView) view.getRootView();

        textInputLayoutEmail = view.findViewById(R.id.txtInputLayoutEmail2);
        textInputLayoutPassword = view.findViewById(R.id.txtInputLayoutPassword2);
        textInputLayoutName = view.findViewById(R.id.txtInputLayoutName2);

        textInputEditTextEmail = view.findViewById(R.id.txtInputEditTextEmail2);
        textInputEditTextEmail.setText(MainActivity.loggedInUser.getEmail());
        textInputEditTextPassword = view.findViewById(R.id.txtInputEditTextPassword2);
        textInputEditTextPassword.setText(MainActivity.loggedInUser.getPassword());
        textInputEditTextName = view.findViewById(R.id.txtInputEditTextName2);
        textInputEditTextName.setText(MainActivity.loggedInUser.getName());

        userImgView = view.findViewById(R.id.userImageView);
        userImgView.setImageBitmap(BitmapFactory.decodeByteArray(MainActivity
                .loggedInUser.getImage(), 0, MainActivity.loggedInUser.getImage().length));

        saveChangesBtn = view.findViewById(R.id.saveChangesBtn);
        saveChangesBtn.setOnClickListener(this);
        addImageView = view.findViewById(R.id.addImageTextView);
        addImageView.setOnClickListener(this);

        return view;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.saveChangesBtn:
                dbHelper = new DatabaseHelper(getActivity());
                validator = new InputValidator(getActivity());

                if (validator.isInputEditTextFilled(textInputEditTextEmail, textInputLayoutEmail, "Entering email is mandatory") &&
                        validator.isInputEditTextFilled(textInputEditTextPassword, textInputLayoutPassword, "Entering password is mandatory") &&
                        validator.isInputEditTextFilled(textInputEditTextName, textInputLayoutName, "Entering name is mandatory") &&
                        validator.isInputEditTextEmail(textInputEditTextEmail, textInputLayoutEmail, "Enter a valid email address") &&
                        validator.isInputEditTextPassword(textInputEditTextPassword, textInputLayoutPassword, "Password must contain 1 lowercase, 1 uppercase, 1 number, 1 special character, at least 8 characters")) {

                    if (!dbHelper.checkUser(textInputEditTextEmail.getText().toString().trim())
                            || textInputEditTextEmail.getText().toString().trim().equals(MainActivity.loggedInUser.getEmail())) {
                        //Βαλε alert για confirmation και βαλε τα νεα στοιχεια του user στη βαση
                        //Οταν ανανεώνονται τα στοιχεία στη βάση να φαίνονται στο nav_header
                        //TransactionTooLargeException

                        MainActivity.loggedInUser.setName(textInputEditTextName.getText().toString().trim());
                        MainActivity.loggedInUser.setEmail(textInputEditTextEmail.getText().toString().trim());
                        MainActivity.loggedInUser.setPassword(textInputEditTextPassword.getText().toString().trim());
                        ByteArrayOutputStream stream = new ByteArrayOutputStream();
                        ((BitmapDrawable)userImgView.getDrawable()).getBitmap().compress(Bitmap.CompressFormat.PNG, 0, stream);;
                        MainActivity.loggedInUser.setImage(stream.toByteArray());
                        dbHelper.updateUser(MainActivity.loggedInUser);

                        Snackbar.make(rootView, "Your details were updated successfully!", Snackbar.LENGTH_LONG).show();
                    } else {
                        Snackbar.make(rootView, "User with the email address you inserted already exists", Snackbar.LENGTH_LONG).show();
                    }

                }
                final InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(v.getRootView().getWindowToken(), 0);
                break;

            case R.id.addImageTextView:
                //Βρες τρόπο να σώζεις την εικόνα, και να κανεις update τα στοιχεία του χρήστη μαζί με αυτήν στη βάση

                try {
                    if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                        ActivityCompat.requestPermissions(getActivity(), new String[]{android.Manifest.permission.READ_EXTERNAL_STORAGE}, 1);
                    } else {
                        Intent galleryIntent = new Intent(Intent.ACTION_PICK,
                                android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                        // Start the Intent
                        startActivityForResult(galleryIntent, PICK_FROM_GALLERY);
                    }
                } catch(Exception e) {
                    e.printStackTrace();
                }

                break;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case PICK_FROM_GALLERY: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Intent galleryIntent = new Intent(Intent.ACTION_PICK,
                            android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    // Start the Intent
                    startActivityForResult(galleryIntent, PICK_FROM_GALLERY);
                } else {
                    Toast.makeText(getActivity(), "Please give your permission.", Toast.LENGTH_LONG).show();
                }
                break;
            }
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        try {
            // When an Image is picked
            if (requestCode == PICK_FROM_GALLERY && resultCode == RESULT_OK && null != data) {

                final Uri imageUri = data.getData();
                final InputStream imageStream = getActivity().getContentResolver().openInputStream(imageUri);
                final Bitmap selectedImage = BitmapFactory.decodeStream(imageStream);

                userImgView.setImageBitmap(selectedImage);

            } else {
                Toast.makeText(getActivity(), "You haven't picked Image",
                        Toast.LENGTH_LONG).show();
            }
        } catch (Exception e) {
            Toast.makeText(getActivity(), "Something went wrong", Toast.LENGTH_LONG)
                    .show();
        }

    }

}
