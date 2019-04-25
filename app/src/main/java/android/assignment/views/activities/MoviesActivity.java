package android.assignment.views.activities;

import android.arch.lifecycle.Observer;
import android.assignment.R;
import android.assignment.adapter.ListingAdapter;
import android.assignment.base.BaseActivity;
import android.assignment.databinding.ActivityMoviesBinding;
import android.assignment.databinding.RowListingsBinding;
import android.assignment.enums.ViewModelEventsEnum;
import android.assignment.models.Movie;
import android.assignment.viewModels.MovieViewModel;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.SimpleItemAnimator;
import android.util.Log;
import android.view.View;

import java.util.List;

public class MoviesActivity extends BaseActivity<MovieViewModel, ActivityMoviesBinding> {

    private ListingAdapter listingAdapter;

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
        initUI();
        loadMovies();
    }

    private void initUI() {

        try {
            ((SimpleItemAnimator) binding.recyclerResults.getItemAnimator()).setSupportsChangeAnimations(false);
            listingAdapter = new ListingAdapter(MoviesActivity.this, viewModel.getAppManager(), viewModel.listData, new ListingAdapter.OnClickListener() {
                @Override
                public void onItemClick(int position, Movie movie, RowListingsBinding binding) {


                }
            });

            binding.recyclerResults.setAdapter(listingAdapter);


            binding.searchAgainBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    loadMovies();
                }
            });

            binding.pullToRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
                @Override
                public void onRefresh() {
                    binding.pullToRefresh.setRefreshing(false);
                    loadMovies();
                }
            });


        } catch (Exception e) {
            Log.e("Exception", "Error while initialize UI components and message =" + e.getMessage());
        }

    }

    private void loadMovies() {
        viewModel.getMovies().observe(this, new Observer<List<Movie>>() {
            @Override
            public void onChanged(@Nullable List<Movie> movies) {
                listingAdapter.setData(movies);
            }
        });
    }

}
