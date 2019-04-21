package android.assignment.repositories;

import android.arch.lifecycle.MutableLiveData;
import android.assignment.BuildConfig;
import android.assignment.R;
import android.assignment.api.ApiService;
import android.assignment.base.BaseNetworkCallBack;
import android.assignment.base.BaseViewModel;
import android.assignment.enums.ViewModelEventsEnum;
import android.assignment.models.Movie;
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

    public MutableLiveData<List<Movie>> getMoviesList(final BaseViewModel viewModel, Call<List<Movie>> listCall) {

        final MutableLiveData<List<Movie>> moviesLiveData = new MutableLiveData<>();
        if (networkUtils.isConnectedToInternet()) {
            if (listCall != null)
                listCall.cancel();

            listCall = apiService.getMovieList(550, BuildConfig.API_KEY, "en-US", 1);
            listCall.enqueue(new BaseNetworkCallBack<List<Movie>>(viewModel) {
                @Override
                public void onResponse(Call<List<Movie>> call, Response<List<Movie>> response) {
                    super.onResponse(call, response);
                    if (!call.isCanceled() && response.isSuccessful()) {
                        moviesLiveData.postValue(response.body());
                    }
                }

                @Override
                public void onFailure(Call<List<Movie>> call, Throwable throwable) {
                    super.onFailure(call, throwable);
                }
            });

        } else {
            viewModel.notifyObserver(ViewModelEventsEnum.NO_INTERNET_CONNECTION, viewModel.getAppManager().getContext().getString(R.string.NO_INTERNET_CONNECTIVITY));
        }
        return moviesLiveData;
    }
}
