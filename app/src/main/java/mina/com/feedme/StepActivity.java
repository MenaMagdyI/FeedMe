package mina.com.feedme;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import mina.com.feedme.Model.Step;

public class StepActivity extends AppCompatActivity {

    StepFragment stepFragment;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_step);

        stepFragment = new StepFragment();

        getSupportFragmentManager().beginTransaction()
                .add(R.id.step_fragment_container, stepFragment)
                .commit();
    }

    @Override
    protected void onResume() {
        super.onResume();
        TransferFragmentData();
    }

    private void TransferFragmentData() {
        Intent sentIntent = getIntent();
        Step sentStep = sentIntent.getParcelableExtra(RecipeFragment.SELECTED_STEP);
        String stepDescription = sentStep.getmDescription();
        String imageUri = sentStep.getmThumbnailUrl();
        String videoUri = sentStep.getmVideoUrl();
        stepFragment.setDescription(stepDescription);
        stepFragment.setImageView(imageUri);
        stepFragment.getVideoPlayerStandBY(videoUri);
    }
}
