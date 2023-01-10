package com.example.recipe;

import android.os.Bundle;

import com.example.recipe.databinding.ActivityAboutPageBinding;

public class AboutPage extends DrawerBaseActivity {
ActivityAboutPageBinding activityAboutPageBinding ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityAboutPageBinding = ActivityAboutPageBinding.inflate(getLayoutInflater());
        allocateActivityTitle("About");
        setContentView(activityAboutPageBinding.getRoot());
    }
}