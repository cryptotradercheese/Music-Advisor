package advisor.model;

import java.io.IOException;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class SpotifyInteraction {
    public static String getTokenData(String code, String uri) {
        HttpRequest request = HttpRequest
                .newBuilder()
                .header("Content-Type", "application/x-www-form-urlencoded")
                .header("Authorization", "Basic ZjkzOGEyM2VhYTkxNGFmODliM2M0YzYwYzFmYmQ5YTQ6YmJkMzFlNDYxZGE4NDAyNWIxZjkyMTAyMDQwNzNiZTQ=")
                .POST(
                        HttpRequest.BodyPublishers.ofString("grant_type=authorization_code&code=" + code + "&redirect_uri=http://localhost:8080")
                )
                .uri(URI.create(uri + "/api/token"))
                .build();

        try {
            HttpClient client = HttpClient.newBuilder().build();
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            return response.body();
        } catch (IOException | InterruptedException e) {
            throw new IllegalStateException(e);
        }
    }

    public static String getContents(String uri, String token) {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(uri))
                .header("Authorization", "Bearer " + token)
                .GET()
                .build();

        HttpClient client = HttpClient.newBuilder().build();

        try {
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            return response.body();
        } catch (IOException | InterruptedException e) {
            throw new IllegalStateException(e);
        }
    }
}
