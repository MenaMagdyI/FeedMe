package mina.com.feedme;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import mina.com.feedme.Model.StepModel;

public class StepActivity extends AppCompatActivity {

    StepFragment stepFragment;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_step);

       /* stepFragment = new StepFragment();

        getSupportFragmentManager().beginTransaction()
                .replace(R.id.step_container, stepFragment)
                .commit();*/

        stepFragment = (StepFragment) getSupportFragmentManager().findFragmentByTag("stepFragment");
        if (stepFragment == null) {
            stepFragment = new StepFragment();
        }
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.step_container, stepFragment, "stepFragment")
                .commit();
    }

    @Override
    protected void onResume() {
        super.onResume();
        TransferFragmentData();
    }

    private void TransferFragmentData() {
        Intent sentIntent = getIntent();
        StepModel sentStep = sentIntent.getParcelableExtra(RecipeFragment.SELECTED_STEP);
        String stepDescription = sentStep.getmDescription();
        String imageUri = sentStep.getmThumbnailUrl();
        String videoUri = sentStep.getmVideoUrl();
        stepFragment.setDescription(stepDescription);
        stepFragment.setImageView(imageUri);
        stepFragment.getVideoPlayerStandBY(videoUri);
    }
}
