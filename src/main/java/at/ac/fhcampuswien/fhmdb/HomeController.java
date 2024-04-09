package at.ac.fhcampuswien.fhmdb;

import at.ac.fhcampuswien.fhmdb.models.Movie;
import at.ac.fhcampuswien.fhmdb.ui.MovieCell;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXListView;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.*;

public class HomeController implements Initializable {
    @FXML
    public JFXButton sortBtn;

    @FXML
    public TextField searchField;

    @FXML
    public JFXListView<Movie> movieListView;

    @FXML
    public JFXComboBox genreComboBox;

    @FXML
    public JFXButton searchBtn;

    public List<Movie> allFilms;

    protected ObservableList<Movie> displayedMovies = FXCollections.observableArrayList();


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        initializeData();
        initializeUI();
        sortBtn.setOnAction(actionEvent -> sortMovies());
        System.out.println("intitialization is set");
    }

    public void initializeData() {
        allFilms = Movie.initializeMovies();
        displayedMovies.clear();
        displayedMovies.addAll(allFilms); // add all movies to the observable list
        System.out.println("all films: " + displayedMovies);
    }

    public void initializeUI() {
        movieListView.setItems(displayedMovies);   // set the items of the listview to the observable list

        movieListView.setCellFactory(param -> new MovieCell()); // Use MovieCell class for list cell

        printMovieListViewContent();

        // Erstellen einer Liste von Filmen
        List<Movie> movies = Movie.initializeMovies();
        List<Object> genresList = new ArrayList<>();
        for (Movie movie : movies) {
            genresList.addAll(movie.getGenres());
        }
        Object[] allGenres = genresList.toArray();

        // Erstellen einer Menge, um doppelte Worte zu entfernen
        Set<Object> uniqueGenresSet = new HashSet<>(Arrays.asList(allGenres));

        // Konvertieren der Menge zurück in ein Array, um doppelte Worte zu entfernen
        Object[] genres = uniqueGenresSet.toArray();


        genreComboBox.getItems().add("No filter");  // add "no filter" to the combobox
        genreComboBox.getItems().addAll(genres);    // add all genres to the combobox
        genreComboBox.setPromptText("Filter by Genre");
    }

    private void printMovieListViewContent() {
        ObservableList<Movie> items = movieListView.getItems();
        System.out.println("Current content of movieListView:");
        for (Movie movie : items) {
            System.out.println(movie.toString());
        }
    }

    public List<Movie> filterBySearchTerm(List<Movie> movies, String term) {
        if (term == null || term.isEmpty()) return movies;

        return movies.stream()
                .filter(Objects::nonNull)
                .filter(movie ->
                        movie.getTitle().toLowerCase().contains(term.toLowerCase()) ||
                                movie.getDescription().toLowerCase().contains(term.toLowerCase())
                )
                .toList();
    }

    public List<Movie> filterByFilmGenre(List<Movie> movies, Object genre) {
        if (genre == null) return movies;

        return movies.stream()
                .filter(Objects::nonNull)
                .filter(movie -> movie.getGenres().contains(genre))
                .toList();
    }

    public void applyFilters(String searchTerm, Object selectedGenre) {
        List<Movie> filteredMovies = allFilms;

        if (!searchTerm.isEmpty()) {
            filteredMovies = filterBySearchTerm(filteredMovies, searchTerm);
            System.out.println("Filtered movies after search filter was set: " + filteredMovies);
        }

        if (selectedGenre != null && !selectedGenre.toString().equals("No filter")) {
            filteredMovies = filterByFilmGenre(filteredMovies, selectedGenre);
            System.out.println("Filtered movies after genre filter was set: " + filteredMovies);
        }
        displayedMovies.clear();
        displayedMovies.addAll(filteredMovies);

        System.out.println("Displayed movies after re-adding: " + displayedMovies.toString());

        // Aktualisiere die ListView, um die Änderungen anzuzeigen
        //movieListView.getItems().clear();
        //movieListView.getItems().addAll(displayedMovies);
    }



    public void searchButtonClicked(ActionEvent actionEvent) {
        System.out.println("Search button clicked");
        String searchTerm = searchField.getText().trim().toLowerCase();
        System.out.println(searchTerm);
        Object selectedGenre = genreComboBox.getSelectionModel().getSelectedItem();
        System.out.println(selectedGenre);
        applyFilters(searchTerm, selectedGenre);
    }

    private void sortMovies() {
        if (sortBtn.getText().equals("Sort (asc)")) {
            displayedMovies.sort((movie1, movie2) -> movie1.getTitle().compareToIgnoreCase(movie2.getTitle()));
            sortBtn.setText("Sort (desc)");
        } else {
            displayedMovies.sort((movie1, movie2) -> movie2.getTitle().compareToIgnoreCase(movie1.getTitle()));
            sortBtn.setText("Sort (asc)");
        }
    }
}
