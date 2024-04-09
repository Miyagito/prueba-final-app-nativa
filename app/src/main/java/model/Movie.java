package model;

public class Movie {
    private String title;
    private String mainActor;
    private String viewingDate;
    private String city;

    public Movie(String title, String mainActor, String viewingDate, String city) {
        this.title = title;
        this.mainActor = mainActor;
        this.viewingDate = viewingDate;
        this.city = city;
    }

    public String getTitle() {
        return title;
    }

    public String getMainActor() {
        return mainActor;
    }
    // Constructor, getters y setters
}

