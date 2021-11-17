package advisor.model;

import java.util.List;

public class Retriever {
    private String resource;
    private String token;

    public Retriever(String resource, String token) {
        this.resource = resource;
        this.token = token;
    }

    public List<String> retrieveNew() {
        String uri = resource + "/v1/browse/new-releases";
        String contents = SpotifyInteraction.getContents(uri, token);
        return Parser.parseNew(contents);
    }

    public List<String> retrieveFeature() {
        String uri = resource + "/v1/browse/featured-playlists";
        String contents = SpotifyInteraction.getContents(uri, token);
        return Parser.parseFeatured(contents);
    }

    public List<String> retrieveCategories() {
        String uri = resource + "/v1/browse/categories";
        String contents = SpotifyInteraction.getContents(uri, token);
        return Parser.parseCategories(Parser.parseCategoriesIds(contents));
    }

    public List<String> retrievePlaylists(String id) throws Exception {
        String uri = resource + "/v1/browse/categories/" + id + "/playlists";
        String contents = SpotifyInteraction.getContents(uri, token);
        return Parser.parsePlaylists(contents);
    }
}
