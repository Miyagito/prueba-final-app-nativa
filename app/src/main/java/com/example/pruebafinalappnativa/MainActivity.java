package com.example.pruebafinalappnativa;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.RecyclerView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private FloatingActionButton fab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        recyclerView = findViewById(R.id.recycler_view_movies);
        // Aquí configurarías tu RecyclerView con un LayoutManager y un Adapter

        fab = findViewById(R.id.add_movie_fab);
        fab.setOnClickListener(view -> {
            // Aquí iniciarías la actividad para agregar una nueva película
        });
    }
}
