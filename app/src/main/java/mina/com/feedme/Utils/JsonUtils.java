package mina.com.feedme.Utils;

import android.content.Context;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import mina.com.feedme.Model.IngredientModel;
import mina.com.feedme.Model.RecipeModel;
import mina.com.feedme.Model.StepModel;
import mina.com.feedme.R;

/**
 * Created by Mena on 1/11/2018.
 */

public class JsonUtils {
    private JsonUtils() {
    }

    public static List<RecipeModel> extractRecipesFromJson(String jsonResponse, Context context) {
        ArrayList<RecipeModel> recipes = new ArrayList<>();
        try {
            JSONArray recipesJsonArray = new JSONArray(jsonResponse);
            for (int i=0 ; i<recipesJsonArray.length() ; i++) {
                JSONObject recipeJson = recipesJsonArray.getJSONObject(i);
                int id = recipeJson.getInt(context.getString(R.string.recipe_id));
                String name = recipeJson.getString(context.getString(R.string.recipe_name));
                List<IngredientModel> ingredients = extractIngredientsFromJson(recipeJson.getJSONArray(context.getString(R.string.recipe_ingredients))
                        , context);
                List<StepModel> steps = extractStepsFromJson(recipeJson.getJSONArray(context.getString(R.string.recipe_steps)), context);
                int servings = recipeJson.getInt(context.getString(R.string.recipe_servings));
                String imageUrl = recipeJson.getString(context.getString(R.string.recipe_image_url));

                recipes.add(new RecipeModel(id, name, ingredients, steps, servings, imageUrl));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        } finally {
            return recipes;
        }
    }

    public static List<IngredientModel> extractIngredientsFromJson(JSONArray ingredientsRoot, Context context) {
        ArrayList<IngredientModel> ingredients = new ArrayList<>();

        try {
            for (int i=0 ; i<ingredientsRoot.length() ; i++) {
                JSONObject ingredientJson = ingredientsRoot.getJSONObject(i);
                double quantity = ingredientJson.getDouble(context.getString(R.string.ingredient_quantity));
                String measure = ingredientJson.getString(context.getString(R.string.ingredient_measure));
                String name = ingredientJson.getString(context.getString(R.string.ingredient_name));

                ingredients.add(new IngredientModel(quantity, measure, name));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return ingredients;
    }

    public static List<StepModel> extractStepsFromJson(JSONArray stepsRoot, Context context) {
        ArrayList<StepModel> steps = new ArrayList<>();

        try {
            for (int i=0 ; i<stepsRoot.length() ; i++) {
                JSONObject stepsJson = stepsRoot.getJSONObject(i);
                int id = stepsJson.getInt(context.getString(R.string.step_id));
                String shortDescription = stepsJson.getString(context.getString(R.string.step_short_description));
                String description = stepsJson.getString(context.getString(R.string.step_description));
                String videoUrl = stepsJson.getString(context.getString(R.string.step_video_url));
                String thumbnailUrl = stepsJson.getString(context.getString(R.string.step_thumbnail_url));

                steps.add(new StepModel(id, shortDescription, description, videoUrl, thumbnailUrl));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return steps;
    }

}