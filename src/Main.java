// Main.java
import java.util.*;

public class Main {
    public static void main(String[] args) {
        // Initialize components
        MovieCatalog catalog = new MovieCatalog("data/movies.txt");
        RatingManager ratingManager = new RatingManager("data/my_ratings.txt");
        RecommendationEngine engine = new RecommendationEngine(catalog, ratingManager);
        
        // Load movie data
        if (!catalog.loadMovies()) {
            System.out.println("Failed to load movie data, program exits");
            return;
        }
        
        System.out.println("Movie data loaded successfully! Total " + catalog.getAllMovies().size() + " movies loaded");
        System.out.println("=== Movie Recommendation System ===");
        
        Scanner scanner = new Scanner(System.in);
        
        while (true) {
            System.out.println("\nPlease select an operation:");
            System.out.println("1. Rate a movie");
            System.out.println("2. Get recommendations");
            System.out.println("3. View all movies");
            System.out.println("4. Exit");
            System.out.print("Please enter your choice: ");
            
            String choice = scanner.nextLine();
            
            switch (choice) {
                case "1":
                    rateMovie(scanner, catalog, ratingManager);
                    break;
                case "2":
                    getRecommendations(engine);
                    break;
                case "3":
                    displayAllMovies(catalog);
                    break;
                case "4":
                    System.out.println("Thank you for using!");
                    scanner.close();
                    return;
                default:
                    System.out.println("Invalid choice, please re-enter");
            }
        }
    }
    
    private static void rateMovie(Scanner scanner, MovieCatalog catalog, RatingManager ratingManager) {
        System.out.print("Please enter movie ID: ");
        try {
            int movieId = Integer.parseInt(scanner.nextLine());
            Movie movie = catalog.getMovie(movieId);
            
            if (movie == null) {
                System.out.println("Error: Movie ID does not exist");
                return;
            }
            
            System.out.println("Movie: " + movie.getTitle() + " (" + movie.getYear() + ")");
            System.out.print("Please enter rating (1-5): ");
            int rating = Integer.parseInt(scanner.nextLine());
            
            if (ratingManager.rateMovie(movieId, rating, catalog)) {
                System.out.println("Rating successful!");
            }
        } catch (NumberFormatException e) {
            System.out.println("Input format error, please enter a number");
        }
    }
    
    private static void getRecommendations(RecommendationEngine engine) {
        System.out.println("\n=== Recommendations for you ===");
        List<Movie> recommendations = engine.getRecommendations();
        
        if (recommendations.isEmpty()) {
            System.out.println("No recommendations available");
            return;
        }
        
        for (int i = 0; i < recommendations.size(); i++) {
            Movie movie = recommendations.get(i);
            System.out.printf("%d. %s (%d) - %s - Rating: %.1f\n", 
                i + 1, movie.getTitle(), movie.getYear(), 
                String.join(", ", movie.getGenres()), movie.getAvgRating());
        }
    }
    
    private static void displayAllMovies(MovieCatalog catalog) {
        System.out.println("\n=== All Movies ===");
        List<Movie> allMovies = catalog.getAllMovies();
        for (Movie movie : allMovies) {
            System.out.printf("ID: %d, %s (%d) - %s\n", 
                movie.getMovieId(), movie.getTitle(), movie.getYear(), 
                String.join(", ", movie.getGenres()));
        }
    }
}