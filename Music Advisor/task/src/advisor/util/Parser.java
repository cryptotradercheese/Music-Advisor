package advisor.util;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

import java.util.LinkedHashMap;
import java.util.Map;

public class Parser {
    public static String parseNew(String json) {
        JsonArray albums = JsonParser.parseString(json)
                .getAsJsonObject()
                .getAsJsonObject("albums")
                .getAsJsonArray("items");

        StringBuilder newAlbums = new StringBuilder("");
        for (JsonElement album : albums) {
            String albumName = album.getAsJsonObject()
                    .get("name").getAsString();
            newAlbums.append(albumName + System.lineSeparator());

            JsonArray artists = album.getAsJsonObject()
                    .getAsJsonArray("artists");
            newAlbums.append("[");
            for (int i = 0; i < artists.size(); i++) {
                if (i > 0) {
                    newAlbums.append(", ");
                }

                String artistName = artists.get(i).getAsJsonObject().get("name").getAsString();
                newAlbums.append(artistName);
            }
            newAlbums.append("]"  + System.lineSeparator());

            String albumLink = album.getAsJsonObject()
                    .getAsJsonObject("external_urls")
                    .get("spotify")
                    .getAsString();
            newAlbums.append(albumLink + System.lineSeparator() + System.lineSeparator());
        }

        return newAlbums.toString();
    }

    public static String parseFeatured(String json) {
        JsonArray playlists = JsonParser.parseString(json)
                .getAsJsonObject()
                .getAsJsonObject("playlists")
                .getAsJsonArray("items");

        StringBuilder featured = new StringBuilder("");
        for (JsonElement playlist : playlists) {
            String playlistName = playlist.getAsJsonObject()
                    .get("name").getAsString();
            featured.append(playlistName + System.lineSeparator());

            String playlistLink = playlist.getAsJsonObject()
                    .getAsJsonObject("external_urls")
                    .get("spotify")
                    .getAsString();
            featured.append(playlistLink + System.lineSeparator() + System.lineSeparator());
        }

        return featured.toString();
    }

    public static String parseCategories(Map<String, String> categoriesIds) {
        StringBuilder categories = new StringBuilder("");

        for (Map.Entry<String, String> entry : categoriesIds.entrySet()) {
            categories.append(entry.getKey() + System.lineSeparator());
        }

        return categories.toString();
    }

    public static Map<String, String> parseCategoriesIds(String json) {
        JsonArray categories = JsonParser.parseString(json)
                .getAsJsonObject()
                .getAsJsonObject("categories")
                .getAsJsonArray("items");

        Map<String, String> categoriesIds = new LinkedHashMap<>();
        for (JsonElement category : categories) {
            String categoryName = category.getAsJsonObject()
                    .get("name").getAsString();

            String categoryId = category.getAsJsonObject()
                    .get("id").getAsString();

            categoriesIds.put(categoryName, categoryId);
        }

        return categoriesIds;
    }

    public static String parsePlaylists(String json) throws Exception {
        if (JsonParser.parseString(json)
                .getAsJsonObject()
                .getAsJsonObject("playlists") == null
        ) {
            throw new Exception(parseError(json));
        }

        JsonArray playlists = JsonParser.parseString(json)
                .getAsJsonObject()
                .getAsJsonObject("playlists")
                .getAsJsonArray("items");

        StringBuilder playlistsReturn = new StringBuilder("");
        for (JsonElement playlist : playlists) {
            String playlistName = playlist.getAsJsonObject()
                    .get("name").getAsString();
            playlistsReturn.append(playlistName + System.lineSeparator());

            String playlistLink = playlist.getAsJsonObject()
                    .getAsJsonObject("external_urls")
                    .get("spotify")
                    .getAsString();
            playlistsReturn.append(playlistLink + System.lineSeparator() + System.lineSeparator());
        }

        return playlistsReturn.toString();
    }

    public static String parseError(String json) {
        return JsonParser.parseString(json)
                .getAsJsonObject()
                .getAsJsonObject("error")
                .get("message")
                .getAsString();
    }

    public static String parseToken(String json) {
        return JsonParser.parseString(json)
                .getAsJsonObject()
                .getAsJsonPrimitive("access_token")
                .getAsString();
    }
}
