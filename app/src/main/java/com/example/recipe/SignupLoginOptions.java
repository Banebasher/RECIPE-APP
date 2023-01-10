package com.example.recipe;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class SignupLoginOptions extends AppCompatActivity {
        Button Signup,SignIn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup_login_options);

        Signup = findViewById(R.id.SignupOption);
        SignIn = findViewById(R.id.SignInOption);
        
        Signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SignupLoginOptions.this,SignupPage.class));
                finish();
            }
        });
            SignIn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startActivity(new Intent(SignupLoginOptions.this,LoginPage.class));
                    finish();
                }
            });

    }

}