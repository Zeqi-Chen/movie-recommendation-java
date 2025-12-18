// Movie.java
public class Movie {
    private int movieId;
    private String title;
    private String director;
    private int year;
    private String[] genres;
    private double avgRating;
    private int numRatings;
    
    public Movie(int movieId, String title, String director, int year, 
                 String[] genres, double avgRating, int numRatings) {
        this.movieId = movieId;
        this.title = title;
        this.director = director;
        this.year = year;
        this.genres = genres;
        this.avgRating = avgRating;
        this.numRatings = numRatings;
    }
    
    public boolean hasGenre(String genre) {
        for (String g : genres) {
            if (g.trim().equalsIgnoreCase(genre)) {
                return true;
            }
        }
        return false;
    }
    
    // All required getter methods
    public int getMovieId() { return movieId; }
    public String getTitle() { return title; }
    public String getDirector() { return director; }
    public int getYear() { return year; }
    public String[] getGenres() { return genres; }
    public double getAvgRating() { return avgRating; }
    public int getNumRatings() { return numRatings; }
    
    // Added toString method for easy debugging
    @Override
    public String toString() {
        return String.format("Movie[ID=%d, Title=%s, Year=%d, Genres=%s, Rating=%.1f]", 
                           movieId, title, year, String.join(",", genres), avgRating);
    }
}