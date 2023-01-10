package com.example.recipe;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;


public class LoginPage extends AppCompatActivity {

    EditText editText_username, editText_password;
    Button button_loginButton;
    TextView textView_dontHaveAccount;
    SQLdbHelper DB;
    public static String value;
    public static String getValue() {
        return value;
    }

    public static String address;
    public static String getAddress() {
        return address  ;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_page);

        textView_dontHaveAccount = findViewById(R.id.textView_dontHaveAccount);
        editText_password = findViewById(R.id.Edittext_password);
        editText_username = findViewById(R.id.Edittext_username);
        button_loginButton = findViewById(R.id.button_loginButton);

        DB = new SQLdbHelper(this);


        button_loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                 checkCredentialss();



            }
        });


        textView_dontHaveAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginPage.this, SignupPage.class);
                startActivity(intent);
                finish();
            }
        });


    }

    private void checkCredentialss() {

        String username = editText_username.getText().toString();
        String password = editText_password.getText().toString();

        if (username.isEmpty()) {
            showError(editText_username, "Enter Username");}
        else if (password.isEmpty()) {
            showError(editText_password, "Please Enter Password");}
            else if (password.length() < 7) {
                showError(editText_password, "Password must be 7 Characters");}
            else {Boolean checkuserpassword = DB.checkusernamepassword(username, password);
            if(checkuserpassword == true)
            {
                value = username;
                Toast.makeText(LoginPage.this, "Sign in Successful", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getApplicationContext(),Mainrecipe.class);
                startActivity(intent);
                finish();

            }
            else {
                Toast.makeText(LoginPage.this, "Invalid Username or Password", Toast.LENGTH_SHORT).show();
            }

        }

    }



    private void showError(EditText input, String s) {
        input.setError(s);
        input.requestFocus();
    }




}