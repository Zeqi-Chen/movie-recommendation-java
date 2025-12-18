// MovieCatalog.java
import java.io.*;
import java.util.*;

public class MovieCatalog {
    private Map<Integer, Movie> movies;
    private String dataFile;
    
    public MovieCatalog(String dataFile) {
        this.dataFile = dataFile;
        this.movies = new HashMap<>();
    }
    
    public boolean loadMovies() {
        try (BufferedReader br = new BufferedReader(new FileReader(dataFile))) {
            String line;
            while ((line = br.readLine()) != null) {
                try {
                    Movie movie = parseMovieLine(line);
                    if (movie != null) {
                        movies.put(movie.getMovieId(), movie);
                    }
                } catch (Exception e) {
                    System.out.println("Skipping invalid data line: " + line);
                }
            }
            return true;
        } catch (IOException e) {
            System.out.println("Error: Unable to read movie data file " + dataFile);
            return false;
        }
    }
    
    private Movie parseMovieLine(String line) {
        String[] parts = line.split(",");
        if (parts.length < 6) return null;
        
        try {
            int movieId = Integer.parseInt(parts[0].trim());
            String title = parts[1].trim();
            String director = parts[2].trim();
            int year = Integer.parseInt(parts[3].trim());
            String[] genres = parts[4].split(";");
            double avgRating = Double.parseDouble(parts[5].trim());
            int numRatings = parts.length > 6 ? Integer.parseInt(parts[6].trim()) : 0;
            
            return new Movie(movieId, title, director, year, genres, avgRating, numRatings);
        } catch (NumberFormatException e) {
            return null;
        }
    }
    
    public Movie getMovie(int movieId) {
        return movies.get(movieId);
    }
    
    public List<Movie> getAllMovies() {
        return new ArrayList<>(movies.values());
    }
}