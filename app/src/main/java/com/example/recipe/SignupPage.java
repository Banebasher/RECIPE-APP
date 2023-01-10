package com.example.recipe;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class SignupPage extends AppCompatActivity {

    TextView textView_haveAccount;

    EditText edittext_username,edittext_email,edittext_password,edittext_repeatPassword,edittext_address;
    Button button_register;
    SQLdbHelper DB;

    ImageView loginProfile_photo;

    private static final int PICK_IMAGE_REQUEST = 99;

    private Uri imagepath;
    private Bitmap imageToStore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup_page);



        textView_haveAccount =findViewById(R.id.textView_haveAccount);
        edittext_email = findViewById(R.id.edittext_email);
        edittext_username = findViewById(R.id.edittext_username);
        edittext_password = findViewById(R.id.edittext_password);
        edittext_repeatPassword = findViewById(R.id.edittext_repeatPassword);
        button_register = findViewById(R.id.button_register);
       loginProfile_photo = findViewById(R.id.loginProfile_photo);
        edittext_address = findViewById(R.id.edittext_address);
        DB = new SQLdbHelper(this);

        loginProfile_photo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(SignupPage.this, "Image clicked", Toast.LENGTH_SHORT).show();
                chooseImage();
            }
        });


        button_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                storeImage();
            }
        });

        textView_haveAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(SignupPage.this,LoginPage.class));
                finish();
            }
        });
    }


    private void chooseImage() {

        try {
            Intent intent = new Intent();
            intent.setType("image/*");
            intent.setAction(Intent.ACTION_GET_CONTENT);
            startActivityForResult(intent,PICK_IMAGE_REQUEST);

        }
        catch (Exception e)
        {
            Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT).show();

        }
    }
    @Override
  protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        try {

            super.onActivityResult(requestCode, resultCode, data);
            if(requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData()!=null)
            {
                imagepath = data.getData();
                imageToStore = MediaStore.Images.Media.getBitmap(getContentResolver(),imagepath);
                loginProfile_photo.setImageBitmap(imageToStore);
            }
        }
        catch (Exception e)
        {
            Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }
private void storeImage(){

        String username,email,password,repeatpassword,address;
        username = edittext_username.getText().toString();
        email = edittext_email.getText().toString();
        password = edittext_password.getText().toString();
        repeatpassword = edittext_repeatPassword.getText().toString();
        address = edittext_address.getText().toString();
        if(username.isEmpty())
        {showError(edittext_username, "Please enter username");}
        else if (username.length() <7)
        {showError(edittext_username,"Username must be > 7 characters");}
       else if(email.isEmpty())
        {showError(edittext_email,"Please enter email");}
       else if (!email.contains("@"))
    {showError(edittext_email, "Email is invalid (@)");}
        else if (address.isEmpty())
        {showError(edittext_address, "Please enter address");}
      else if(password.isEmpty())
        {showError(edittext_password, "Please enter password");}
    else if (password.length()<7)
    {showError(edittext_password,"Username must be > 7 characters");}
      else if (repeatpassword.isEmpty())
        {showError(edittext_repeatPassword, "Please repeat Password");}
      else if (!password.equals(repeatpassword))
        {showError(edittext_repeatPassword,"Password doesn't match");}
      else if(password.equals(repeatpassword)
   && loginProfile_photo.getDrawable() != null && imageToStore != null)
        {
            Boolean checkuser = DB.checkusername(username);
            if(checkuser ==false)
            {
        Boolean insert= DB.insertData(new ModelClass(username,email,password,imageToStore,address));
            if(insert == true)
            {
                Toast.makeText(this, "Registered successfully", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(SignupPage.this, LoginPage.class);
                startActivity(intent);
                finish();

            }
            else
                Toast.makeText(this, "Registration Failed", Toast.LENGTH_SHORT).show();
            }
            else
                showError(edittext_username,"Username has already been taken");
        }
        else Toast.makeText(getApplicationContext(), "Please fill all the fields or Select Image", Toast.LENGTH_SHORT).show();
}

    private void showError(EditText input, String s) {
        input.setError(s);
        input.requestFocus();
    }
}