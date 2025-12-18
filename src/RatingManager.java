// RatingManager.java
import java.io.*;
import java.util.*;

public class RatingManager {
    private String ratingsFile;
    private Map<Integer, Integer> userRatings; // movieId -> rating
    
    public RatingManager(String ratingsFile) {
        this.ratingsFile = ratingsFile;
        this.userRatings = new HashMap<>();
        loadRatings();
    }
    
    private void loadRatings() {
        File file = new File(ratingsFile);
        if (!file.exists()) return;
        
        try (BufferedReader br = new BufferedReader(new FileReader(ratingsFile))) {
            String line;
            while ((line = br.readLine()) != null) {
                try {
                    String[] parts = line.split(",");
                    if (parts.length >= 2) {
                        int movieId = Integer.parseInt(parts[0].trim());
                        int rating = Integer.parseInt(parts[1].trim());
                        userRatings.put(movieId, rating);
                    }
                } catch (NumberFormatException e) {
                    System.out.println("Skipping invalid rating line: " + line);
                }
            }
        } catch (IOException e) {
            System.out.println("Warning: Unable to read ratings file");
        }
    }
    
    public boolean rateMovie(int movieId, int rating, MovieCatalog catalog) {
        if (rating < 1 || rating > 5) {
            System.out.println("Error: Rating must be between 1-5");
            return false;
        }
        
        if (catalog.getMovie(movieId) == null) {
            System.out.println("Error: Movie ID does not exist");
            return false;
        }
        
        userRatings.put(movieId, rating);
        return saveRatings();
    }
    
    private boolean saveRatings() {
        try (PrintWriter writer = new PrintWriter(new FileWriter(ratingsFile))) {
            for (Map.Entry<Integer, Integer> entry : userRatings.entrySet()) {
                writer.println(entry.getKey() + "," + entry.getValue() + "," + java.time.LocalDate.now());
            }
            return true;
        } catch (IOException e) {
            System.out.println("Error: Unable to save ratings");
            return false;
        }
    }
    
    public Integer getUserRating(int movieId) {
        return userRatings.get(movieId);
    }
    
    public Map<Integer, Integer> getAllRatings() {
        return new HashMap<>(userRatings);
    }
}