// AddMovieActivity.java
package com.example.pruebafinalappnativa;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import androidx.appcompat.app.AppCompatActivity;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import model.SearchResults;
import network.OMDbApiService;
import network.RetrofitClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddMovieActivity extends AppCompatActivity {

    private static final String TAG = "AddMovieActivity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_movie);

        EditText editTextImdbId = findViewById(R.id.editTextImdbId);
        Button buttonFetchMovie = findViewById(R.id.buttonFetchMovie);

        buttonFetchMovie.setOnClickListener(view -> {
            String searchTerm = editTextImdbId.getText().toString();
            fetchMoviesByTitle(searchTerm);
        });
    }

    private void fetchMoviesByTitle(String searchTerm) {
        OMDbApiService apiService = RetrofitClient.getApiService();
        Call<SearchResults> call = apiService.getMoviesBySearch(searchTerm, "3999787b");

        Log.d(TAG, "Iniciando búsqueda para: " + searchTerm);
        call.enqueue(new Callback<SearchResults>() {
            @Override
            public void onResponse(Call<SearchResults> call, Response<SearchResults> response) {
                Log.d(TAG, "Respuesta recibida: " + response.toString());
                if (response.isSuccessful() && response.body() != null) {
                    SearchResults results = response.body();
                    if ("True".equals(results.getResponse())) {
                        List<SearchResults.Movie> movies = results.getSearch();

                        ArrayList<String> movieTitles = new ArrayList<>();
                        ArrayList<String> movieYears = new ArrayList<>();
                        for (SearchResults.Movie movie : movies) {
                            movieTitles.add(movie.getTitle());
                            movieYears.add(movie.getYear().toString());
                        }

                        // Preparar el resultado para MainActivity
                        Intent returnIntent = new Intent();
                        returnIntent.putStringArrayListExtra("movieTitles", movieTitles);
                        returnIntent.putStringArrayListExtra("movieYears", movieYears);
                        setResult(Activity.RESULT_OK, returnIntent);
                        finish(); // Finaliza la actividad
                    }} else {
                    // Registro de respuesta fallida
                    Log.e(TAG, "Respuesta no exitosa: " + response.errorBody());

                }
            }

            @Override
            public void onFailure(Call<SearchResults> call, Throwable t) {
                Log.e(TAG, "Fallo en la llamada: " + t.getMessage(), t);
                // Error handling
            }
        });
    }


}
