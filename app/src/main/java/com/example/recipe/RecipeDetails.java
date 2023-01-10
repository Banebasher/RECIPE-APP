package com.example.recipe;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.widget.AppCompatButton;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.recipe.Adapter.IngredientsAdapter;
import com.example.recipe.Adapter.InstructionsAdapter;
import com.example.recipe.Adapter.SimilarRecipeAdapter;
import com.example.recipe.Listeners.InstructionsListener;
import com.example.recipe.Listeners.RecipeClickListener;
import com.example.recipe.Listeners.RecipeDetailsListener;
import com.example.recipe.Listeners.SimilarRecipesListener;
import com.example.recipe.Models.InstructionsResponse;
import com.example.recipe.Models.RecipeDetailsResponse;
import com.example.recipe.Models.SimilarRecipesResponse;
import com.example.recipe.databinding.ActivityRecipeDetailsBinding;
import com.squareup.picasso.Picasso;

import java.util.List;

public class RecipeDetails extends DrawerBaseActivity {

    int id;
    TextView textView_meal_name,textView_meal_source,textView_meal_summary;
    ImageView imageView_meal_image;
    RecyclerView recyclerView_meal_ingredients;
    RequestManager manager;
    IngredientsAdapter ingredientsAdapter;
    RecyclerView recyclerView_meal_similar;
    SimilarRecipeAdapter similarRecipeAdapter;
    AppCompatButton HomeButton;
    RecyclerView recyclerView_meal_instructions;
    InstructionsAdapter instructionsAdapter;

    ActivityRecipeDetailsBinding activityRecipeDetailsBinding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityRecipeDetailsBinding = ActivityRecipeDetailsBinding.inflate(getLayoutInflater());
        allocateActivityTitle("RECIPE DETAILS");
        setContentView(activityRecipeDetailsBinding.getRoot());

        findViews();

        id = Integer.parseInt(getIntent().getStringExtra("id"));
        manager = new RequestManager(this);
        manager.getRecipeDetails(recipeDetailsListener,id);

        manager.getSimilarRecipes(similarRecipesListener,id);
        manager.getInstructions(instructionsListener,id);


        HomeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(RecipeDetails.this, Mainrecipe.class)
                        .putExtra("id",id));
            }
        });
    }

    private void findViews() {
        imageView_meal_image = findViewById(R.id.imageView_meal_image);
        textView_meal_name = findViewById(R.id.textView_meal_name);
        textView_meal_source = findViewById(R.id.textView_meal_source);
        recyclerView_meal_ingredients = findViewById(R.id.recycler_meal_ingredients);
        recyclerView_meal_similar = findViewById(R.id.recycler_meal_similar);
        HomeButton = findViewById(R.id.HomeButton);
        recyclerView_meal_instructions = findViewById(R.id.recycler_meal_instructions);
    }

    private final RecipeDetailsListener recipeDetailsListener = new RecipeDetailsListener() {
        @Override
        public void didFetch(RecipeDetailsResponse response, String Message) {
            textView_meal_name.setText(response.title);
            textView_meal_source.setText(response.sourceName);

            Picasso.get().load(response.image).into(imageView_meal_image);

            recyclerView_meal_ingredients.setHasFixedSize(true);
            recyclerView_meal_ingredients.setLayoutManager( new LinearLayoutManager(RecipeDetails.this,LinearLayoutManager.HORIZONTAL,false));

            ingredientsAdapter = new IngredientsAdapter(RecipeDetails.this,response.extendedIngredients);

            recyclerView_meal_ingredients.setAdapter(ingredientsAdapter);

        }

        @Override
        public void didError(String Message) {
            Toast.makeText(RecipeDetails.this, Message, Toast.LENGTH_SHORT).show();
        }
    };
    private final SimilarRecipesListener similarRecipesListener = new SimilarRecipesListener() {
        @Override
        public void didFetch(List<SimilarRecipesResponse> response, String Message) {
            recyclerView_meal_similar.setHasFixedSize(true);
            recyclerView_meal_similar.setLayoutManager(new LinearLayoutManager(RecipeDetails.this,LinearLayoutManager.HORIZONTAL,false));
            similarRecipeAdapter = new SimilarRecipeAdapter(RecipeDetails.this,response,recipeClickListener);
            recyclerView_meal_similar.setAdapter(similarRecipeAdapter);
        }

        @Override
        public void didError(String Message) {
            Toast.makeText(RecipeDetails.this, Message, Toast.LENGTH_SHORT).show();
        }
    };


    private final RecipeClickListener recipeClickListener = new RecipeClickListener() {
        @Override
        public void onRecipeClicked(String id) {
            startActivity(new Intent(RecipeDetails.this, RecipeDetails.class)
                    .putExtra("id",id)

            );

        }
    };
    private final InstructionsListener instructionsListener = new InstructionsListener() {
        @Override
        public void didFetch(List<InstructionsResponse> response, String Message) {
                recyclerView_meal_instructions.setHasFixedSize(true);
                recyclerView_meal_instructions.setLayoutManager(new LinearLayoutManager(RecipeDetails.this,LinearLayoutManager.VERTICAL,false));
                instructionsAdapter = new InstructionsAdapter(RecipeDetails.this,response);
                recyclerView_meal_instructions.setAdapter(instructionsAdapter);
        }

        @Override
        public void didError(String Message) {
            Toast.makeText(RecipeDetails.this, Message, Toast.LENGTH_SHORT).show();
        }
    };

}