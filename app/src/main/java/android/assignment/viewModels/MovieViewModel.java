package android.assignment.viewModels;

import android.arch.lifecycle.MutableLiveData;
import android.assignment.BR;
import android.assignment.base.BaseViewModel;
import android.assignment.models.MovieListing;
import android.assignment.preferences.PreferenceHandler;
import android.assignment.repositories.MoviesRepository;
import android.assignment.utils.ErrorResponse;
import android.databinding.Bindable;
import android.databinding.ObservableField;
import android.util.Log;

import java.util.Iterator;
import java.util.List;

import javax.inject.Inject;

import retrofit2.Call;

public class MovieViewModel extends BaseViewModel {

    private ObservableField<String> movieCount = new ObservableField<>();
    public MutableLiveData<List<MovieListing>> listData = new MutableLiveData<>();

    private ErrorResponse errorResponse;

    Call<List<MovieListing>> listCall;


    @Inject
    PreferenceHandler preferenceHandler;

    @Inject
    MoviesRepository moviesRepository;

    @Inject
    public MovieViewModel() {

    }


    public ObservableField<String> getMovieCount() {
        return movieCount;
    }

    public void setMovieCount(String movieCount) {
        this.movieCount.set(movieCount);
    }


    public PreferenceHandler getPreferenceHandler() {
        return preferenceHandler;
    }

    @Bindable
    public ErrorResponse getErrorResponse() {
        return errorResponse;
    }

    public void setErrorResponse(ErrorResponse errorResponse) {
        this.errorResponse = errorResponse;
        notifyPropertyChanged(BR.errorResponse);
    }

    public MutableLiveData<List<MovieListing>> getMovies() {
        return moviesRepository.getMoviesList(this, listCall, preferenceHandler.getLastSelectedSort());
    }

    public List<MovieListing> removeAdultMovies(List<MovieListing> listData) {
        for (Iterator<MovieListing> iterator = listData.iterator(); iterator.hasNext(); ) {
            MovieListing movie = iterator.next();
            if (movie.getAdult()) {
                Log.e("removed movie", movie.getTitle());
                iterator.remove();
            }
        }
        return listData;
    }

}
