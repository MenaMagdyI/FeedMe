package mina.com.feedme;


import android.content.res.Configuration;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import mina.com.feedme.Adapter.RecipesAdapter;
import mina.com.feedme.Data.Recipe;

/**
 * A simple {@link Fragment} subclass.
 */
public class MainFragment extends Fragment implements RecipesAdapter.RecipeOnClickListener {

    public static final String RECIPE_KEY = "recipe_value";
    public static final String RECIPE_INGREDIENTS_KEY = "ingredients_value";
    public static final String RECIPE_STEPS_KEY = "steps_value";

    private List<Recipe> mRecipes;
    RecipesAdapter recipesAdapter;

    RecyclerView recipesRecyclerView;
    @BindView(R.id.empty_recipes)
    View emptyListView;


    public MainFragment() {
        // Required empty public constructor
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater,@Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_main, container, false);
        ButterKnife.bind(this, rootView);

        mRecipes = new ArrayList<>();
        recipesRecyclerView = (RecyclerView) getActivity().findViewById(R.id.fragment_recycler_view);
        Log.i("FragmentActivity","7777777777777");

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
        Log.i("mainActivity","after all");

        recipesAdapter = new RecipesAdapter(mRecipes, this);
        recipesRecyclerView.setAdapter(recipesAdapter);

        return rootView;

    }



    public void resetFragmentArray(List<Recipe> recipes) {
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
        //will add some action here later!
    }
}
