package at.ac.fhcampuswien.fhmdb;

import at.ac.fhcampuswien.fhmdb.API.MovieAPI;
import com.jfoenix.controls.JFXComboBox;
import javafx.collections.FXCollections;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

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

    @Test
    public void testSearchButtonClicked() {
        // Mock controller to bypass UI initialization
        HomeController controller = mock(HomeController.class, CALLS_REAL_METHODS);
        controller.allFilms = Arrays.asList(
                new Movie("Movie 1", 2000, Arrays.asList("Action"), 7.0),
                new Movie("Movie 2", 2002, Arrays.asList("Drama"), 8.0)
        );
        controller.displayedMovies = FXCollections.observableArrayList(controller.allFilms);
        controller.searchField = new javafx.scene.control.TextField();
        controller.genreComboBox = new JFXComboBox();
        controller.releaseYearComboBox = new JFXComboBox();
        controller.ratingComboBox = new JFXComboBox();
        controller.searchField.setText("1");

        // Perform search
        controller.searchButtonClicked(new ActionEvent());

        // Verify results
        assertEquals(1, controller.displayedMovies.size(), "Only one movie should match the search");
        assertEquals("Movie 1", controller.displayedMovies.get(0).getTitle(), "The displayed movie should be 'Movie 1'");
    }

    @Test
    public void testResetBtnClicked() {
        // Initialize controller with some selections made
        HomeController controller = new HomeController();
        controller.genreComboBox = new JFXComboBox();
        controller.releaseYearComboBox = new JFXComboBox();
        controller.searchField = new javafx.scene.control.TextField("search term");
        controller.ratingComboBox = new JFXComboBox();
        controller.genreComboBox.getSelectionModel().select(1);
        controller.releaseYearComboBox.getSelectionModel().select(1);
        controller.ratingComboBox.getSelectionModel().select(1);

        // Reset
        controller.resetBtnClicked(new ActionEvent());

        // Assert all filters are cleared
        assertTrue(controller.searchField.getText().isEmpty(), "Search field should be cleared");
        assertNull(controller.genreComboBox.getSelectionModel().getSelectedItem(), "Genre selection should be cleared");
        assertNull(controller.releaseYearComboBox.getSelectionModel().getSelectedItem(), "Release year selection should be cleared");
        assertNull(controller.ratingComboBox.getSelectionModel().getSelectedItem(), "Rating selection should be cleared");
    }

    @Test

    void testInitialization() {
        // Mock API responses or other external dependencies
        List<Movie> mockMovies = Arrays.asList(new Movie("Title1", "Desc1", Arrays.asList("Genre1"), 8.0),
                new Movie("Title2", "Desc2", Arrays.asList("Genre2"), 9.0));

        MovieAPI mockApi = mock(MovieAPI.class);
        when(mockApi.getAllMovies()).thenReturn(mockMovies);

        // Create a real HomeController but replace its API dependency with a mock
        HomeController controller = new HomeController(mockApi);

        // Simulate the initialize call that JavaFX would normally perform
        controller.initialize(null, null);

        // Assertions
        assertFalse(controller.displayedMovies.isEmpty(), "Displayed movies should be initialized with all films");
        assertEquals(2, controller.displayedMovies.size(), "Displayed movies list should contain two movies.");

        // Assert that the sort button and other components are initialized properly
        assertNotNull(controller.sortBtn, "Sort button should be initialized");
        assertNotNull(controller.resetBtn, "Reset button should be initialized");
        // More assertions can be added to validate the initialization of other UI components
    }
}



    // Add more test cases for different scenarios as mentioned in the previous response.
}
