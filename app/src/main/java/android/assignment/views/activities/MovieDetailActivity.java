package android.assignment.views.activities;

import android.app.Activity;
import android.arch.lifecycle.Observer;
import android.assignment.BuildConfig;
import android.assignment.R;
import android.assignment.base.BaseActivity;
import android.assignment.databinding.ActivityMovieDetailBinding;
import android.assignment.enums.ViewModelEventsEnum;
import android.assignment.models.Movie;
import android.assignment.models.MovieListing;
import android.assignment.viewModels.MovieDetailViewModel;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.Priority;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.Target;


public class MovieDetailActivity extends BaseActivity<MovieDetailViewModel, ActivityMovieDetailBinding> {

    public static final int REQUEST_CODE = 100;
    public final static String LISTING_POSITION = "listing_index";
    public final static String MOVIES_INTENT_KEY = "movie_detail";


    Movie movieDetail;

    public static void openActivityForResult(Activity activity,
                                             Movie movie, int requestCode, int listingPosition) {

        Intent intent = new Intent(activity, MovieDetailActivity.class);
        intent.putExtra(MOVIES_INTENT_KEY, movie);
        intent.putExtra(LISTING_POSITION, listingPosition);
        activity.startActivityForResult(intent, requestCode);
    }

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        movieDetail = getIntent().getParcelableExtra(MOVIES_INTENT_KEY);
        loadMovieDetail();
        initImagePlaceHolder();
        binding.tvDescription.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadMovieDetail();
            }
        });
    }

    public void onClose(View view) {
        onBackPressed();
    }

    private void initImagePlaceHolder() {
        binding.ivPlaceholder.setScaleType(ImageView.ScaleType.CENTER_CROP);

        String imgUrl = BuildConfig.IMG_BASE_URL_LARGE + movieDetail.getPosterPath();

        RequestOptions imageOptions = new RequestOptions()
                .placeholder(R.drawable.img_loading_pics)
                .error(R.drawable.img_loading_pics)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .priority(Priority.HIGH).override(Target.SIZE_ORIGINAL, Target.SIZE_ORIGINAL);
        Glide.with(this)
                .load(imgUrl)
                .apply(imageOptions)
                .into(binding.ivPlaceholder);
        binding.moreMediaTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (movieDetail != null) {
                    openImageSilderActivity();
                }
            }
        });

    }

    private void loadMovieDetail() {
        viewModel.getMovieDetail(movieDetail.getId()).observe(this, new Observer<Movie>() {
            @Override
            public void onChanged(@Nullable Movie movie) {
                movieDetail = movie;
                binding.setMovie(movieDetail);
                binding.rlDetails.setGravity(View.VISIBLE);
            }
        });
    }

    private void openImageSilderActivity() {
        Intent fullMediaqIntent = new Intent(this, ImageSliderActivity.class);
        fullMediaqIntent.putExtra(ImageSliderActivity.MOVIE, movieDetail);
        startActivityForResult(fullMediaqIntent, REQUEST_CODE);
    }
}
