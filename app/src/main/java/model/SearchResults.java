package model;

import java.util.List;

public class SearchResults {
    private List<Movie> Search;
    private String totalResults;
    private String Response;

    // Getter y Setter para Search
    public List<Movie> getSearch() {
        return Search;
    }

    public void setSearch(List<Movie> search) {
        Search = search;
    }

    // Getter y Setter para totalResults
    public String getTotalResults() {
        return totalResults;
    }

    public void setTotalResults(String totalResults) {
        this.totalResults = totalResults;
    }

    // Getter y Setter para Response
    public String getResponse() {
        return Response;
    }

    public void setResponse(String response) {
        Response = response;
    }

    public static class Movie {
        private String Title;
        private Number Year;
        private String imdbID;
        private String Type;
        private String Poster;

        // Getter y Setter para Title
        public String getTitle() {
            return Title;
        }

        public void setTitle(String title) {
            Title = title;
        }

        // Getter y Setter para Year
        public Number getYear() {
            return Year;
        }

        public void setYear(Number year) {
            Year = year;
        }

        // Getter y Setter para imdbID
        public String getImdbID() {
            return imdbID;
        }

        public void setImdbID(String imdbID) {
            this.imdbID = imdbID;
        }

        // Getter y Setter para Type
        public String getType() {
            return Type;
        }

        public void setType(String type) {
            Type = type;
        }

        // Getter y Setter para Poster
        public String getPoster() {
            return Poster;
        }

        public void setPoster(String poster) {
            Poster = poster;
        }
    }
}

