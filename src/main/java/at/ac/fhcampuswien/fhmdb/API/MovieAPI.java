package at.ac.fhcampuswien.fhmdb.API;

import at.ac.fhcampuswien.fhmdb.models.Movie;
import com.google.gson.Gson;
import okhttp3.*;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

public class MovieAPI {
    private static final String URL = "https://prog2.fh-campuswien.ac.at/movies";
    private static final String DELIMITER = "&";
    private static final OkHttpClient client = new OkHttpClient();
    private static final Gson gson = new Gson();

    private static String buildURL(String query, Object genre, String releaseYear, String ratingFrom) {
        StringBuilder url = new StringBuilder(URL);
        if (query != null || genre != null || releaseYear != null || ratingFrom != null) {
            url.append("?query=").append(query != null ? query : "").append(DELIMITER);
            if (genre != null) {
                // Hier wandeln wir den Genre-Enum in einen String um und fügen ihn zur URL hinzu
                url.append("genre=").append(genre.toString()).append(DELIMITER);
            }
            url.append("releaseYear=").append(releaseYear != null ? releaseYear : "").append(DELIMITER)
                    .append("ratingFrom=").append(ratingFrom != null ? ratingFrom : "");
        }
        System.out.println("New API Request: " + url.toString());
        return url.toString();
    }


    public static List<Movie> getAllMovies(String query, Object genre, String releaseYear, String ratingFrom) {
        Request request = new Request.Builder()
                .url(buildURL(query, genre, releaseYear, ratingFrom))
                .removeHeader("User-Agent")
                .addHeader("User-Agent", "http.agent")
                .build();
        try (Response response = client.newCall(request).execute()) {
            assert response.body() != null;
            String jsonResponse = response.body().string();
            // Überprüfen, ob die Antwort ein Array ist
            if (jsonResponse.startsWith("[")) {
                // Die Antwort ist ein Array, also können wir sie direkt parsen
                return Arrays.asList(gson.fromJson(jsonResponse, Movie[].class));
            } else {
                // Die Antwort ist kein Array, möglicherweise ein Fehler oder eine andere Datenstruktur
                // Behandeln Sie den Fall entsprechend
                System.err.println("Unerwartetes Antwortformat: " + jsonResponse);
            }
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
        return List.of();
    }


    public static List<Movie> getAllMovies() {
        return getAllMovies(null, null, null, null);
    }
}