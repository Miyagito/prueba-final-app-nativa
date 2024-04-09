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

    private RecyclerView recyclerView;
    private FloatingActionButton fab;

    private List<Movie> moviesList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        recyclerView = findViewById(R.id.recycler_view_movies);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        moviesList = new ArrayList<>();
        moviesList.add(new Movie("Titulo 1", "Actor Principal 1", "Fecha 1", "Ciudad 1"));
        moviesList.add(new Movie("Titulo 2", "Actor Principal 2", "Fecha 2", "Ciudad 2"));

        // Suponiendo que moviesList ya está inicializada y contiene datos
        MovieAdapter adapter = new MovieAdapter(moviesList);
        recyclerView.setAdapter(adapter);

        fab = findViewById(R.id.add_movie_fab);
        fab.setOnClickListener(view -> {
            Intent intent = new Intent(MainActivity.this, AddMovieActivity.class);
            startActivity(intent);
        });
    }
}
