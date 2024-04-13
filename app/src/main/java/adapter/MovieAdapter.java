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
import model.Movie;

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.MovieViewHolder> {

    private final List<Movie> moviesList;

    public MovieAdapter(List<Movie> moviesList) {
        this.moviesList = moviesList;
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
    }

    @Override
    public int getItemCount() {
        return moviesList != null ? moviesList.size() : 0;
    }

    public static class MovieViewHolder extends RecyclerView.ViewHolder {
        ImageView moviePosterImageView;
        TextView movieTitleTextView;
        TextView movieYearTextView;
        TextView movieTypeTextView;
        TextView movieImdbIDTextView;

        public MovieViewHolder(View view) {
            super(view);
            moviePosterImageView = view.findViewById(R.id.moviePosterImageView);
            movieTitleTextView = view.findViewById(R.id.movieTitleTextView);
            movieYearTextView = view.findViewById(R.id.movieYearTextView);
            movieTypeTextView = view.findViewById(R.id.movieTypeTextView);
            movieImdbIDTextView = view.findViewById(R.id.movieImdbIDTextView);
        }
    }
}
