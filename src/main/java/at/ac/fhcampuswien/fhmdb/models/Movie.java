package at.ac.fhcampuswien.fhmdb.models;

import java.util.ArrayList;
import java.util.List;

public class Movie {
    private final String id;
    private final String title;
    private final String description;
    private final List<Object> genres;
    private final int releaseYear;
    private final String imgUrl;
    private final int lengthInMinutes;
    private final List<String> directors = new ArrayList<>();
    private final List<String> writers = new ArrayList<>();
    private final List<String> mainCast = new ArrayList<>();
    private final double rating;

    public Movie(String title, String description, List<Object> genres) {
        this.title = title;
        this.description = description;
        this.genres = genres;
        this.id = null;
        this.releaseYear = 0;
        this.imgUrl = "";
        this.lengthInMinutes = 0;
        this.rating = 0;
    }

    public Movie(String id, String title, String description, List<Object> genres,
                 int releaseYear, String imgUrl, int lengthInMinutes, List<String>
                         directors, List<String> writers, List<String> mainCast, double rating) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.genres = genres;
        this.releaseYear = releaseYear;
        this.imgUrl = imgUrl;
        this.lengthInMinutes = lengthInMinutes;
        this.rating = rating;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public List<Object> getGenres() {
        return genres;
    }

    public static List<Movie> initializeMovies() {
        List<Movie> movies = new ArrayList<>();
        // Adding some dummy data
        movies.add(new Movie("The Shawshank Redemption", "Two imprisoned men bond over a number of years, finding solace and eventual redemption through acts of common decency.", List.of("Drama")));
        movies.add(new Movie("The Godfather", "The aging patriarch of an organized crime dynasty transfers control of his clandestine empire to his reluctant son.", List.of("Crime", "Drama")));
        movies.add(new Movie("The Dark Knight", "When the menace known as The Joker emerges from his mysterious past, he wreaks havoc and chaos on the people of Gotham.", List.of("Action", "Crime", "Drama")));
        movies.add(new Movie("Schindler's List", "In German-occupied Poland during World War II, industrialist Oskar Schindler gradually becomes concerned for his Jewish workforce after witnessing their persecution by the Nazis.", List.of("Biography", "Drama", "History")));
        movies.add(new Movie("Inception", "A thief who steals corporate secrets through the use of dream-sharing technology is given the inverse task of planting an idea into the mind of a C.E.O.", List.of("Action", "Adventure", "Sci-Fi")));
        movies.add(new Movie("Pulp Fiction", "The lives of two mob hitmen, a boxer, a gangster and his wife, and a pair of diner bandits intertwine in four tales of violence and redemption.", List.of("Crime", "Drama")));
        movies.add(new Movie("Forrest Gump", "The presidencies of Kennedy and Johnson, the Vietnam War, the Watergate scandal and other historical events unfold from the perspective of an Alabama man with an IQ of 75, whose only desire is to be reunited with his childhood sweetheart.", List.of("Drama", "Romance")));
        movies.add(new Movie("The Matrix", "A computer hacker learns from mysterious rebels about the true nature of his reality and his role in the war against its controllers.", List.of("Action", "Sci-Fi")));
        movies.add(new Movie("Fight Club", "An insomniac office worker and a devil-may-care soap maker form an underground fight club that evolves into much more.", List.of("Drama")));
        movies.add(new Movie("Goodfellas", "The story of Henry Hill and his life in the mob, covering his relationship with his wife Karen Hill and his mob partners Jimmy Conway and Tommy DeVito in the Italian-American crime syndicate.", List.of("Biography", "Crime", "Drama")));
        // Add more dummy data as needed

        return movies;
    }

    public String getId() {
        return id;
    }
    public int getReleaseYear() {
        return releaseYear;
    }
    public String getImgUrl() {
        return imgUrl;
    }
    public int getLengthInMinutes() {
        return lengthInMinutes;
    }
    public List<String> getDirectors() {
        return directors;
    }
    public List<String> getWriters() {
        return writers;
    }
    public List<String> getMainCast() {
        return mainCast;
    }
    public double getRating() {
        return rating;
    }

}

