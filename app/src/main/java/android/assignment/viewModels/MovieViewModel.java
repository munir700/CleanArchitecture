package android.assignment.viewModels;

import android.arch.lifecycle.MutableLiveData;
import android.assignment.base.BaseViewModel;
import android.assignment.models.Movie;
import android.assignment.repositories.MoviesRepository;
import android.assignment.utils.ErrorResponse;
import android.databinding.Bindable;

import java.util.List;

import javax.inject.Inject;

import retrofit2.Call;

public class MovieViewModel extends BaseViewModel {

    public MutableLiveData<List<Movie>> listData = new MutableLiveData<>();

    private ErrorResponse errorResponse;

    Call<List<Movie>> listCall;

    @Inject
    MoviesRepository moviesRepository;

    @Inject
    public MovieViewModel() {

    }


    @Bindable
    public ErrorResponse getErrorResponse() {
        return errorResponse;
    }

    public void setErrorResponse(ErrorResponse errorResponse) {
        this.errorResponse = errorResponse;
        //notifyPropertyChanged(BR.errorResponse);
    }

    public MutableLiveData<List<Movie>> getMovies() {
        return moviesRepository.getMoviesList(this, listCall);
    }


}