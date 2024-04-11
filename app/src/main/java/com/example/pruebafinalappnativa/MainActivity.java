// MainActivity.java
package com.example.pruebafinalappnativa;

import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

import adapter.MovieAdapter;
import model.Movie;

public class MainActivity extends AppCompatActivity {

    private static final int REQUEST_CODE_ADD_MOVIE = 1; // Código de solicitud arbitrario
    private RecyclerView recyclerView;
    private FloatingActionButton fab;
    private List<Movie> moviesList;
    private MovieAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        recyclerView = findViewById(R.id.recycler_view_movies);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        moviesList = new ArrayList<>();
        adapter = new MovieAdapter(moviesList);
        recyclerView.setAdapter(adapter);

        fab = findViewById(R.id.add_movie_fab);
        fab.setOnClickListener(view -> {
            Intent intent = new Intent(MainActivity.this, AddMovieActivity.class);
            startActivityForResult(intent, REQUEST_CODE_ADD_MOVIE);
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_CODE_ADD_MOVIE && resultCode == RESULT_OK && data != null) {
            ArrayList<String> movieTitles = data.getStringArrayListExtra("movieTitles");
            ArrayList<String> movieYears = data.getStringArrayListExtra("movieYears");

            if (movieTitles != null && movieYears != null) {
                for (int i = 0; i < movieTitles.size(); i++) {
                    moviesList.add(new Movie(movieTitles.get(i), "Actor Desconocido", movieYears.get(i), "Ciudad Desconocida"));
                }
                adapter.notifyDataSetChanged();
            }
        }
    }
}
