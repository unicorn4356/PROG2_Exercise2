package at.ac.fhcampuswien.fhmdb.ui;

import at.ac.fhcampuswien.fhmdb.models.Movie;
import javafx.application.Platform;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class MovieCellTest {

    @BeforeAll
    public static void initToolkit() {
        // Initialize JavaFX Toolkit explicitly
        Platform.startup(() -> {
            // No need to do anything here
        });
    }

    @Test
    public void testUpdateItemWithMovie() {
        // Arrange
        Movie movie = new Movie("Title", "Description", List.of("Genre"));
        MovieCell cell = new MovieCell();

        // Act
        // Simulate adding the cell to a scene before calling updateItem
        cell.setGraphic(new VBox());
        cell.updateItem(movie, false);

        // Assert
        assertTrue(cell.getGraphic() instanceof VBox);
        VBox layout = (VBox) cell.getGraphic();
        assertEquals(2, layout.getChildren().size());

        Label titleLabel = (Label) layout.getChildren().get(0);
        assertEquals("Title", titleLabel.getText());

        Label detailLabel = (Label) layout.getChildren().get(1);
        assertEquals("Description", detailLabel.getText());
    }


}