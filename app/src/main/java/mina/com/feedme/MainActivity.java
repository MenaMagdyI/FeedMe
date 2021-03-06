package mina.com.feedme;

import android.content.Context;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.AsyncTaskLoader;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import mina.com.feedme.Model.RecipeModel;
import mina.com.feedme.Utils.JsonUtils;
import mina.com.feedme.Utils.NetworkUtils;

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<List<RecipeModel>> {


    Context mContext;
    ProgressBar loadingIndicator;
    MainFragment mainFragment;
    private List<RecipeModel> mRecipes;
    public static final int RECIPES_LOADER_ID = 1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.i("MainActivity","just Created !!!!!");
        mContext = MainActivity.this;
        mRecipes = new ArrayList<>();
        loadingIndicator = (ProgressBar) findViewById(R.id.loading_indicator);

        mainFragment = (MainFragment) getSupportFragmentManager().findFragmentByTag("MainFragment");
        if (mainFragment == null) {
            mainFragment = new MainFragment();
        }
        getSupportFragmentManager().beginTransaction().replace(R.id.container, mainFragment,"MainFragment").commit();






        if (NetworkUtils.isConnected(mContext)) {
            getSupportLoaderManager().initLoader(RECIPES_LOADER_ID, null, this);
            Log.i("MainActivity","2222222222222222");
        } else {
            Log.i("MainActivity","3333333333333333");
            loadingIndicator.setVisibility(View.GONE);
            Toast.makeText(mContext,"NO internet Connection", Toast.LENGTH_LONG).show();

        }

    }


    @Override
    public Loader<List<RecipeModel>> onCreateLoader(int id, Bundle args) {
        return new AsyncTaskLoader<List<RecipeModel>>(mContext) {
            @Override
            protected void onStartLoading() {
                super.onStartLoading();
                Log.i("MainActivity","4444444444444444");
                loadingIndicator.setVisibility(View.VISIBLE);
                forceLoad();
            }

            @Override
            public List<RecipeModel> loadInBackground() {

                Log.i("MainActivity","555555555555555555555");
                String recipes_Url = getString(R.string.recipes_url);
                URL recipesUrl = NetworkUtils.getUrl(recipes_Url);
                String recipesJsonResponse = NetworkUtils.getJsonResponse(recipesUrl);
                return JsonUtils.extractRecipesFromJson(recipesJsonResponse, mContext);
            }
        };
    }

    @Override
    public void onLoadFinished(Loader<List<RecipeModel>> loader, List<RecipeModel> data) {
        mRecipes.clear();
        mRecipes.addAll(data);
        Log.i("mainActivity","91919191919191919");

        loadingIndicator.setVisibility(View.GONE);
        mainFragment.resetFragmentArray(mRecipes);
        Log.i("MainActivity","66666666666666666");

    }

    @Override
    public void onLoaderReset(Loader<List<RecipeModel>> loader) {
    }
}
