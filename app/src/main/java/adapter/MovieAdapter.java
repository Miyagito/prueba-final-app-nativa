package adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import com.example.pruebafinalappnativa.R;
import java.util.List;
import java.util.Set;
import model.Movie;

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.MovieViewHolder> {

    private final List<Movie> moviesList;
    private Set<Movie> selectedMovies;
    private final OnMovieSelectionListener selectionListener;
    private final boolean isMovieListView;

    public interface OnMovieSelectionListener {
        void onMovieSelectionChanged(Movie movie, boolean isSelected);
    }

    public MovieAdapter(List<Movie> moviesList, Set<Movie> selectedMovies, OnMovieSelectionListener listener, boolean isMovieListView) {
        setHasStableIds(true);
        this.moviesList = moviesList;
        this.selectedMovies = selectedMovies;
        this.selectionListener = listener;
        this.isMovieListView = isMovieListView;
    }

    @NonNull
    @Override
    public MovieViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_movie, parent, false);
        return new MovieViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MovieViewHolder holder, int position) {
        Movie movie = moviesList.get(position);
        holder.movieTitleTextView.setText(movie.getTitle());
        holder.movieYearTextView.setText(movie.getYear());
        holder.movieTypeTextView.setText(movie.getType());
        holder.movieImdbIDTextView.setText(movie.getImdbID());

        if (!"N/A".equals(movie.getPoster())) {
            Glide.with(holder.itemView.getContext())
                    .load(movie.getPoster())
                    .placeholder(android.R.drawable.ic_menu_gallery)
                    .into(holder.moviePosterImageView);
        } else {
            holder.moviePosterImageView.setImageResource(android.R.drawable.ic_menu_gallery);
        }

        if (isMovieListView) {
            holder.addButton.setImageResource(android.R.drawable.ic_menu_delete);
        } else {
            holder.addButton.setImageResource(android.R.drawable.ic_input_add);
        }

        holder.addButton.setOnClickListener(view -> {
            if (holder.getAdapterPosition() != RecyclerView.NO_POSITION) {
                handleMovieClick(moviesList.get(holder.getAdapterPosition()));
            }
        });

    }

    private void handleMovieClick(Movie movie) {
        boolean isSelected = selectedMovies.contains(movie);
        if (isSelected) {
            selectedMovies.remove(movie);
            selectionListener.onMovieSelectionChanged(movie, false);
            if (isMovieListView) {
                removeMovieById(movie.getImdbID());
            }
        } else {
            if (!selectedMovies.contains(movie)) {
                selectedMovies.add(movie);
                selectionListener.onMovieSelectionChanged(movie, true);
            }
        }
    }

    private void removeMovieById(String imdbId) {
        int indexToRemove = -1;
        for (int i = 0; i < moviesList.size(); i++) {
            if (moviesList.get(i).getImdbID().equals(imdbId)) {
                indexToRemove = i;
                break;
            }
        }
        if (indexToRemove != -1) {
            moviesList.remove(indexToRemove);
            notifyItemRemoved(indexToRemove);
            notifyItemRangeChanged(indexToRemove, getItemCount());
        }
    }

    @Override
    public int getItemCount() {
        return moviesList.size();
    }

    @Override
    public long getItemId(int position) {
        return moviesList.get(position).getImdbID().hashCode();
    }

    public Set<Movie> getSelectedMovies() {
        return selectedMovies;
    }

    public static class MovieViewHolder extends RecyclerView.ViewHolder {
        ImageView moviePosterImageView;
        TextView movieTitleTextView;
        TextView movieYearTextView;
        TextView movieTypeTextView;
        TextView movieImdbIDTextView;
        ImageView addButton;

        public MovieViewHolder(View view) {
            super(view);
            moviePosterImageView = view.findViewById(R.id.moviePosterImageView);
            movieTitleTextView = view.findViewById(R.id.movieTitleTextView);
            movieYearTextView = view.findViewById(R.id.movieYearTextView);
            movieTypeTextView = view.findViewById(R.id.movieTypeTextView);
            movieImdbIDTextView = view.findViewById(R.id.movieImdbIDTextView);
            addButton = view.findViewById(R.id.addButton);
        }
    }
}
