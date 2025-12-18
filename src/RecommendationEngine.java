// RecommendationEngine.java
import java.util.*;

public class RecommendationEngine {
    private MovieCatalog catalog;
    private RatingManager ratingManager;
    
    public RecommendationEngine(MovieCatalog catalog, RatingManager ratingManager) {
        this.catalog = catalog;
        this.ratingManager = ratingManager;
    }
    
    public List<Movie> getRecommendations() {
        Map<Integer, Integer> userRatings = ratingManager.getAllRatings();
        
        // Cold start handling: return popular movies when user has no ratings
        if (userRatings.isEmpty()) {
            return getPopularMovies();
        }
        
        // Content-based recommendations
        return getContentBasedRecommendations(userRatings);
    }
    
    private List<Movie> getPopularMovies() {
        List<Movie> allMovies = catalog.getAllMovies();
        // Sort by rating (using getAvgRating() method)
        allMovies.sort((m1, m2) -> Double.compare(m2.getAvgRating(), m1.getAvgRating()));
        return allMovies.subList(0, Math.min(10, allMovies.size()));
    }
    
    private List<Movie> getContentBasedRecommendations(Map<Integer, Integer> userRatings) {
        // Get user's highly rated movies (4-5 stars)
        List<Movie> highlyRatedMovies = new ArrayList<>();
        for (Map.Entry<Integer, Integer> entry : userRatings.entrySet()) {
            if (entry.getValue() >= 4) {
                Movie movie = catalog.getMovie(entry.getKey());
                if (movie != null) {
                    highlyRatedMovies.add(movie);
                }
            }
        }
        
        // If no highly rated movies, return popular movies
        if (highlyRatedMovies.isEmpty()) {
            return getPopularMovies();
        }
        
        // Calculate similarity scores for unrated movies
        Map<Movie, Integer> movieScores = new HashMap<>();
        for (Movie movie : catalog.getAllMovies()) {
            if (!userRatings.containsKey(movie.getMovieId())) {
                int score = calculateSimilarityScore(movie, highlyRatedMovies);
                movieScores.put(movie, score);
            }
        }
        
        // Sort by score and return recommendations
        List<Movie> recommendations = new ArrayList<>(movieScores.keySet());
        recommendations.sort((m1, m2) -> Integer.compare(movieScores.get(m2), movieScores.get(m1)));
        
        return recommendations.subList(0, Math.min(10, recommendations.size()));
    }
    
    private int calculateSimilarityScore(Movie targetMovie, List<Movie> highlyRatedMovies) {
        int score = 0;
        for (Movie ratedMovie : highlyRatedMovies) {
            // Genre matching: count number of common genres
            for (String genre : targetMovie.getGenres()) {
                if (ratedMovie.hasGenre(genre)) {
                    score++;
                }
            }
            // Optional: add points for director match
            if (targetMovie.getDirector().equals(ratedMovie.getDirector())) {
                score += 2;
            }
        }
        return score;
    }
}