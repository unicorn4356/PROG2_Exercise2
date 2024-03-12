package at.ac.fhcampuswien.fhmdb;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import static org.junit.jupiter.api.Assertions.assertEquals;

class HomeControllerTest {

    static class Movie {
        private List<String> genres;

        public Movie(String... genres) {
            this.genres = Arrays.asList(genres);
        }

        public List<String> getGenres() {
            return genres;
        }
    }

    static class MovieFilter {
        private List<Movie> allMovies;
        private List<Movie> observableMovies;
        private String selectedGenre;

        public MovieFilter(List<Movie> allMovies, List<Movie> observableMovies) {
            this.allMovies = allMovies;
            this.observableMovies = observableMovies;
        }

        public void setSelectedGenre(String selectedGenre) {
            this.selectedGenre = selectedGenre;
        }

        public void filterByGenre() {
            if (selectedGenre == null || selectedGenre.isEmpty()) {
                // If no genre is selected, show all movies
                observableMovies.clear();
                observableMovies.addAll(allMovies);
            } else {
                // Filter movies to show only those that belong to the selected genre
                observableMovies.clear();
                for (Movie movie : allMovies) {
                    if (movie.getGenres().contains(selectedGenre)) {
                        observableMovies.add(movie);
                    }
                }
            }
        }
    }

    private List<Movie> allMovies;
    private List<Movie> observableMovies;
    private MovieFilter movieFilter;

    @BeforeEach
    void setUp() {
        allMovies = new ArrayList<>();
        observableMovies = new ArrayList<>();
        movieFilter = new MovieFilter(allMovies, observableMovies);
    }

    @Test
    void testNoGenreSelected() {
        // Add some movies to allMovies
        allMovies.add(new Movie("Action"));
        allMovies.add(new Movie("Comedy"));
        allMovies.add(new Movie("Drama"));

        // Selecting no genre
        movieFilter.filterByGenre();

        assertEquals(3, observableMovies.size());
    }

    @Test
    void testFilterByGenre() {
        // Add some movies to allMovies
        allMovies.add(new Movie("Action", "Adventure"));
        allMovies.add(new Movie("Comedy", "Romance"));
        allMovies.add(new Movie("Drama", "Romance"));

        // Selecting "Romance" genre
        movieFilter.setSelectedGenre("Romance");
        movieFilter.filterByGenre();

        assertEquals(2, observableMovies.size());
        assertEquals("Comedy", observableMovies.get(0).getGenres().get(0));
        assertEquals("Drama", observableMovies.get(1).getGenres().get(0));
    }

    // Add more test cases for different scenarios as mentioned in the previous response.
}
