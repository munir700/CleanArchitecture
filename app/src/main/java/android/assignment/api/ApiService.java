package android.assignment.api;

import android.assignment.models.Movie;
import android.assignment.models.MovieListing;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ApiService {

    @GET("movie/{movie_id}")
    Call<Movie> getMovieDetail(@Path("movie_id") int movieId, @Query("api_key") String apiKey, @Query("language") String lang);

}
