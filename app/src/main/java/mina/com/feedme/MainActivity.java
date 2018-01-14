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

import butterknife.BindView;
import butterknife.ButterKnife;
import mina.com.feedme.Data.Recipe;
import mina.com.feedme.Utils.JsonUtils;
import mina.com.feedme.Utils.NetworkUtils;

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<List<Recipe>> {


    Context context;
    @BindView(R.id.loading_indicator)
    ProgressBar loadingIndicator;

    private List<Recipe> mRecipes;
    public static final int RECIPES_LOADER_ID = 11;

    MainFragment mainRecipesFragment;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.i("MainActivity","just Created !!!!!");
        ButterKnife.bind(this);
        context = MainActivity.this;

        mRecipes = new ArrayList<>();
        mainRecipesFragment = new MainFragment();

        getSupportFragmentManager().beginTransaction()
                .add(R.id.container, mainRecipesFragment)
                .commit();


        if (NetworkUtils.isConnected(context)) {
            getSupportLoaderManager().initLoader(RECIPES_LOADER_ID, null, this);
            Log.i("MainActivity","2222222222222222");
        } else {
            Log.i("MainActivity","3333333333333333");
            loadingIndicator.setVisibility(View.GONE);
            Toast.makeText(context,"NO internet Connection", Toast.LENGTH_LONG).show();

        }

    }


    @Override
    public Loader<List<Recipe>> onCreateLoader(int id, Bundle args) {
        return new AsyncTaskLoader<List<Recipe>>(context) {
            @Override
            protected void onStartLoading() {
                super.onStartLoading();
                Log.i("MainActivity","4444444444444444");
                loadingIndicator.setVisibility(View.VISIBLE);
                forceLoad();
            }

            @Override
            public List<Recipe> loadInBackground() {

                Log.i("MainActivity","555555555555555555555");
                String recipesUrlStr = getString(R.string.recipes_url);
                URL recipesUrl = NetworkUtils.getUrl(recipesUrlStr);
                String recipesJsonResponse = NetworkUtils.getJsonResponse(recipesUrl);
                return JsonUtils.extractRecipesFromJson(recipesJsonResponse, context);
            }
        };
    }

    @Override
    public void onLoadFinished(Loader<List<Recipe>> loader, List<Recipe> data) {
        mRecipes.clear();
        mRecipes.addAll(data);
        Log.i("mainActivity","91919191919191919");

        loadingIndicator.setVisibility(View.GONE);
        mainRecipesFragment.resetFragmentArray(mRecipes);
        Log.i("MainActivity","66666666666666666");

    }

    @Override
    public void onLoaderReset(Loader<List<Recipe>> loader) {
    }
}
