package at.ac.fhcampuswien.fhmdb;

import at.ac.fhcampuswien.fhmdb.models.Movie;
import at.ac.fhcampuswien.fhmdb.ui.MovieCell;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXListView;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

public class HomeController implements Initializable {
    @FXML
    public JFXButton searchBtn;

    @FXML
    public TextField searchField;

    @FXML
    public JFXListView<Movie> movieListView;

    @FXML
    public JFXComboBox<String> genreComboBox;

    @FXML
    public JFXButton sortBtn;

    public List<Movie> allMovies = Movie.initializeMovies();

    private final ObservableList<Movie> observableMovies = FXCollections.observableArrayList();   // automatically updates corresponding UI elements when underlying data changes

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        observableMovies.addAll(allMovies);         // add dummy data to observable list

        // initialize UI stuff
        movieListView.setItems(observableMovies);   // set data of observable list to list view
        movieListView.setCellFactory(movieListView -> new MovieCell()); // use custom cell factory to display data

        // Populate genre filter items
        List<String> genres = allMovies.stream()
                .flatMap(movie -> movie.getGenres().stream())
                .distinct()
                .collect(Collectors.toList());
        genreComboBox.getItems().addAll(genres);
        genreComboBox.setPromptText("Filter by Genre");

        // Set event handlers to buttons
        searchBtn.setOnAction(actionEvent -> filterBySearch());
        sortBtn.setOnAction(actionEvent -> sortMovies());
        genreComboBox.setOnAction(actionEvent -> filterByGenre());
    }

    // Filter movies by search query
    // Filter movies by search query and genre
    // Filter movies to show only the one that matches exactly with the search query
    // Filter movies by search query
    private void filterBySearch() {
        String query = searchField.getText().trim().toLowerCase();

        // Filter movies based on search query in title or description
        observableMovies.setAll(allMovies.stream()
                .filter(movie -> {
                    String title = movie.getTitle().toLowerCase();
                    String description = movie.getDescription().toLowerCase();
                    return title.contains(query) || description.contains(query);
                })
                .collect(Collectors.toList()));
    }





    // Filter movies by genre
    private void filterByGenre() {
        String selectedGenre = genreComboBox.getValue();
        if (selectedGenre == null || selectedGenre.isEmpty()) {
            // If no genre is selected, show all movies
            observableMovies.setAll(allMovies);
        } else {
            // Filter movies to show only those that belong to the selected genre
            observableMovies.setAll(allMovies.stream()
                    .filter(movie -> movie.getGenres().contains(selectedGenre))
                    .collect(Collectors.toList()));
        }
    }




    // Sort movies
    private void sortMovies() {
        if (sortBtn.getText().equals("Sort (asc)")) {
            observableMovies.sort((movie1, movie2) -> movie1.getTitle().compareToIgnoreCase(movie2.getTitle()));
            sortBtn.setText("Sort (desc)");
        } else {
            observableMovies.sort((movie1, movie2) -> movie2.getTitle().compareToIgnoreCase(movie1.getTitle()));
            sortBtn.setText("Sort (asc)");
        }
    }
}
