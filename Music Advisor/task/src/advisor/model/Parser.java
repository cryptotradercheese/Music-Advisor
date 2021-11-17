package advisor.model;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class Parser {
    public static List<String> parseNew(String json) {
        JsonArray albums = JsonParser.parseString(json)
                .getAsJsonObject()
                .getAsJsonObject("albums")
                .getAsJsonArray("items");

        List<String> newAlbums = new LinkedList<>();
        for (JsonElement album : albums) {
            StringBuilder newAlbum = new StringBuilder("");

            String albumName = album.getAsJsonObject()
                    .get("name").getAsString();
            newAlbum.append(albumName + System.lineSeparator());

            JsonArray artists = album.getAsJsonObject()
                    .getAsJsonArray("artists");
            newAlbum.append("[");
            for (int i = 0; i < artists.size(); i++) {
                if (i > 0) {
                    newAlbum.append(", ");
                }

                String artistName = artists.get(i).getAsJsonObject().get("name").getAsString();
                newAlbum.append(artistName);
            }
            newAlbum.append("]"  + System.lineSeparator());

            String albumLink = album.getAsJsonObject()
                    .getAsJsonObject("external_urls")
                    .get("spotify")
                    .getAsString();
            newAlbum.append(albumLink);
            newAlbums.add(newAlbum.toString());
        }

        return newAlbums;
    }

    public static List<String> parseFeatured(String json) {
        JsonArray playlists = JsonParser.parseString(json)
                .getAsJsonObject()
                .getAsJsonObject("playlists")
                .getAsJsonArray("items");

        List<String> featuredList = new LinkedList<>();
        for (JsonElement playlist : playlists) {
            StringBuilder featuredItem = new StringBuilder("");

            String playlistName = playlist.getAsJsonObject()
                    .get("name").getAsString();
            featuredItem.append(playlistName + System.lineSeparator());

            String playlistLink = playlist.getAsJsonObject()
                    .getAsJsonObject("external_urls")
                    .get("spotify")
                    .getAsString();
            featuredItem.append(playlistLink);
            featuredList.add(featuredItem.toString());
        }

        return featuredList;
    }

    public static List<String> parseCategories(Map<String, String> categoriesIds) {
        List<String> categories = new LinkedList<>();

        for (Map.Entry<String, String> entry : categoriesIds.entrySet()) {
            categories.add(entry.getKey());
        }

        return categories;
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

    public static List<String> parsePlaylists(String json) throws Exception {
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

        List<String> playlistList = new LinkedList<>();

        for (JsonElement playlist : playlists) {
            StringBuilder playlistItem = new StringBuilder("");

            String playlistName = playlist.getAsJsonObject()
                    .get("name").getAsString();
            playlistItem.append(playlistName + System.lineSeparator());

            String playlistLink = playlist.getAsJsonObject()
                    .getAsJsonObject("external_urls")
                    .get("spotify")
                    .getAsString();
            playlistItem.append(playlistLink);

            playlistList.add(playlistItem.toString());
        }

        return playlistList;
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
