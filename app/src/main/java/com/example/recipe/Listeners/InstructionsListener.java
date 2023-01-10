package com.example.recipe.Listeners;

import com.example.recipe.Models.InstructionsResponse;

import java.util.List;

public interface InstructionsListener {
    void didFetch(List<InstructionsResponse> response, String Message);
    void didError(String Message);

}
