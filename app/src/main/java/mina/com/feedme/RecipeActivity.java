package mina.com.feedme;

import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;

import java.util.List;

import mina.com.feedme.DataBase.RecipesContentProvider;
import mina.com.feedme.DataBase.RecipesContract;
import mina.com.feedme.Model.IngredientModel;
import mina.com.feedme.Model.RecipeModel;
import mina.com.feedme.Model.StepModel;

public class RecipeActivity extends AppCompatActivity {

    private StepFragment stepFragment;
    private List<StepModel> steps;
    private boolean existInDB;
    private RecipeModel mCurrentRecipe;
    private List<IngredientModel> mIngredients;
    private Menu mRecipeMenu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe);

        final Intent sentIntent = getIntent();

        mCurrentRecipe = sentIntent.getParcelableExtra(MainFragment.RECIPE_KEY);
        mIngredients = sentIntent.getParcelableArrayListExtra(MainFragment.RECIPE_INGREDIENTS_KEY);
        steps = sentIntent.getParcelableArrayListExtra(MainFragment.RECIPE_STEPS_KEY);

        RecipeFragment recipeFragment = new RecipeFragment();
        String selection = RecipesContract.IngredientEntry.RECIPE_ID + "=?";
        String[] selectionArgs = new String[]{String.valueOf(mCurrentRecipe.getmId())};
        Cursor cursor = getContentResolver().query(RecipesContentProvider.Ingredients.INGREDIENTS, null, selection, selectionArgs, null);
        if (cursor != null && cursor.getCount() > 0) {
            existInDB = true;
        } else {
            existInDB = false;
        } if (cursor != null) cursor.close();

        recipeFragment.updateStatus(existInDB);
        recipeFragment.getRecipeObject(mCurrentRecipe);
        Log.i("RRRRRRRRRR",mCurrentRecipe.getmName());
        recipeFragment.setRecipeTitle(mCurrentRecipe.getmName());

        getSupportFragmentManager().beginTransaction()
                .add(R.id.fragment_recipe_container, recipeFragment)
                .commit();

        recipeFragment.setIngredients(mIngredients);
        recipeFragment.setSteps(steps);




        View tabletView = findViewById(R.id.tablet_fragment_step_container);
        if (tabletView != null) {
            stepFragment = new StepFragment();
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.tablet_fragment_step_container, stepFragment)
                    .commit();

            recipeFragment.setUpdateFragment(new RecipeFragment.UpdateStepFragment() {
                @Override
                public void update(int position) {
                    if (stepFragment != null) {
                        sendData(position);
                    }
                }
            });
        }

    }


    private void sendData(int position) {
        StepModel selectedStep = steps.get(position);

        String stepDescription = selectedStep.getmDescription();
        String imageUri = selectedStep.getmThumbnailUrl();
        String videoUri = selectedStep.getmVideoUrl();
        stepFragment.setDescription(stepDescription);
        stepFragment.setImageView(imageUri);
        stepFragment.getVideoPlayerStandBY(videoUri);
    }


}
