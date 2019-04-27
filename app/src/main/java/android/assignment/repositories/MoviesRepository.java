package android.assignment.repositories;

import android.arch.lifecycle.MutableLiveData;
import android.assignment.BuildConfig;
import android.assignment.R;
import android.assignment.api.ApiService;
import android.assignment.base.BaseNetworkCallBack;
import android.assignment.base.BaseViewModel;
import android.assignment.enums.ViewModelEventsEnum;
import android.assignment.models.Movie;
import android.assignment.models.MovieListing;
import android.assignment.utils.NetworkUtils;

import java.util.List;

import javax.inject.Inject;

import retrofit2.Call;
import retrofit2.Response;

public class MoviesRepository {

    @Inject
    ApiService apiService;


    @Inject
    NetworkUtils networkUtils;

    @Inject
    public MoviesRepository() {

    }

    public MutableLiveData<List<MovieListing>> getMoviesList(final BaseViewModel viewModel, Call<List<MovieListing>> listCall) {

        final MutableLiveData<List<MovieListing>> moviesLiveData = new MutableLiveData<>();
        if (networkUtils.isConnectedToInternet()) {
            if (listCall != null)
                listCall.cancel();

            listCall = apiService.getMovieList(BuildConfig.API_KEY, "en-US", 4);
            listCall.enqueue(new BaseNetworkCallBack<List<MovieListing>>(viewModel) {
                @Override
                public void onResponse(Call<List<MovieListing>> call, Response<List<MovieListing>> response) {
                    super.onResponse(call, response);
                    if (!call.isCanceled() && response.isSuccessful()) {
                        moviesLiveData.postValue(response.body());
                    }
                }

                @Override
                public void onFailure(Call<List<MovieListing>> call, Throwable throwable) {
                    super.onFailure(call, throwable);
                }
            });

        } else {
            viewModel.notifyObserver(ViewModelEventsEnum.NO_INTERNET_CONNECTION, viewModel.getAppManager().getContext().getString(R.string.NO_INTERNET_CONNECTIVITY));
        }
        return moviesLiveData;
    }


    public MutableLiveData<Movie> getMovieDetail(final BaseViewModel viewModel, Call<Movie> listCall, String movieId) {

        final MutableLiveData<Movie> moviesLiveData = new MutableLiveData<>();
        if (networkUtils.isConnectedToInternet()) {
            if (listCall != null)
                listCall.cancel();

            listCall = apiService.getMovieDetail(Integer.valueOf(movieId), BuildConfig.API_KEY, "en-US" );
            listCall.enqueue(new BaseNetworkCallBack<Movie>(viewModel) {
                @Override
                public void onResponse(Call<Movie> call, Response<Movie> response) {
                    super.onResponse(call, response);
                    if (!call.isCanceled() && response.isSuccessful()) {
                        moviesLiveData.postValue(response.body());
                    }
                }

                @Override
                public void onFailure(Call<Movie> call, Throwable throwable) {
                    super.onFailure(call, throwable);
                }
            });

        } else {
            viewModel.notifyObserver(ViewModelEventsEnum.NO_INTERNET_CONNECTION, viewModel.getAppManager().getContext().getString(R.string.NO_INTERNET_CONNECTIVITY));
        }
        return moviesLiveData;
    }
}
