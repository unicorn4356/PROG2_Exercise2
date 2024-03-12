package at.ac.fhcampuswien.fhmdb.models;

import org.junit.jupiter.api.Test;
import java.util.List;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class MovieTest {

    @Test
    void testGetTitle() {
        Movie movie = new Movie("The Shawshank Redemption", "Two imprisoned men bond over a number of years, finding solace and eventual redemption through acts of common decency.", List.of("Drama"));
        assertEquals("The Shawshank Redemption", movie.getTitle());
    }

    @Test
    void testGetDescription() {
        Movie movie = new Movie("The Godfather", "The aging patriarch of an organized crime dynasty transfers control of his clandestine empire to his reluctant son.", List.of("Crime", "Drama"));
        assertEquals("The aging patriarch of an organized crime dynasty transfers control of his clandestine empire to his reluctant son.", movie.getDescription());
    }

    @Test
    void testGetGenres() {
        Movie movie = new Movie("The Dark Knight", "When the menace known as The Joker emerges from his mysterious past, he wreaks havoc and chaos on the people of Gotham.", List.of("Action", "Crime", "Drama"));
        List<String> genres = movie.getGenres();
        assertEquals(3, genres.size());
        assertEquals("Action", genres.get(0));
        assertEquals("Crime", genres.get(1));
        assertEquals("Drama", genres.get(2));
    }

    @Test
    void testInitializeMovies() {
        List<Movie> movies = Movie.initializeMovies();
        assertEquals(10, movies.size()); // Assuming 10 dummy movies are added
    }
}
