package mina.com.feedme;


import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.exoplayer2.C;
import com.google.android.exoplayer2.DefaultLoadControl;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.ui.SimpleExoPlayerView;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.squareup.picasso.Picasso;

/**
 * A simple {@link Fragment} subclass.
 */
public class StepFragment extends Fragment {

    public StepFragment() {
        // Required empty public constructor
    }

    SimpleExoPlayerView videoInstructions;
    ImageView imageDescription;
    TextView descriptionTextView;
    private SimpleExoPlayer mExoPlayer;
    private long position,temp;
    String videoURIGlobal;
    private final String SELECTED_POSITION = "EXO_POSITION";
    private final String PLAY_STATE = "state_of_play";
    private boolean state;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_step, container, false);
        videoInstructions = (SimpleExoPlayerView) rootView.findViewById(R.id.video_instructions);
        imageDescription = (ImageView) rootView.findViewById(R.id.image_instructions);
        descriptionTextView = (TextView) rootView.findViewById(R.id.description);

        Log.i("POSITIONNNNN-----------",String.valueOf(position));
        position = C.TIME_UNSET;
        if (savedInstanceState != null) {
            state = savedInstanceState.getBoolean(PLAY_STATE,state);
            position = savedInstanceState.getLong(SELECTED_POSITION, C.TIME_UNSET);
            Log.i("POSITIONNNNN11111111111",String.valueOf(position));
        }

        return rootView;
    }

    public void getVideoPlayerStandBY(String videoURL){
        if (!TextUtils.isEmpty(videoURL)){
            if (videoURL != null){
                videoURIGlobal = videoURL;
                videoInstructions.setVisibility(View.VISIBLE);
                getVideoPlayerReady(videoURL);
            }
        }
    }



    public void getVideoPlayerReady(String videoURL){
        if (mExoPlayer == null) {
            mExoPlayer = ExoPlayerFactory.newSimpleInstance(getContext(), new DefaultTrackSelector(), new DefaultLoadControl());
            videoInstructions.setPlayer(mExoPlayer);
            Uri mediaUri = Uri.parse(videoURL);
            MediaSource mediaSource = new ExtractorMediaSource(mediaUri, new DefaultDataSourceFactory(getContext(), "BakingStep"), new DefaultExtractorsFactory(), null, null);
            Log.i("POSITIONNNNN3333333333",String.valueOf(position));
            if (position != C.TIME_UNSET) mExoPlayer.seekTo(position);
            mExoPlayer.setPlayWhenReady(state);
            Log.i("EXxxxxxxxxxxx",mediaUri.toString());
            mExoPlayer.prepare(mediaSource);
        }
    }

    public void setDescription(String description) {

        descriptionTextView.setText(description);
    }

    public void setImageView(String imageUrl){
        if (imageUrl != null && !TextUtils.isEmpty(imageUrl)) {
            Picasso.with(getContext())
                    .load(Uri.parse(imageUrl))
                    .placeholder(R.drawable.content)
                    .error(R.drawable.content)
                    .into(imageDescription);
            imageDescription.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (savedInstanceState != null) {
            state = savedInstanceState.getBoolean(PLAY_STATE,state);
            position = savedInstanceState.getLong(SELECTED_POSITION, C.TIME_UNSET);
            temp = position;
            Log.i("temp1111111111111",String.valueOf(temp));

        }
    }

    @Override
    public void onStop() {
        super.onStop();
        resetPlayer();
    }


    private void resetPlayer() {
        if (mExoPlayer != null) {
            mExoPlayer.stop();
            mExoPlayer.release();
            mExoPlayer = null;
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        if (mExoPlayer != null) {
            position = mExoPlayer.getCurrentPosition();
            state = mExoPlayer.getPlayWhenReady();
            mExoPlayer.stop();
            mExoPlayer.release();
            mExoPlayer = null;

        }
    }



    @Override
    public void onResume() {
        super.onResume();
        if (videoURIGlobal != null)
            getVideoPlayerReady(videoURIGlobal);
    }


    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putBoolean(PLAY_STATE,state);
        outState.putLong(SELECTED_POSITION,position);

    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (savedInstanceState != null) {
            state = savedInstanceState.getBoolean(PLAY_STATE,state);
            position = savedInstanceState.getLong(SELECTED_POSITION, C.TIME_UNSET);
            Log.i("POSITIONNNNN22222222222",String.valueOf(position));
            Log.i("temp22222222222",String.valueOf(temp));
        }
    }
}
