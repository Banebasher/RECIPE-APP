package com.example.recipe.Listeners;

import com.example.recipe.Models.RecipeDetailsResponse;

public interface RecipeDetailsListener {
    void didFetch(RecipeDetailsResponse response,String Message);
    void didError(String Message);
}
