package mina.com.feedme;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import mina.com.feedme.Adapter.IngredientsAdapter;
import mina.com.feedme.Adapter.StepsAdapter;
import mina.com.feedme.Model.Ingredient;
import mina.com.feedme.Model.Step;

/**
 * A simple {@link Fragment} subclass.
 */
public class RecipeFragment extends Fragment implements StepsAdapter.StepOnClickListener {


    public RecipeFragment() {
        mIngredients = new ArrayList<>();
        mIngredientsAdapter = new IngredientsAdapter(mIngredients);

        mSteps = new ArrayList<>();
        mStepsAdapter = new StepsAdapter(mSteps, this);

        // For Highlighting the selected Item on tablets Layout
        mLastClickedPosition = -1;
    }

    public static String SELECTED_STEP = "selected_step";
    TextView recipeTitleTextView;
    RecyclerView ingredientsRecyclerView;
    RecyclerView stepsRecyclerView;

    private String mRecipeTitle;

    private List<Ingredient> mIngredients;
    private List<Step> mSteps;

    private IngredientsAdapter mIngredientsAdapter;
    private StepsAdapter mStepsAdapter;

    private UpdateStepFragment updateFragment;
    private int mLastClickedPosition;

    @Override
    public void onClick(int position) {

        if (getContext().getResources().getBoolean(R.bool.isTablet)) {
            updateFragment.update(position);
            // Highlight Selected Item and remove last highlighted item
            if (mLastClickedPosition != -1) {
                View lastClickedItemView = stepsRecyclerView.findViewHolderForAdapterPosition(mLastClickedPosition).itemView;
                lastClickedItemView.setBackgroundColor(ContextCompat.getColor(lastClickedItemView.getContext(), android.R.color.white));
            }
            View itemView = stepsRecyclerView.findViewHolderForAdapterPosition(position).itemView;
            itemView.setBackgroundColor(ContextCompat.getColor(itemView.getContext(), android.R.color.holo_blue_dark));
            mLastClickedPosition = position;
        } else {
            Step selectedStep = mSteps.get(position);
            Intent stepIntent = new Intent(getContext(), StepActivity.class);
            stepIntent.putExtra(SELECTED_STEP, selectedStep);

            startActivity(stepIntent);
        }

    }


    public void setRecipeTitle(String recipeTitle) {
        mRecipeTitle = recipeTitle;
        Log.i("RRRRRr222222",mRecipeTitle);
    }


    public interface UpdateStepFragment {
        void update(int position);
    }

    public void setUpdateFragment(UpdateStepFragment usf) {
        updateFragment = usf;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_recipe, container, false);
        recipeTitleTextView = (TextView) rootView.findViewById(R.id.recipe_title);
        ingredientsRecyclerView = (RecyclerView) rootView.findViewById(R.id.ingredients_recycler_view);
        stepsRecyclerView = (RecyclerView) rootView.findViewById(R.id.steps_recycler_view);

        recipeTitleTextView.setText(mRecipeTitle);

        LinearLayoutManager stepsLayoutManager = new LinearLayoutManager(getContext());
        stepsRecyclerView.setLayoutManager(stepsLayoutManager);
        stepsRecyclerView.setAdapter(mStepsAdapter);

        LinearLayoutManager ingredientsLayoutManager = new LinearLayoutManager(getContext());
        ingredientsRecyclerView.setLayoutManager(ingredientsLayoutManager);
        ingredientsRecyclerView.setAdapter(mIngredientsAdapter);

        return rootView;
    }

    public void setIngredients(List<Ingredient> ingredients) {
        mIngredients.addAll(ingredients);
        mIngredientsAdapter.notifyDataSetChanged();
    }

    public void setSteps(List<Step> steps) {
        mSteps.addAll(steps);
        mStepsAdapter.notifyDataSetChanged();
    }


}
