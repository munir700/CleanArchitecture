package android.assignment.views.activities;

import android.annotation.SuppressLint;
import android.assignment.R;
import android.assignment.adapter.PhotoSliderAdapter;
import android.assignment.base.BaseActivity;
import android.assignment.base.BaseViewModel;
import android.assignment.databinding.ActivityImageSliderBinding;
import android.assignment.models.Movie;
import android.assignment.models.ProductionCompanies;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.util.Log;
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

public class ImageSliderActivity extends BaseActivity<BaseViewModel, ActivityImageSliderBinding> implements View.OnClickListener {

    private final static String TAG = ImageSliderActivity.class.getCanonicalName();
    public static final String MOVIE = "movies";

    SimpleExoPlayer player;
    private boolean playWhenReady = false;
    private int currentWindow;
    private long playbackPosition;


    private PhotoSliderAdapter mySliderPagerAdapter;
    int pagerPosition;
    private Movie movie;


    @Override
    public Class<BaseViewModel> getViewModel() {
        return BaseViewModel.class;
    }

    @Override
    public int getLayoutRes() {
        return R.layout.activity_image_slider;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initUI();
    }


    private void initUI() {
        try {

            Toolbar toolbar = binding.layoutToolbar.toolbar;
            toolbar.getBackground().mutate();
            toolbar.getBackground().setAlpha(0);
            setSupportActionBar(toolbar);
            getSupportActionBar().setDisplayHomeAsUpEnabled(false);
            getSupportActionBar().setDisplayShowHomeEnabled(false);
            getSupportActionBar().setTitle("");

            findViewById(R.id.next_ib).setOnClickListener(this);
            findViewById(R.id.previous_ib).setOnClickListener(this);


            Bundle intent = getIntent().getExtras();
            if (intent != null) {
                movie = intent.containsKey(MOVIE) ? (Movie) intent.getParcelable(MOVIE) : null;
                setPhotoSlider();
                initializePlayer();
            }
            if (mySliderPagerAdapter.getCount() <= 1) {
                binding.previousIb.setVisibility(View.GONE);
                binding.nextIb.setVisibility(View.GONE);
            }

        } catch (Exception e) {
            Log.e(TAG, "Error while initialize UI components and message =" + e.getMessage());
        }

    }

    private void setPhotoSlider() {
        final ProductionCompanies[] productionCompanies = movie != null && movie.getProductionCompanies() != null ? movie.getProductionCompanies() : new ProductionCompanies[0];
        if (mySliderPagerAdapter == null) {
            mySliderPagerAdapter = new PhotoSliderAdapter(this, productionCompanies);
            binding.viewPager.setAdapter(mySliderPagerAdapter);
            binding.viewPager.setOffscreenPageLimit(4);
        } else {
            mySliderPagerAdapter.setPhotos(productionCompanies);
        }

        binding.viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                setEnableDisable(position, productionCompanies.length);
            }

            @Override
            public void onPageSelected(int position) {
                pagerPosition = position;
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        binding.viewPager.setCurrentItem(0);
    }

    private void setEnableDisable(int position, int totalSize) {
        if (position == 0) {
            binding.previousIb.setColorFilter(ContextCompat.getColor(this, R.color.text_color_1));
        } else {
            binding.previousIb.setColorFilter(ContextCompat.getColor(this, R.color.white));
        }
        if (position == totalSize - 1) {
            binding.nextIb.setColorFilter(ContextCompat.getColor(this, R.color.text_color_1));
        } else {
            binding.nextIb.setColorFilter(ContextCompat.getColor(this, R.color.white));
        }
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

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.next_ib:
                binding.viewPager.setCurrentItem(binding.viewPager.getCurrentItem() + 1, true);
                break;
            case R.id.previous_ib:
                binding.viewPager.setCurrentItem(binding.viewPager.getCurrentItem() - 1, true);
                break;
            default:
                break;
        }

    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return false;
    }

    public void onBackButtonPressed(View view) {
        onSupportNavigateUp();
    }


}
