package com.example.pruebafinalappnativa;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import adapter.MovieAdapter;
import model.Movie;

public class MainActivity extends AppCompatActivity implements MovieAdapter.OnMovieSelectionListener {

    private static final int REQUEST_CODE_ADD_MOVIE = 1;
    private RecyclerView recyclerViewMovies;
    private FloatingActionButton fabAddMovie;
    private List<Movie> moviesList;
    private MovieAdapter movieAdapter;
    private Set<Movie> selectedMovies = new HashSet<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        recyclerViewMovies = findViewById(R.id.recycler_view_movies);
        recyclerViewMovies.setLayoutManager(new LinearLayoutManager(this));

        moviesList = new ArrayList<>();
        movieAdapter = new MovieAdapter(moviesList, selectedMovies, this, true);
        recyclerViewMovies.setAdapter(movieAdapter);

        fabAddMovie = findViewById(R.id.add_movie_fab);
        fabAddMovie.setOnClickListener(view -> {
            Intent intent = new Intent(MainActivity.this, AddMovieActivity.class);
            startActivityForResult(intent, REQUEST_CODE_ADD_MOVIE);
        });
        refreshSelectedMoviesView();
    }

    @Override
    public void onMovieSelectionChanged(Movie movie, boolean isSelected) {
        if (isSelected) {
            selectedMovies.add(movie);
        } else {
            selectedMovies.remove(movie);
        }
        refreshSelectedMoviesView();
    }

    private void refreshSelectedMoviesView() {
        if (selectedMovies.isEmpty()) {
            // Si no hay películas seleccionadas, mostrar la tarjeta de estado vacío.
            findViewById(R.id.empty_state_card).setVisibility(View.VISIBLE);
            recyclerViewMovies.setVisibility(View.GONE);
        } else {
            // Si hay películas seleccionadas, actualizar la lista y mostrar el RecyclerView.
            findViewById(R.id.empty_state_card).setVisibility(View.GONE);
            recyclerViewMovies.setVisibility(View.VISIBLE);

            // Actualiza la lista que el adaptador está usando y notifica al adaptador.
            moviesList.clear();
            moviesList.addAll(selectedMovies);
            movieAdapter.notifyDataSetChanged();
        }
    }



    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_CODE_ADD_MOVIE && resultCode == RESULT_OK && data != null) {
            ArrayList<Movie> newSelectedMovies = data.getParcelableArrayListExtra("selectedMovies");
            if (newSelectedMovies != null) {
                selectedMovies.addAll(newSelectedMovies);
                refreshSelectedMoviesView(); // Actualiza la vista después de añadir las películas
            }
        }
    }



}
