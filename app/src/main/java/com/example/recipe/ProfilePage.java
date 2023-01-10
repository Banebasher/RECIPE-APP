package com.example.recipe;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.recipe.databinding.ActivityProfilePageBinding;
import com.google.android.gms.maps.GoogleMap;

public class ProfilePage extends DrawerBaseActivity {
    ActivityProfilePageBinding activityProfilePageBinding;
    SQLdbHelper DB;
    SQLdbHelper sqLdbHelper = new SQLdbHelper(this);
    TextView profile_username, profile_mail,profile_address;
    ImageView profile_photo;
    GoogleMap googleMap;
    Map map;
    public static int wait = 0;
    public static int getWait() {
        return wait;
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityProfilePageBinding = ActivityProfilePageBinding.inflate(getLayoutInflater());
        allocateActivityTitle("Profile");
        setContentView(activityProfilePageBinding.getRoot());


        Cursor cursor = sqLdbHelper.ViewData();

        profile_username = findViewById(R.id.profile_username);
        profile_mail = findViewById(R.id.profile_email);

        profile_photo = findViewById(R.id.profile_photo);
        profile_address = findViewById(R.id.profile_address);
        profile_address.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


              Intent intent = new Intent(getApplicationContext(),Map.class);

             startActivity(intent);
             wait =1 ;

        }
        });

        cursor = sqLdbHelper.getusername(LoginPage.getValue());
while(cursor.moveToNext()) {

    profile_username.setText("Username:  " + (cursor.getString(0)));
    profile_mail.setText("Email:  " + cursor.getString(1));
    byte[] imagebyte = cursor.getBlob(3);
    Bitmap bitmap = BitmapFactory.decodeByteArray(imagebyte,0,imagebyte.length);
    profile_photo.setImageBitmap(bitmap);
    profile_address.setText("Address: "+ cursor.getString(4));


}
    }


}


