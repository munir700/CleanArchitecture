package android.assignment.views.activities;

import android.app.Activity;
import android.arch.lifecycle.Observer;
import android.assignment.R;
import android.assignment.base.BaseActivity;
import android.assignment.models.Movie;
import android.assignment.models.MovieListing;
import android.assignment.viewModels.MovieDetailViewModel;
import android.content.Intent;
import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.util.Pair;
import android.view.View;

import java.util.List;

public class MovieDetailActivity extends BaseActivity<MovieDetailViewModel, ViewDataBinding> {

    public static final int REQUEST_CODE = 100;
    public final static String LISTING_POSITION = "listing_index";
    public final static String MOVIES_INTENT_KEY = "movie_listing";


    MovieListing movieListing;

    @Override
    public Class<MovieDetailViewModel> getViewModel() {
        return MovieDetailViewModel.class;
    }

    @Override
    public int getLayoutRes() {
        return R.layout.activity_movie_detail;
    }


    public static void openActivityForResult(Activity activity,
                                             MovieListing movieListing,
                                             final View thumb, int requestCode, int listingPosition) {

        Intent intent = new Intent(activity, MovieDetailActivity.class);
        intent.putExtra(MOVIES_INTENT_KEY, movieListing);
        intent.putExtra(LISTING_POSITION, listingPosition);
       /* if (thumb != null) {
            Pair<View, String> p1;
            ActivityOptionsCompat options;

            p1 = Pair.create(thumb, activity.getResources()
                    .getString(R.string.STR_NEWS_DETAILS_TRANSITION));
            options = ActivityOptionsCompat.makeSceneTransitionAnimation(activity,
                    p1);

            activity.startActivityForResult(intent, requestCode, options.toBundle());
        } else {*/
        activity.startActivityForResult(intent, requestCode);
        //}
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_detail);
        movieListing = (MovieListing) getIntent().getSerializableExtra(MOVIES_INTENT_KEY);
        loadMovieDetail();
    }

    private void loadMovieDetail() {
        viewModel.getMovieDetail(movieListing.getId()).observe(this, new Observer<Movie>() {
            @Override
            public void onChanged(@Nullable Movie movie) {

            }
        });
    }
}
