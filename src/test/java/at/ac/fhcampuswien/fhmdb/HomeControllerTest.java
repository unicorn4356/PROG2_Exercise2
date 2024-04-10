package at.ac.fhcampuswien.fhmdb;

import com.jfoenix.controls.JFXComboBox;
import javafx.collections.FXCollections;
import javafx.scene.control.ComboBox;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

class HomeControllerTest {

    static class Movie {
        private List<String> genres;

        public Movie(String... object) {
            this.genres = Arrays.asList(object);
        }

        public List<String> getGenres() {
            return genres;
        }
    }

    static class HomeController {
        List<Movie> allFilms;
        List<Movie> displayedMovies;
        javafx.scene.control.TextField searchField;
        ComboBox<String> genreComboBox;
        ComboBox<Integer> releaseYearComboBox;
        ComboBox<Double> ratingComboBox;

        public void searchButtonClicked(ActionEvent event) {
            // Implement search functionality
        }

        public void resetBtnClicked(ActionEvent event) {
            // Implement reset functionality
        }

        // Add more methods and fields as needed
    }

    private List<Movie> allMovies;
    private List<Movie> observableMovies;
    private HomeController controller;

    @BeforeEach
    void setUp() {
        allMovies = new ArrayList<>();
        observableMovies = new ArrayList<>();
        controller = new HomeController();
    }

    @Test
    void testNoGenreSelected() {
        // Add some movies to allMovies
        allMovies.add(new Movie("Action"));
        allMovies.add(new Movie("Comedy"));
        allMovies.add(new Movie("Drama"));

        // Selecting no genre
        // For the purpose of this test, let's assume we directly call the method
        controller.allFilms = allMovies;
        controller.displayedMovies = observableMovies;
        controller.filterByGenre();

        assertEquals(3, observableMovies.size());
    }

    @Test
    void testFilterByGenre() {
        // Add some movies to allMovies
        allMovies.add(new Movie("Action", "Adventure"));
        allMovies.add(new Movie("Comedy", "Romance"));
        allMovies.add(new Movie("Drama", "Romance"));

        // Selecting "Romance" genre
        // For the purpose of this test, let's assume we directly call the method
        controller.allFilms = allMovies;
        controller.displayedMovies = observableMovies;
        controller.setSelectedGenre("Romance");
        controller.filterByGenre();

        assertEquals(2, observableMovies.size());
        assertEquals("Comedy", observableMovies.get(0).getGenres().get(0));
        assertEquals("Drama", observableMovies.get(1).getGenres().get(0));
    }

    @Test
    public void testSearchButtonClicked() {
        // Setting up data for testing
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
        // Setting up data for testing
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

    // Add more test cases for different scenarios as mentioned in the previous response.
}
