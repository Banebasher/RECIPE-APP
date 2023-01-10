package com.example.recipe.Listeners;

import com.example.recipe.Models.RandomRecipeAPIResponse;

public interface RandomRecipeResponseListener {
    void didFetch(RandomRecipeAPIResponse response, String Message);
    void didError(String Message);


}
