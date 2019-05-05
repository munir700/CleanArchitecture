package android.assignment.views.activities;

import android.arch.lifecycle.Observer;
import android.assignment.R;
import android.assignment.adapter.ListingAdapter;
import android.assignment.base.BaseActivity;
import android.assignment.databinding.ActivityMoviesBinding;
import android.assignment.databinding.RowListingsBinding;
import android.assignment.enums.ErrorResponseEnum;
import android.assignment.enums.ViewModelEventsEnum;
import android.assignment.models.Movie;
import android.assignment.models.MovieListing;
import android.assignment.ui.SortDialog;
import android.assignment.utils.AppConstants;
import android.assignment.utils.ErrorResponse;
import android.assignment.viewModels.MovieViewModel;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.SimpleItemAnimator;
import android.util.Log;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

import static android.assignment.views.activities.MovieDetailActivity.REQUEST_CODE;

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
                binding.pullToRefresh.setVisibility(View.GONE);
                binding.constraintError.setVisibility(View.VISIBLE);
                viewModel.setErrorResponse(new ErrorResponse.Builder(ErrorResponseEnum.NO_INTERNET_CONNECTION).build());
                hideProgress();
                break;
            case ON_NO_DATA_RECEIVED:
                if (eventMessage != null) {
                    onApiRequestFailed(eventMessage.toString());
                }
                binding.pullToRefresh.setVisibility(View.GONE);
                binding.constraintError.setVisibility(View.VISIBLE);
                viewModel.listData.setValue(new ArrayList<MovieListing>());
                listingAdapter.setData(new ArrayList<MovieListing>());
                viewModel.setErrorResponse(new ErrorResponse.Builder(ErrorResponseEnum.NO_DATA_RECEIVED).build());
                break;
            case ON_API_REQUEST_FAILURE:
                binding.constraintError.setVisibility(View.VISIBLE);
                viewModel.setErrorResponse(new ErrorResponse.Builder(ErrorResponseEnum.API_REQUEST_FAILURE).build());
                break;
            case ON_API_CALL_START:
                binding.pullToRefresh.setVisibility(View.VISIBLE);
                binding.constraintError.setVisibility(View.GONE);
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
        binding.setVm(viewModel);
        binding.setCountText(viewModel.getMovieCount());
        initUI();
        loadMovies();
    }

    private void initUI() {

        try {
            ((SimpleItemAnimator) binding.recyclerResults.getItemAnimator()).setSupportsChangeAnimations(false);
            listingAdapter = new ListingAdapter(MoviesActivity.this, viewModel.getAppManager(), viewModel.listData, new ListingAdapter.OnClickListener() {
                @Override
                public void onItemClick(int position, MovieListing movie, RowListingsBinding binding) {
                    Movie movieDetail = new Movie();
                    movieDetail.toMovie(movie);
                    MovieDetailActivity.openActivityForResult(MoviesActivity.this, movieDetail, REQUEST_CODE, position);
                }
            });

            binding.recyclerResults.setAdapter(listingAdapter);
            binding.recyclerResults.setLayoutManager(new GridLayoutManager(this, 3));

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

            binding.sortIv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    openSortDialog(v);
                }
            });


        } catch (Exception e) {
            Log.e("Exception", "Error while initialize UI components and message =" + e.getMessage());
        }

    }

    private void loadMovies() {
        viewModel.getMovies().observe(this, new Observer<List<MovieListing>>() {
            @Override
            public void onChanged(@Nullable List<MovieListing> movies) {
                List<MovieListing> movieList = movies;
                viewModel.setMovieCount(viewModel.getPreferenceHandler().getLastSelectedSortTitle() + " # " + movieList.size());
                listingAdapter.setData(movieList);
            }
        });
    }

    public void openSortDialog(View v) {

        final SortDialog propertySortDialog = new SortDialog(MoviesActivity.this, AppConstants.moviesSortModelList, viewModel.getPreferenceHandler());
        propertySortDialog.setCancelable(true);
        if (propertySortDialog.getWindow() != null)
            propertySortDialog.getWindow().getAttributes().windowAnimations = R.style.dialogAnimation;
        propertySortDialog.show();
        propertySortDialog.getWindow().getDecorView().setBackgroundResource(android.R.color.transparent);
        propertySortDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialogInterface) {
                int selection = propertySortDialog.getSelectedPosition();
                if (selection != SortDialog.INITIAL_POSITION) {
                    loadMovies();
                }

            }
        });
    }

}
