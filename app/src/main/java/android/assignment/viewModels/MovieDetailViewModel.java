package android.assignment.viewModels;

import android.arch.lifecycle.MutableLiveData;
import android.assignment.BR;
import android.assignment.base.BaseViewModel;
import android.assignment.models.Movie;
import android.assignment.models.MovieListing;
import android.assignment.repositories.MoviesRepository;
import android.assignment.utils.ErrorResponse;
import android.databinding.Bindable;
import android.databinding.ObservableField;
import android.util.Log;

import java.util.List;

import javax.inject.Inject;

import retrofit2.Call;

public class MovieDetailViewModel extends BaseViewModel {


    private ErrorResponse errorResponse;

    Call<Movie> listCall;

    @Inject
    MoviesRepository moviesRepository;

    @Inject
    public MovieDetailViewModel() {

    }

    @Bindable
    public ErrorResponse getErrorResponse() {
        return errorResponse;
    }

    public void setErrorResponse(ErrorResponse errorResponse) {
        this.errorResponse = errorResponse;
        notifyPropertyChanged(BR.errorResponse);
    }

    public MutableLiveData<Movie> getMovieDetail(String moviezId) {
        return moviesRepository.getMovieDetail(this, listCall, moviezId);
    }

    public List<MovieListing> removeAdultMovies(List<MovieListing> listData) {
        for (MovieListing movie : listData) {
            if (!movie.getAdult()) {
                Log.e("removed movie", movie.getTitle());
                listData.remove(movie);
            }
        }
        return listData;
    }

}
