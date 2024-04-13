package com.example.pruebafinalappnativa;

import android.content.Intent;
import android.os.Bundle;
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
        // Esta función debería actualizar el RecyclerView con las películas seleccionadas.
        movieAdapter.setSelectedMovies(selectedMovies); // Pasar las películas seleccionadas al adaptador.
        movieAdapter.notifyDataSetChanged(); // Notificar al adaptador que los datos han cambiado para refrescar la vista.
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_CODE_ADD_MOVIE && resultCode == RESULT_OK && data != null) {
            ArrayList<Movie> newSelectedMovies = data.getParcelableArrayListExtra("selectedMovies");
            if (newSelectedMovies != null) {
                selectedMovies.addAll(newSelectedMovies);
                moviesList.clear();
                moviesList.addAll(selectedMovies);
                movieAdapter.setSelectedMovies(selectedMovies);
                movieAdapter.notifyDataSetChanged();
            }
        }
    }



}
