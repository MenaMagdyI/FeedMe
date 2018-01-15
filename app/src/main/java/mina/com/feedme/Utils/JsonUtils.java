package mina.com.feedme.Utils;

import android.content.Context;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import mina.com.feedme.Model.Ingredient;
import mina.com.feedme.Model.Recipe;
import mina.com.feedme.Model.Step;
import mina.com.feedme.R;

/**
 * Created by Mena on 1/11/2018.
 */

public class JsonUtils {
    private JsonUtils() {
    }

    public static List<Recipe> extractRecipesFromJson(String jsonResponse, Context context) {
        ArrayList<Recipe> recipes = new ArrayList<>();
        try {
            JSONArray recipesJsonArray = new JSONArray(jsonResponse);
            for (int i=0 ; i<recipesJsonArray.length() ; i++) {
                JSONObject recipeJson = recipesJsonArray.getJSONObject(i);
                int id = recipeJson.getInt(context.getString(R.string.recipe_id));
                String name = recipeJson.getString(context.getString(R.string.recipe_name));
                List<Ingredient> ingredients = extractIngredientsFromJson(recipeJson.getJSONArray(context.getString(R.string.recipe_ingredients))
                        , context);
                List<Step> steps = extractStepsFromJson(recipeJson.getJSONArray(context.getString(R.string.recipe_steps)), context);
                int servings = recipeJson.getInt(context.getString(R.string.recipe_servings));
                String imageUrl = recipeJson.getString(context.getString(R.string.recipe_image_url));

                recipes.add(new Recipe(id, name, ingredients, steps, servings, imageUrl));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        } finally {
            return recipes;
        }
    }

    public static List<Ingredient> extractIngredientsFromJson(JSONArray ingredientsRoot, Context context) {
        ArrayList<Ingredient> ingredients = new ArrayList<>();

        try {
            for (int i=0 ; i<ingredientsRoot.length() ; i++) {
                JSONObject ingredientJson = ingredientsRoot.getJSONObject(i);
                double quantity = ingredientJson.getDouble(context.getString(R.string.ingredient_quantity));
                String measure = ingredientJson.getString(context.getString(R.string.ingredient_measure));
                String name = ingredientJson.getString(context.getString(R.string.ingredient_name));

                ingredients.add(new Ingredient(quantity, measure, name));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return ingredients;
    }

    public static List<Step> extractStepsFromJson(JSONArray stepsRoot, Context context) {
        ArrayList<Step> steps = new ArrayList<>();

        try {
            for (int i=0 ; i<stepsRoot.length() ; i++) {
                JSONObject stepsJson = stepsRoot.getJSONObject(i);
                int id = stepsJson.getInt(context.getString(R.string.step_id));
                String shortDescription = stepsJson.getString(context.getString(R.string.step_short_description));
                String description = stepsJson.getString(context.getString(R.string.step_description));
                String videoUrl = stepsJson.getString(context.getString(R.string.step_video_url));
                String thumbnailUrl = stepsJson.getString(context.getString(R.string.step_thumbnail_url));

                steps.add(new Step(id, shortDescription, description, videoUrl, thumbnailUrl));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return steps;
    }

}