package com.example.pruebafinalappnativa;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

// Importación de las clases necesarias para usar AppCompatActivity, RecyclerView, etc.
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

// Importación de estructuras de datos de Java y clases de modelos y adaptadores personalizados.
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import adapter.MovieAdapter;
import model.Movie;

// La clase MainActivity hereda de AppCompatActivity e implementa la interfaz OnMovieSelectionListener.
public class MainActivity extends AppCompatActivity implements MovieAdapter.OnMovieSelectionListener {

    // Código de solicitud para identificar el resultado de la actividad AddMovie.
    private static final int REQUEST_CODE_ADD_MOVIE = 1;
    private RecyclerView recyclerViewMovies;
    private FloatingActionButton fabAddMovie;
    private List<Movie> moviesList; // Lista para almacenar las películas.
    private MovieAdapter movieAdapter; // Adaptador para el RecyclerView.
    private Set<Movie> selectedMovies = new HashSet<>(); // Conjunto para almacenar las películas seleccionadas.

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main); // Asignar el layout de la actividad.

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar); // Configurar la barra de herramientas como la barra de la app.

        recyclerViewMovies = findViewById(R.id.recycler_view_movies);
        recyclerViewMovies.setLayoutManager(new LinearLayoutManager(this)); // Configurar el RecyclerView.

        // Inicialización de la lista de películas y configuración del adaptador.
        moviesList = new ArrayList<>();
        movieAdapter = new MovieAdapter(moviesList, selectedMovies, this, true);
        recyclerViewMovies.setAdapter(movieAdapter);

        // Configuración del botón flotante de acción para agregar películas.
        fabAddMovie = findViewById(R.id.add_movie_fab);
        fabAddMovie.setOnClickListener(view -> {
            Intent intent = new Intent(MainActivity.this, AddMovieActivity.class);
            startActivityForResult(intent, REQUEST_CODE_ADD_MOVIE); // Iniciar AddMovieActivity esperando un resultado.
        });
        refreshSelectedMoviesView(); // Actualizar la vista de películas seleccionadas.
    }

    // Manejo del cambio en la selección de películas.
    @Override
    public void onMovieSelectionChanged(Movie movie, boolean isSelected) {
        if (isSelected) {
            selectedMovies.add(movie);
        } else {
            selectedMovies.remove(movie);
        }
        refreshSelectedMoviesView(); // Refrescar la vista de películas seleccionadas.
    }

    // Método para actualizar la vista cuando cambia el conjunto de películas seleccionadas.
    private void refreshSelectedMoviesView() {
        if (selectedMovies.isEmpty()) {
            // Si no hay películas seleccionadas, mostrar un estado de vista vacío.
            findViewById(R.id.empty_state_card).setVisibility(View.VISIBLE);
            recyclerViewMovies.setVisibility(View.GONE);
        } else {
            // Si hay películas seleccionadas, mostrar el RecyclerView y actualizar la lista.
            findViewById(R.id.empty_state_card).setVisibility(View.GONE);
            recyclerViewMovies.setVisibility(View.VISIBLE);

            moviesList.clear();
            moviesList.addAll(selectedMovies);
            movieAdapter.notifyDataSetChanged(); // Notificar al adaptador de los cambios.
        }
    }

    // Manejo del resultado de la actividad AddMovie.
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Verificar si el resultado viene de AddMovieActivity y si fue exitoso.
        if (requestCode == REQUEST_CODE_ADD_MOVIE && resultCode == RESULT_OK && data != null) {
            ArrayList<Movie> newSelectedMovies = data.getParcelableArrayListExtra("selectedMovies");
            if (newSelectedMovies != null) {
                // Agregar nuevas películas seleccionadas al conjunto y actualizar la vista.
                selectedMovies.addAll(newSelectedMovies);
                refreshSelectedMoviesView();
            }
        }
    }
}
