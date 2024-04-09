package at.ac.fhcampuswien.fhmdb.API;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MovieAPITest {

    @Test
    public void testBuildURLWithAllParameters() {
        String query = "The Matrix";
        String genre = "Sci-Fi";
        String releaseYear = "1999";
        String ratingFrom = "8";

        String expectedURL = "https://prog2.fh-campuswien.ac.at/movies?query=The Matrix&genre=Sci-Fi&releaseYear=1999&ratingFrom=8";
        String actualURL = MovieAPI.buildURL(query, genre, releaseYear, ratingFrom);

        assertEquals(expectedURL, actualURL, "URL should match expected URL with all query parameters.");
    }

    
}