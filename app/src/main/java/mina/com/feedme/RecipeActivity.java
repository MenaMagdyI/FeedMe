package mina.com.feedme;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;

import java.util.List;

import mina.com.feedme.Model.Ingredient;
import mina.com.feedme.Model.Recipe;
import mina.com.feedme.Model.Step;

public class RecipeActivity extends AppCompatActivity {

    private StepFragment stepFragment;
    private List<Step> steps;
    private boolean mIsInWidget;
    private Recipe mCurrentRecipe;
    private List<Ingredient> mIngredients;
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
        Step selectedStep = steps.get(position);

        String stepDescription = selectedStep.getmDescription();
        String imageUri = selectedStep.getmThumbnailUrl();
        String videoUri = selectedStep.getmVideoUrl();
        stepFragment.setDescription(stepDescription);
        stepFragment.setImageView(imageUri);
        stepFragment.getVideoPlayerStandBY(videoUri);
    }


}
