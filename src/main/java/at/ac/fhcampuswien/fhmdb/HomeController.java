package at.ac.fhcampuswien.fhmdb;

import at.ac.fhcampuswien.fhmdb.models.Movie;
import at.ac.fhcampuswien.fhmdb.ui.MovieCell;
import static at.ac.fhcampuswien.fhmdb.API.MovieAPI.*;
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
import java.util.stream.Collectors;

public class HomeController implements Initializable {
    @FXML
    public JFXButton sortBtn;

    @FXML
    public JFXButton resetBtn;

    @FXML
    public JFXButton searchBtn;

    @FXML
    public TextField searchField;

    @FXML
    public JFXListView<Movie> movieListView;

    @FXML
    public JFXComboBox genreComboBox;

    @FXML
    public JFXComboBox releaseYearComboBox;

    @FXML
    public JFXComboBox ratingComboBox;

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
        allFilms = getAllMovies();
        displayedMovies.clear();
        displayedMovies.addAll(allFilms); // add all movies to the observable list
        System.out.println("all films: " + displayedMovies);
    }

    public void initializeUI() {
        movieListView.setItems(displayedMovies);   // set the items of the listview to the observable list

        movieListView.setCellFactory(param -> new MovieCell()); // Use MovieCell class for list cell

        printMovieListViewContent();

        // Erstellen einer Liste von Filmen
        List<Movie> movies = getAllMovies();
        List<Object> genresList = new ArrayList<>();
        for (Movie movie : movies) {
            genresList.addAll(movie.getGenres());
        }
        Object[] allGenres = genresList.toArray();

        // Erstellen einer Menge, um doppelte Worte zu entfernen
        Set<Object> uniqueGenresSet = new HashSet<>(Arrays.asList(allGenres));

        // Konvertieren der Menge zurück in ein Array, um doppelte Worte zu entfernen
        Object[] genres = uniqueGenresSet.toArray();

        genreComboBox.getItems().add((Object) "No filter");
        genreComboBox.getItems().addAll(genres);    // add all genres to the combobox
        genreComboBox.setPromptText("Filter by Genre");

        releaseYearComboBox.setPromptText("Filter by Release Year");
        Integer[] releaseYears = new Integer[78];
        for (int i = 0; i < 78; i++) {
            releaseYears[i] = 2023 - i;
        }
        //releaseYearComboBox.getItems().add("No filter");
        releaseYearComboBox.getItems().addAll(releaseYears);

        ratingComboBox.setPromptText("Filter by rating");
        Double[] rating = new Double[]{1.00, 2.00, 3.00, 4.00, 5.00, 6.00, 7.00, 8.00, 9.00, 10.00};
        ratingComboBox.getItems().addAll(rating);
    }

    private void printMovieListViewContent() {
        ObservableList<Movie> items = movieListView.getItems();
        System.out.println("Current content of movieListView:");
        for (Movie movie : items) {
            System.out.println(movie.toString());
        }
    }

    public void searchButtonClicked(ActionEvent actionEvent) {
        System.out.println("Search button clicked");
        String searchTerm = searchField.getText().trim().toLowerCase();
        System.out.println(searchTerm);
        Object selectedGenre = genreComboBox.getSelectionModel().getSelectedItem();
        System.out.println(selectedGenre);
        String releaseYear = "";
        String rating = "";
        if (releaseYearComboBox.getSelectionModel().getSelectedItem() != null)
            releaseYear = releaseYearComboBox.getSelectionModel().getSelectedItem().toString();
        if (ratingComboBox.getSelectionModel().getSelectedItem() != null)
            rating = ratingComboBox.getSelectionModel().getSelectedItem().toString();
        applyFilters(searchTerm, selectedGenre, releaseYear, rating);
    }

    public void applyFilters(String searchTerm, Object selectedGenre, String releaseYear, String rating) {
        List<Movie> filteredMovies;
        if (searchTerm.isEmpty() && releaseYear.equals("Filter by Release Year") && rating.equals("Filter by rating") && selectedGenre == null || selectedGenre.equals("No filter")) {
            filteredMovies = getAllMovies(null, null, null, null);
        } else {
            String search;
            if (searchTerm.isEmpty()) {
                search = null;
            } else {
                search = searchTerm;
            }

            Object thisGenre;
            if (selectedGenre == null || selectedGenre.equals("No filter")) {
                thisGenre = null;
            } else {
                thisGenre = selectedGenre;
            }

            String selectedReleaseYear;
            if (releaseYear.equals("Filter by Release Year")) {
                selectedReleaseYear = null;
            } else {
                //selectedReleaseYear = Integer.parseInt(releaseYear);
                selectedReleaseYear = releaseYear;
            }

            String selectedRating;
            if (rating.equals("Filter by rating")) {
                selectedRating = null;
            } else {
                //selectedRating = Double.parseDouble(rating);
                selectedRating = rating;
            }

            filteredMovies = getAllMovies(search, thisGenre, selectedReleaseYear, selectedRating);
            displayedMovies.clear();
            displayedMovies.addAll(filteredMovies);
            System.out.println("Displayed movies after re-adding: " + displayedMovies.toString());


        }
    }

    private void sortMovies() {
        if (sortBtn.getText().equals("Sort")) {
            displayedMovies.sort((movie1, movie2) -> movie1.getTitle().compareToIgnoreCase(movie2.getTitle()));
            sortBtn.setText("Sort");
        } else {
            displayedMovies.sort((movie1, movie2) -> movie2.getTitle().compareToIgnoreCase(movie1.getTitle()));
            sortBtn.setText("Sort");
        }
    }

    public void resetBtnClicked(ActionEvent actionEvent) {
        genreComboBox.setPromptText("Filter by Genre");
        releaseYearComboBox.getSelectionModel().clearSelection();
        searchField.clear();
        ratingComboBox.getSelectionModel().clearSelection();

    }

}
