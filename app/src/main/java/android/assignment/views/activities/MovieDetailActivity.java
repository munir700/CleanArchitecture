package android.assignment.views.activities;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.arch.lifecycle.Observer;
import android.assignment.R;
import android.assignment.base.BaseActivity;
import android.assignment.databinding.ActivityMovieDetailBinding;
import android.assignment.enums.ViewModelEventsEnum;
import android.assignment.models.Movie;
import android.assignment.models.MovieListing;
import android.assignment.viewModels.MovieDetailViewModel;
import android.content.Intent;
import android.databinding.ViewDataBinding;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.util.Pair;
import android.view.View;

import com.google.android.exoplayer2.DefaultLoadControl;
import com.google.android.exoplayer2.DefaultRenderersFactory;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.upstream.DefaultHttpDataSourceFactory;
import com.google.android.exoplayer2.util.Util;

import java.util.List;

public class MovieDetailActivity extends BaseActivity<MovieDetailViewModel, ActivityMovieDetailBinding> {

    public static final int REQUEST_CODE = 100;
    public final static String LISTING_POSITION = "listing_index";
    public final static String MOVIES_INTENT_KEY = "movie_listing";


    MovieListing movieListing;
    Movie movieDetail;
    SimpleExoPlayer player;
    private boolean playWhenReady = false;
    private int currentWindow;
    private long playbackPosition;


    @Override
    public Class<MovieDetailViewModel> getViewModel() {
        return MovieDetailViewModel.class;
    }

    @Override
    public int getLayoutRes() {
        return R.layout.activity_movie_detail;
    }

    @Override
    public void onObserve(ViewModelEventsEnum event, Object eventMessage) {
        switch (event) {
            case NO_INTERNET_CONNECTION:
                binding.progressBarTitle.setVisibility(View.GONE);
                binding.tvDescription.setText(getString(R.string.STR_ERROR_FETCHING_DETIAL));
                break;
            case ON_API_REQUEST_FAILURE:
                binding.progressBarTitle.setVisibility(View.GONE);
                binding.tvDescription.setText(getString(R.string.STR_ERROR_FETCHING_DETIAL));
                break;
            case ON_API_CALL_START:
                binding.progressBarTitle.setVisibility(View.VISIBLE);
                break;
            case ON_API_CALL_STOP:
                binding.progressBarTitle.setVisibility(View.GONE);
                break;
            default:
                break;
        }
    }

    public static void openActivityForResult(Activity activity,
                                             MovieListing movieListing, int requestCode, int listingPosition) {

        Intent intent = new Intent(activity, MovieDetailActivity.class);
        intent.putExtra(MOVIES_INTENT_KEY, movieListing);
        intent.putExtra(LISTING_POSITION, listingPosition);
        activity.startActivityForResult(intent, requestCode);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        movieListing = (MovieListing) getIntent().getSerializableExtra(MOVIES_INTENT_KEY);
    }


    @Override
    public void onPause() {
        super.onPause();
        if (Util.SDK_INT <= 23) {
            releasePlayer();
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        if (Util.SDK_INT > 23) {
            releasePlayer();
        }
    }

    @SuppressLint("InlinedApi")
    private void hideSystemUi() {
        binding.epVideoView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LOW_PROFILE
                | View.SYSTEM_UI_FLAG_FULLSCREEN
                | View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);
    }

    private void loadMovieDetail() {
        viewModel.getMovieDetail(movieListing.getId()).observe(this, new Observer<Movie>() {
            @Override
            public void onChanged(@Nullable Movie movie) {
                movieDetail = movie;
                binding.setMovie(movieDetail);
                binding.rlDetails.setGravity(View.VISIBLE);
                initializePlayer();
            }
        });
    }


    @Override
    public void onResume() {
        super.onResume();
        //hideSystemUi();
        loadMovieDetail();
    }

    private void initializePlayer() {
        player = ExoPlayerFactory.newSimpleInstance(
                new DefaultRenderersFactory(this),
                new DefaultTrackSelector(), new DefaultLoadControl());

        binding.epVideoView.setPlayer(player);

        player.setPlayWhenReady(playWhenReady);
        player.seekTo(currentWindow, playbackPosition);

        Uri uri = Uri.parse("https://storage.googleapis.com/exoplayer-test-media-1/mkv/android-screens-lavf-56.36.100-aac-avc-main-1280x720.mkv");
        MediaSource mediaSource = buildMediaSource(uri);
        player.prepare(mediaSource, true, false);
    }

    private MediaSource buildMediaSource(Uri uri) {
        return new ExtractorMediaSource.Factory(
                new DefaultHttpDataSourceFactory("exoplayer-codelab")).
                createMediaSource(uri);
    }

    private void releasePlayer() {
        if (player != null) {
            playbackPosition = player.getCurrentPosition();
            currentWindow = player.getCurrentWindowIndex();
            playWhenReady = player.getPlayWhenReady();
            player.release();
            player = null;
        }
    }
}
