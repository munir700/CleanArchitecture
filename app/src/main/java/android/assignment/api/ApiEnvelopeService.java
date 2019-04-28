package android.assignment.api;

import android.assignment.models.MovieListing;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ApiEnvelopeService {

    @GET("movie/now_playing/")
    Call<List<MovieListing>> getMovieList(@Query("api_key") String apiKey, @Query("language") String lang, @Query("page") int page);


}
