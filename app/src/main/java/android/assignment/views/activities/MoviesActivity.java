package android.assignment.views.activities;

import android.arch.lifecycle.Observer;
import android.assignment.R;
import android.assignment.base.BaseActivity;
import android.assignment.databinding.ActivityMoviesBinding;
import android.assignment.enums.ViewModelEventsEnum;
import android.assignment.models.Movie;
import android.assignment.viewModels.MovieViewModel;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;

import java.util.List;

public class MoviesActivity extends BaseActivity<MovieViewModel, ActivityMoviesBinding> {

    @Override
    public Class<MovieViewModel> getViewModel() {
        return MovieViewModel.class;
    }

    @Override
    public int getLayoutRes() {
        return R.layout.activity_movies;
    }

    @Override
    public void onObserve(ViewModelEventsEnum event, Object eventMessage) {
        super.onObserve(event, eventMessage);
        switch (event) {
            case NO_INTERNET_CONNECTION:
                onApiRequestFailed(getString(R.string.NO_INTERNET_CONNECTIVITY));
                break;
            case ON_NO_DATA_RECEIVED:
                onApiRequestFailed(eventMessage.toString());
                break;
            case ON_API_REQUEST_FAILURE:
                onApiRequestFailed(eventMessage.toString());
                break;
            case ON_API_CALL_START:
                showProgress();
                break;
            case ON_API_CALL_STOP:
                hideProgress();
                break;
            default:
                break;
        }
    }

    public static Intent newIntent(Context context) {
        Intent intent = new Intent(context, MoviesActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        return intent;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel.getMovies().observe(this, new Observer<List<Movie>>() {
            @Override
            public void onChanged(@Nullable List<Movie> movies) {

            }
        });
    }
}
