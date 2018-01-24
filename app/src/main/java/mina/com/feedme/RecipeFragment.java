package mina.com.feedme;


import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.ContentValues;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RemoteViews;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.github.ivbaranov.mfb.MaterialFavoriteButton;

import java.util.ArrayList;
import java.util.List;

import mina.com.feedme.Adapter.IngredientsAdapter;
import mina.com.feedme.Adapter.StepsAdapter;
import mina.com.feedme.DataBase.RecipesContentProvider;
import mina.com.feedme.DataBase.RecipesContract;
import mina.com.feedme.Model.IngredientModel;
import mina.com.feedme.Model.RecipeModel;
import mina.com.feedme.Model.StepModel;
import mina.com.feedme.widget.FeedmeWidget;

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
    private boolean mDBstatus;

   // public static int scrollX = 0;
   // public static int scrollY = -1;
    private NestedScrollView mScrollview;

    private List<IngredientModel> mIngredients;
    private List<StepModel> mSteps;

    private IngredientsAdapter mIngredientsAdapter;
    private StepsAdapter mStepsAdapter;
    private RecipeModel mCurrentRecipe;

    private UpdateStepFragment updateFragment;
    private int mLastClickedPosition;

    LinearLayoutManager stepsLayoutManager;
    LinearLayoutManager ingredientsLayoutManager;
    Parcelable mListState;
    Parcelable mListState2;

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
            StepModel selectedStep = mSteps.get(position);
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
    public void onPause() {
        super.onPause();
       // scrollX = mScrollview.getScrollX();
       // scrollY = mScrollview.getScrollY();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_recipe, container, false);
        recipeTitleTextView = (TextView) rootView.findViewById(R.id.recipe_title);
        ingredientsRecyclerView = (RecyclerView) rootView.findViewById(R.id.ingredients_recycler_view);
        stepsRecyclerView = (RecyclerView) rootView.findViewById(R.id.steps_recycler_view);
        mScrollview = (NestedScrollView) rootView.findViewById(R.id.mainScrollView);
        MaterialFavoriteButton materialFavoriteButtonNice = (MaterialFavoriteButton) rootView.findViewById(R.id.favorite_nice);

        if (savedInstanceState != null){
            mListState = savedInstanceState.getParcelable("state1");
            mListState2 = savedInstanceState.getParcelable("state2");
        }


        materialFavoriteButtonNice.setFavorite(mDBstatus, !mDBstatus);
        final String[] temp = new String[1];
        materialFavoriteButtonNice.setOnFavoriteChangeListener(
                new MaterialFavoriteButton.OnFavoriteChangeListener() {
                    @Override
                    public void onFavoriteChanged(MaterialFavoriteButton buttonView, boolean favorite) {
                        if (favorite) {
                            changeDatabaseIngredient();
                            temp[0] = "favorite it!";


                        } else {
                            getActivity().getApplicationContext().getContentResolver().delete(RecipesContentProvider.Ingredients.INGREDIENTS, null, null);
                            temp[0] = "Un favorite it!";
                        }
                    }
                });


        materialFavoriteButtonNice.setOnFavoriteAnimationEndListener(
                new MaterialFavoriteButton.OnFavoriteAnimationEndListener() {
                    @Override
                    public void onAnimationEnd(MaterialFavoriteButton buttonView, boolean favorite) {


                        Toast.makeText(getContext(), temp[0], Toast.LENGTH_SHORT).show();
                        changeWidget();

                    }
                });

        recipeTitleTextView.setText(mRecipeTitle);

        stepsLayoutManager = new LinearLayoutManager(getContext());
        stepsRecyclerView.setLayoutManager(stepsLayoutManager);
        stepsRecyclerView.setAdapter(mStepsAdapter);

        ingredientsLayoutManager = new LinearLayoutManager(getContext());
        ingredientsRecyclerView.setLayoutManager(ingredientsLayoutManager);
        ingredientsRecyclerView.setAdapter(mIngredientsAdapter);

        return rootView;
    }

    public void updateStatus (boolean status){
        mDBstatus = status;
    }


    public void setIngredients(List<IngredientModel> ingredients) {
        mIngredients.addAll(ingredients);
        mIngredientsAdapter.notifyDataSetChanged();
    }

    public void setSteps(List<StepModel> steps) {
        mSteps.addAll(steps);
        mStepsAdapter.notifyDataSetChanged();
    }

    public void getRecipeObject(RecipeModel object){
        mCurrentRecipe = object;

    }

    private void insertIntoProvider() {
        ContentValues[] cvs = new ContentValues[mIngredients.size()];
        Log.i("insertIntoProvider",mIngredients.size()+"");
        for (int i=0 ; i< mIngredients.size() ; i++) {
            cvs[i] = new ContentValues();
            cvs[i].put(RecipesContract.IngredientEntry.RECIPE_ID, mCurrentRecipe.getmId());
            cvs[i].put(RecipesContract.IngredientEntry.RECIPE_NAME, mCurrentRecipe.getmName());
            cvs[i].put(RecipesContract.IngredientEntry.INGREDIENT_NAME, mIngredients.get(i).getmIngredient());
            cvs[i].put(RecipesContract.IngredientEntry.MEASURE, mIngredients.get(i).getmMeasure());
            cvs[i].put(RecipesContract.IngredientEntry.QUANTITY, mIngredients.get(i).getmQuantity());
        }
        getActivity().getApplicationContext().getContentResolver().bulkInsert(RecipesContentProvider.Ingredients.INGREDIENTS, cvs);
    }


    private void changeDatabaseIngredient(){
        getActivity().getApplicationContext().getContentResolver().delete(RecipesContentProvider.Ingredients.INGREDIENTS, null, null);
        insertIntoProvider();

    }


    private void changeWidget() {
        getActivity().getApplicationContext().getContentResolver().delete(RecipesContentProvider.Ingredients.INGREDIENTS, null, null);

        AppWidgetManager widgetManager = AppWidgetManager.getInstance(getActivity().getApplicationContext());
        int[] appWidgetIds = widgetManager.getAppWidgetIds(new ComponentName(getActivity().getApplicationContext(), FeedmeWidget.class));
        RemoteViews remoteViews = new RemoteViews(getActivity().getApplicationContext().getPackageName(), R.layout.widget_layout);

        if (!mDBstatus) {
            insertIntoProvider();
            remoteViews.setViewVisibility(R.id.widget_recipe_title, View.VISIBLE);
            remoteViews.setTextViewText(R.id.widget_recipe_title, mCurrentRecipe.getmName());
            mDBstatus = true;
        } else {
            remoteViews.setViewVisibility(R.id.widget_recipe_title, View.GONE);
            mDBstatus = false;
        }


        widgetManager.notifyAppWidgetViewDataChanged(appWidgetIds, R.id.widget_ingredients_list_view);
        widgetManager.updateAppWidget(appWidgetIds, remoteViews);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);


        mListState = stepsLayoutManager.onSaveInstanceState();
        mListState2 = ingredientsLayoutManager.onSaveInstanceState();
        outState.putParcelable("state1", mListState);
        outState.putParcelable("state2", mListState2);

       // outState.putIntArray("SCROLL_POSITION",
         //       new int[]{mScrollview.getScrollX(), mScrollview.getScrollY()});
    }

    @Override
    public void onResume() {
        super.onResume();

        if (mListState != null) {
            stepsLayoutManager.onRestoreInstanceState(mListState);
        }

        if (mListState2 != null){
            ingredientsLayoutManager.onRestoreInstanceState(mListState);
        }

      /*  mScrollview.post(new Runnable() {
            @Override
            public void run() {
                mScrollview.scrollTo(scrollX, scrollY);
            }
        });*/
    }


}
