package com.example.pruebafinalappnativa;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import adapter.MovieAdapter;
import model.Movie;
import model.SearchResults;
import network.OMDbApiService;
import network.RetrofitClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddMovieActivity extends AppCompatActivity {

    private static final String TAG = "AddMovieActivity";
    private RecyclerView recyclerViewMovies;
    private MovieAdapter adapter;
    private List<Movie> moviesList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_movie);

        EditText editTextSearchTerm = findViewById(R.id.editTextSearchTerm);
        Button buttonFetchMovie = findViewById(R.id.buttonFetchMovie);
        recyclerViewMovies = findViewById(R.id.recyclerViewMovies);
        moviesList = new ArrayList<>();
        adapter = new MovieAdapter(moviesList);

        recyclerViewMovies.setLayoutManager(new LinearLayoutManager(this));
        recyclerViewMovies.setAdapter(adapter);

        buttonFetchMovie.setOnClickListener(view -> {
            String searchTerm = editTextSearchTerm.getText().toString();
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

                        // Transforma los resultados en una lista de objetos Movie
                        List<Movie> movieList = new ArrayList<>();
                        for (SearchResults.Movie resultMovie : movies) {
                            Movie movie = new Movie(
                                    resultMovie.getTitle(),       // Título de la película
                                    resultMovie.getYear().toString(), // Año de la película como String
                                    resultMovie.getImdbID(),     // ID de IMDb de la película
                                    resultMovie.getType(),       // Tipo de la película
                                    resultMovie.getPoster()      // URL del póster de la película
                            );
                            movieList.add(movie);
                        }

                        // Actualiza la lista de películas del adaptador
                        moviesList.clear();
                        moviesList.addAll(movieList);
                        adapter.notifyDataSetChanged(); // Notifica al adaptador sobre el cambio de datos
                    }
                } else {
                    Log.e(TAG, "Respuesta no exitosa: " + response.errorBody().toString());
                }
            }

            @Override
            public void onFailure(Call<SearchResults> call, Throwable t) {
                Log.e(TAG, "Fallo en la llamada: " + t.getMessage(), t);
                // Aquí podrías mostrar un mensaje al usuario o actualizar la UI para reflejar el error
            }
        });
    }
}
