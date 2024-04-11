package network;

import model.MovieDetails;
import model.SearchResults;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface OMDbApiService {
    @GET("/")
    Call<SearchResults> getMoviesBySearch(@Query("s") String searchTerm, @Query("apikey") String apiKey);
}

