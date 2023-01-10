package com.example.recipe.Listeners;

import com.example.recipe.Models.SimilarRecipesResponse;

import java.util.List;

public interface SimilarRecipesListener {
    void didFetch(List<SimilarRecipesResponse> response,String Message);
    void didError(String Message);
}
