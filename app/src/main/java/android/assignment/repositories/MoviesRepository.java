package android.assignment.repositories;

import android.arch.lifecycle.MutableLiveData;
import android.assignment.BuildConfig;
import android.assignment.R;
import android.assignment.api.ApiEnvelopeService;
import android.assignment.api.ApiService;
import android.assignment.base.BaseNetworkCallBack;
import android.assignment.base.BaseViewModel;
import android.assignment.enums.ViewModelEventsEnum;
import android.assignment.models.ArrayListWithTotalResultCount;
import android.assignment.models.Movie;
import android.assignment.models.MovieListing;
import android.assignment.models.Videos;
import android.assignment.utils.NetworkUtils;

import java.util.List;

import javax.inject.Inject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MoviesRepository {

    @Inject
    ApiService apiService;

    @Inject
    ApiEnvelopeService apiEnvelopeService;

    @Inject
    NetworkUtils networkUtils;

    @Inject
    public MoviesRepository() {

    }

    public MutableLiveData<ArrayListWithTotalResultCount<MovieListing>> getMoviesList(final BaseViewModel viewModel, Call<ArrayListWithTotalResultCount<MovieListing>> listCall, final String playingType) {

        final MutableLiveData<ArrayListWithTotalResultCount<MovieListing>> moviesLiveData = new MutableLiveData<>();
        if (networkUtils.isConnectedToInternet()) {
            if (listCall != null)
                listCall.cancel();
            listCall = apiEnvelopeService.getMovieList(playingType, BuildConfig.API_KEY, "en-US", 4);
            listCall.enqueue(new BaseNetworkCallBack<ArrayListWithTotalResultCount<MovieListing>>(viewModel) {
                @Override
                public void onResponse(Call<ArrayListWithTotalResultCount<MovieListing>> call, Response<ArrayListWithTotalResultCount<MovieListing>> response) {
                    super.onResponse(call, response);
                    if (!call.isCanceled() && response.isSuccessful()) {
                        moviesLiveData.postValue(response.body());
                    }
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

            listCall = apiService.getMovieDetail(Integer.valueOf(movieId), BuildConfig.API_KEY, "en-US");
            listCall.enqueue(new BaseNetworkCallBack<Movie>(viewModel) {
                @Override
                public void onResponse(Call<Movie> call, Response<Movie> response) {
                    super.onResponse(call, response);
                    if (!call.isCanceled() && response.isSuccessful()) {
                        moviesLiveData.postValue(response.body());
                    }
                }
            });

        } else {
            viewModel.notifyObserver(ViewModelEventsEnum.NO_INTERNET_CONNECTION, viewModel.getAppManager().getContext().getString(R.string.NO_INTERNET_CONNECTIVITY));
        }
        return moviesLiveData;
    }

    public MutableLiveData<Videos> getMovieVideo(final BaseViewModel viewModel, Call<Videos> listCall, String movieId) {

        final MutableLiveData<Videos> moviesLiveData = new MutableLiveData<>();
        if (networkUtils.isConnectedToInternet()) {
            if (listCall != null)
                listCall.cancel();

            listCall = apiEnvelopeService.getMovieVideo(Integer.valueOf(movieId), BuildConfig.API_KEY, "en-US");
            listCall.enqueue(new Callback<Videos>() {
                @Override
                public void onResponse(Call<Videos> call, Response<Videos> response) {
                    if (!call.isCanceled() && response.isSuccessful()) {
                        moviesLiveData.postValue(response.body());
                    }
                }

                @Override
                public void onFailure(Call<Videos> call, Throwable t) {

                }
            });

        } else {
            viewModel.notifyObserver(ViewModelEventsEnum.NO_INTERNET_CONNECTION, viewModel.getAppManager().getContext().getString(R.string.NO_INTERNET_CONNECTIVITY));
        }
        return moviesLiveData;
    }
}
