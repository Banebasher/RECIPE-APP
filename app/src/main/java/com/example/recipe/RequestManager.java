package com.example.recipe;

import android.content.Context;

import com.example.recipe.Listeners.InstructionsListener;
import com.example.recipe.Listeners.RandomRecipeResponseListener;
import com.example.recipe.Listeners.RecipeDetailsListener;
import com.example.recipe.Listeners.SimilarRecipesListener;
import com.example.recipe.Models.InstructionsResponse;
import com.example.recipe.Models.RandomRecipeAPIResponse;
import com.example.recipe.Models.RecipeDetailsResponse;
import com.example.recipe.Models.SimilarRecipesResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public class RequestManager {

    Context context;
    Retrofit retrofit = new Retrofit.Builder()
            .baseUrl("https://api.spoonacular.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build();

    public RequestManager(Context context) {
        this.context = context;
    }

    public void getRandomRecipes(RandomRecipeResponseListener listener,List<String> tags)
    {
        CallRandomRecipes callRandomRecipes = retrofit.create(CallRandomRecipes.class);
        Call<RandomRecipeAPIResponse> call = callRandomRecipes.callRandomRecipe(context.getString(R.string.api_key),"10",tags);
        call.enqueue(new Callback<RandomRecipeAPIResponse>() {
            @Override
            public void onResponse(Call<RandomRecipeAPIResponse> call, Response<RandomRecipeAPIResponse> response) {
                if(!response.isSuccessful())
                {
                    listener.didError(response.message());
                    return;
                }
                listener.didFetch(response.body(),response.message());
            }

            @Override
            public void onFailure(Call<RandomRecipeAPIResponse> call, Throwable t) {
            listener.didError(t.getMessage());
            }
        });
    }

    public void getRecipeDetails(RecipeDetailsListener recipeDetailsListener, int id)
    {
        CallRecipeDetails callRecipeDetails = retrofit.create(CallRecipeDetails.class);
        Call<RecipeDetailsResponse> call = callRecipeDetails.callRecipeDetails(id,context.getString(R.string.api_key));
        call.enqueue(new Callback<RecipeDetailsResponse>() {
            @Override
            public void onResponse(Call<RecipeDetailsResponse> call, Response<RecipeDetailsResponse> response) {
                if(!response.isSuccessful())
                {recipeDetailsListener.didError(response.message());
                return;}
                recipeDetailsListener.didFetch(response.body(),response.message());

            }

            @Override
            public void onFailure(Call<RecipeDetailsResponse> call, Throwable t) {
                recipeDetailsListener.didError(t.getMessage());
            }
        });
    }
    public  void getSimilarRecipes(SimilarRecipesListener similarRecipesListener,int id){
        CallSimliarRecipes callSimilarRecipes = retrofit.create(CallSimliarRecipes.class);
        Call<List<SimilarRecipesResponse>> call = callSimilarRecipes.callSimilarRecipe(id,"10",context.getString(R.string.api_key));
        call.enqueue(new Callback<List<SimilarRecipesResponse>>() {
            @Override
            public void onResponse(Call<List<SimilarRecipesResponse>> call, Response<List<SimilarRecipesResponse>> response) {
                if(!response.isSuccessful())
                {
                    similarRecipesListener.didError(response.message());
                    return;
                }
                similarRecipesListener.didFetch(response.body(),response.message());
            }

            @Override
            public void onFailure(Call<List<SimilarRecipesResponse>> call, Throwable t) {
                similarRecipesListener.didError(t.getMessage());
            }
        });

    }
    public void getInstructions(InstructionsListener listener, int id)
    {
        CallInstructions callInstructions = retrofit.create(CallInstructions.class);
        Call<List<InstructionsResponse>> call = callInstructions.callInstructions(id, context.getString(R.string.api_key));
        call.enqueue(new Callback<List<InstructionsResponse>>() {
            @Override
            public void onResponse(Call<List<InstructionsResponse>> call, Response<List<InstructionsResponse>> response) {
                if(!response.isSuccessful())
                { listener.didError(response.message()); return;}
                 listener.didFetch(response.body(), response.message());
            }

            @Override
            public void onFailure(Call<List<InstructionsResponse>> call, Throwable t) {
                listener.didError(t.getMessage());
            }
        });

    }

    private interface CallRandomRecipes
    {
        @GET("recipes/random")
        Call<RandomRecipeAPIResponse> callRandomRecipe(
                @Query("apiKey")  String apiKey,
                @Query("number" ) String number,
                @Query("tags") List<String> tags

        );


    }
    private interface CallRecipeDetails{
        @GET("recipes/{id}/information")
        Call<RecipeDetailsResponse> callRecipeDetails(
                @Path("id") int id,
                @Query("apiKey") String apiKey
        );
    }
    private interface CallSimliarRecipes{
        @GET("recipes/{id}/similar")
        Call<List<SimilarRecipesResponse>> callSimilarRecipe(
                @Path("id" ) int id,
                @Query("number") String number,
                @Query ("apiKey") String apiKey
        );
    }
    private interface CallInstructions{
        @GET("recipes/{id}/analyzedInstructions")
        Call<List<InstructionsResponse>> callInstructions(
                @Path("id") int id,
                @Query("apiKey") String apiKey
        );
    }

}
