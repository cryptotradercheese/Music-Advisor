package advisor;

import java.util.Map;

import advisor.util.SpotifyInteraction;
import advisor.util.Code;
import advisor.util.Parser;
import advisor.util.Server;

public class ClientCode {
    private static String access = "https://accounts.spotify.com";
    private static String resource = "https://api.spotify.com";
    private static Server server;
    private static String token = "";
    private static Map<String, String> categoriesIds = null;
    private static boolean isAccessGranted = false;
    private static String redirectUri = "http://localhost:8080";
    private static String clientId = "f938a23eaa914af89b3c4c60c1fbd9a4";

    public static void retrieveNew() {
        String uri = resource + "/v1/browse/new-releases";
        String contents = SpotifyInteraction.getContents(uri, token);
        System.out.println(Parser.parseNew(contents));
    }

    public static void retrieveFeatured() {
        String uri = resource + "/v1/browse/featured-playlists";
        String contents = SpotifyInteraction.getContents(uri, token);
        System.out.println(Parser.parseFeatured(contents));
    }

    public static void retrieveCategories() {
        String uri = resource + "/v1/browse/categories";
        String contents = SpotifyInteraction.getContents(uri, token);
        System.out.println(Parser.parseCategories(categoriesIds));
    }

    public static void retrievePlaylists(String name) {
        String id = categoriesIds.get(name);

        if (id != null) {
            String uri = resource + "/v1/browse/categories/" + id + "/playlists";
            String contents = SpotifyInteraction.getContents(uri, token);

            try {
                System.out.println(Parser.parsePlaylists(contents));
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        } else {
            System.out.println("Unknown category name.");
        }
    }

    public static void auth() {
        server = new Server();
        Code code = server.start();

        System.out.println("use this link to request the access code:");
        System.out.println(access + "/authorize?client_id=" + clientId +
                "&response_type=code&redirect_uri=" + redirectUri);
        System.out.println("waiting for code...");
        while (!code.isCodeReceived());

        System.out.println("code received");
        System.out.println("making http request for access_token...");

        String response = SpotifyInteraction.getTokenData(code.getCode(), access);
        System.out.println("---SUCCESS---");
        server.stop();
        token = Parser.parseToken(response);
        categoriesIds = Parser.parseCategoriesIds(
                SpotifyInteraction.getContents(resource + "/v1/browse/categories", token)
        );

        isAccessGranted = true;
    }

    public static void exit() {
        System.out.println("---GOODBYE!---");
    }

    public static void parseArgs(String[] args) {
        if (args.length == 2) {
            if ("-access".equals(args[0])) {
                access = args[1];
            } else if ("-resource".equals(args[0])) {
                resource = args[1];
            }
        } else if (args.length == 4) {
            if ("-access".equals(args[0])) {
                access = args[1];
            } else if ("-resource".equals(args[0])) {
                resource = args[1];
            }

            if ("-access".equals(args[2])) {
                access = args[3];
            } else if ("-resource".equals(args[2])) {
                resource = args[3];
            }
        }
    }

    public static boolean isAccessGranted() {
        return isAccessGranted;
    }
}
