package com.example.recipe;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.recipe.Adapter.RandomRecipeAdapter;
import com.example.recipe.Listeners.RandomRecipeResponseListener;
import com.example.recipe.Listeners.RecipeClickListener;
import com.example.recipe.Models.RandomRecipeAPIResponse;
import com.example.recipe.databinding.ActivityMainrecipeBinding;

import java.util.ArrayList;
import java.util.List;

public class Mainrecipe extends DrawerBaseActivity {

   RequestManager manager;
   RandomRecipeAdapter randomRecipeAdapter;
   RecyclerView recyclerView;
   Spinner spinner;
   List<String> tags = new ArrayList<>();
   SearchView searchView;

    ActivityMainrecipeBinding activityMainrecipeBinding;
    String usernameid = "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        SQLdbHelper sqLdbHelper = new SQLdbHelper(this);

        super.onCreate(savedInstanceState);
        activityMainrecipeBinding = ActivityMainrecipeBinding.inflate(getLayoutInflater());
        allocateActivityTitle("RECIPES");
        setContentView(activityMainrecipeBinding.getRoot());

        searchView = findViewById(R.id.searchView_home);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                tags.clear();
                tags.add(query);
                manager.getRandomRecipes(randomRecipeResponseListener,tags);

                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });


        spinner = findViewById(R.id.spinner_tags);
        ArrayAdapter arrayAdapter = ArrayAdapter.createFromResource(
                this,
                R.array.tags,
                R.layout.spinner_text

        );

        arrayAdapter.setDropDownViewResource(R.layout.spinner_inner_text);
        spinner.setAdapter(arrayAdapter);
        spinner.setOnItemSelectedListener(spinnerSelectedListener);

        manager = new RequestManager(this);


    }
    private final RandomRecipeResponseListener randomRecipeResponseListener = new RandomRecipeResponseListener() {
        @Override
        public void didFetch(RandomRecipeAPIResponse response, String Message) {
            recyclerView = findViewById(R.id.recycler_random);
            recyclerView.setHasFixedSize(true);
            recyclerView.setLayoutManager(new GridLayoutManager(Mainrecipe.this,1));
            randomRecipeAdapter = new RandomRecipeAdapter(Mainrecipe.this,response.recipes,recipeClickListener);
            recyclerView.setAdapter(randomRecipeAdapter);
        }

        @Override
        public void didError(String Message) {
            Toast.makeText(Mainrecipe.this, Message, Toast.LENGTH_SHORT).show();
        }
    };

    private final AdapterView.OnItemSelectedListener spinnerSelectedListener = new AdapterView.OnItemSelectedListener() {
        @Override
        public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
            tags.clear();
            tags.add(adapterView.getSelectedItem().toString());
            manager.getRandomRecipes(randomRecipeResponseListener,tags);

        }

        @Override
        public void onNothingSelected(AdapterView<?> adapterView) {

        }
    };
    private final RecipeClickListener recipeClickListener = new RecipeClickListener() {
        @Override
        public void onRecipeClicked(String id) {
            startActivity(new Intent(Mainrecipe.this, RecipeDetails.class)
                    .putExtra("id",id)

            );

        }
    };




}
