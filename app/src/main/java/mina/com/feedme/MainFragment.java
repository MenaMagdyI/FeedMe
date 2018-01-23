package mina.com.feedme;


import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import mina.com.feedme.Adapter.RecipesAdapter;
import mina.com.feedme.Model.RecipeModel;

/**
 * A simple {@link Fragment} subclass.
 */
public class MainFragment extends Fragment implements RecipesAdapter.RecipeOnClickListener {

    public static final String RECIPE_KEY = "recipe_value";
    public static final String RECIPE_INGREDIENTS_KEY = "ingredients_value";
    public static final String RECIPE_STEPS_KEY = "steps_value";

    private List<RecipeModel> mRecipes;
    RecipesAdapter recipesAdapter;

    private StatefulRecyclerView recipesRecyclerView;
    TextView emptyListView;


    public MainFragment() {
        // Required empty public constructor
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater,@Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_main, container, false);

        mRecipes = new ArrayList<>();
        recipesRecyclerView = (StatefulRecyclerView) rootView.findViewById(R.id.fragment_recycler_view);
        emptyListView = (TextView) rootView.findViewById(R.id.empty_recipes);
        Log.i("FragmentActivity","7777777777777");


        //stackoverflow suggestion !-- must edit the dimens files
        if (getResources().getBoolean(R.bool.isTablet)) {

            Log.i("FragmentActivity","88888888888888888");
            GridLayoutManager layoutManager;
            if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
                layoutManager = new GridLayoutManager(getContext(), 3);
            } else {
                layoutManager = new GridLayoutManager(getContext(), 2);
            }
            recipesRecyclerView.setLayoutManager(layoutManager);
        } else {

            LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
            recipesRecyclerView.setLayoutManager(layoutManager);
            Log.i("FragmentActivity","999999999999999999999");
        }
        Log.i("FragmentActivity","after all");

        recipesAdapter = new RecipesAdapter(mRecipes, this);
        recipesRecyclerView.setAdapter(recipesAdapter);

        return rootView;

    }



    public void resetFragmentArray(List<RecipeModel> recipes) {
        Log.i("FragmentActivity","00000000000000000");
        mRecipes.clear();
        mRecipes.addAll(recipes);
        recipesAdapter.notifyDataSetChanged();

        if (mRecipes.isEmpty()) {
            recipesRecyclerView.setVisibility(View.GONE);
            emptyListView.setVisibility(View.VISIBLE);
        } else {
            emptyListView.setVisibility(View.GONE);
            recipesRecyclerView.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onClick(int position) {
        RecipeModel selectedRecipe = mRecipes.get(position);
        Intent detailIntent = new Intent(getContext(), RecipeActivity.class);
        detailIntent.putExtra(RECIPE_KEY, selectedRecipe);
        detailIntent.putParcelableArrayListExtra(RECIPE_STEPS_KEY, (ArrayList<? extends Parcelable>) selectedRecipe.getmSteps());
        detailIntent.putParcelableArrayListExtra(RECIPE_INGREDIENTS_KEY, (ArrayList<? extends Parcelable>) selectedRecipe.getmIngredients());

        startActivity(detailIntent);

    }
}
