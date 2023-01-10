package com.example.recipe;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.material.navigation.NavigationView;

public class DrawerBaseActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    DrawerLayout drawerLayout;
    SQLdbHelper sqLdbHelper;
    Cursor cursor;
    LoginPage loginPage;
    @Override
    public void setContentView(View view) {
        drawerLayout = (DrawerLayout) getLayoutInflater().inflate(R.layout.activity_drawer_base,null);
        FrameLayout container = drawerLayout.findViewById(R.id.activityContainer);
        container.addView(view);
        super.setContentView(drawerLayout);

        Toolbar toolbar = drawerLayout.findViewById(R.id.toolBar);
        setSupportActionBar(toolbar);


        NavigationView navigationView = drawerLayout.findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this,drawerLayout,toolbar,R.string.men_drawer_open,R.string.men_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        View header = navigationView.getHeaderView(0);
        TextView nav_username,nav_email;
        ImageView nav_profileimage;

        nav_profileimage = header.findViewById(R.id.nav_profileIcon);
        nav_email = header.findViewById(R.id.nav_email);
        nav_username = header.findViewById(R.id.nav_username);

        sqLdbHelper = new SQLdbHelper(this);

        cursor = sqLdbHelper.getusername(LoginPage.getValue());
            while(cursor.moveToNext())
        {

                nav_username.setText(""+cursor.getString(0));
                nav_email.setText(""+cursor.getString(1));
            byte[] imagebyte = cursor.getBlob(3);
            Bitmap bitmap = BitmapFactory.decodeByteArray(imagebyte,0,imagebyte.length);
            nav_profileimage.setImageBitmap(bitmap);

        }

    }


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        drawerLayout.closeDrawer(GravityCompat.START);

        switch (item.getItemId())
        {
            case R.id.nav_profile:
            {
                startActivity(new Intent(getApplicationContext(),ProfilePage.class));
                overridePendingTransition(0,0);

                break;
            }
            case R.id.nav_home:
            {
                startActivity(new Intent(getApplicationContext(),Mainrecipe.class));
                overridePendingTransition(0,0);

                break;
            }
            case R.id.nav_about:
            {
                startActivity(new Intent(getApplicationContext(),AboutPage.class));
                overridePendingTransition(0,0);
                break;
            }
            case R.id.nav_logout:
            {
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setMessage("Are you sure you want to exit")
                        .setCancelable(false)
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                moveTaskToBack(true);
                                finish();
                                android.os.Process.killProcess(android.os.Process.myPid());
                                System.exit(1);


                            }
                        })
                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.cancel();
                            }
                        });
                AlertDialog alertDialog = builder.create();
                alertDialog.show();

                break;
            }
            case R.id.nav_search:
            {
                startActivity(new Intent(getApplicationContext(),Map.class));
                overridePendingTransition(0,0);
                break;
            }


        }
        return false;
    }
protected void allocateActivityTitle(String titleString)
{
    if(getSupportActionBar() != null)
    {getSupportActionBar().setTitle(titleString);}
}
    @Override
    public void onBackPressed()
    {
        if(drawerLayout.isDrawerOpen(GravityCompat.START))
        {
            drawerLayout.closeDrawer(GravityCompat.START);
        }
        else    {
            super.onBackPressed();
        }
    }
}