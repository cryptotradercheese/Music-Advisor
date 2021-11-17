package advisor;

import java.util.List;
import java.util.Map;
import java.util.function.BiConsumer;

import advisor.util.SpotifyInteraction;
import advisor.util.Code;
import advisor.util.Parser;
import advisor.util.Server;

public class ClientCode {
    private static String access = "https://accounts.spotify.com";
    private static String resource = "https://api.spotify.com";
    private static int maxPerPage = 5;
    private static String redirectUri = "http://localhost:8080";
    private static String clientId = "f938a23eaa914af89b3c4c60c1fbd9a4";

    private static Server server;
    private static String token = "";
    private static Map<String, String> categoriesIds = null;
    private static boolean isAccessGranted = false;

    public static class Retriever {
        private int currentPage;
        private int totalPages;
        private RetrieveStrategy strategy;
        private List<String> retrievedContents;

        public void setStrategy(RetrieveStrategy strategy) {
            nullifyState();
            this.strategy = strategy;
        }

        public void retrieve() {
            this.strategy.retrieve();
        }

        public void next() {
            if (this.retrievedContents == null) {
                throw new IllegalStateException("Retrieve contents first");
            }

            if (this.currentPage + 1 == Retriever.this.totalPages) {
                System.out.println("No more pages.");
            } else {
                this.currentPage++;
            }
        }

        public void prev() {
            if (this.retrievedContents == null) {
                throw new IllegalStateException("Retrieve contents first");
            }

            if (this.currentPage == 0) {
                System.out.println("No more pages.");
            } else {
                this.currentPage--;
            }
        }

        private void nullifyState() {
            this.currentPage = 0;
            this.totalPages = (int) Math.ceil((double) this.retrievedContents.size() / ClientCode.maxPerPage);
            this.retrievedContents = null;
        }

        private interface RetrieveStrategy {
            void retrieve();
        }

        public class New implements RetrieveStrategy {
            @Override
            public void retrieve() {
                String uri = resource + "/v1/browse/new-releases";
                String contents = SpotifyInteraction.getContents(uri, token);
                Retriever.this.retrievedContents = Parser.parseNew(contents);
                Retriever.this.retrievedContents.stream()
                        .skip(ClientCode.maxPerPage * Retriever.this.currentPage)
                        .limit(ClientCode.maxPerPage)
                        .forEach((item) -> System.out.println(item + System.lineSeparator()));

                System.out.println("---PAGE " + (Retriever.this.currentPage + 1) +
                        " OF " + Retriever.this.totalPages + "---");
            }
        }

        public class Featured implements RetrieveStrategy {
            @Override
            public void retrieve() {
                String uri = resource + "/v1/browse/featured-playlists";
                String contents = SpotifyInteraction.getContents(uri, token);
                Retriever.this.retrievedContents = Parser.parseFeatured(contents);
                Retriever.this.retrievedContents.stream()
                        .skip(ClientCode.maxPerPage * Retriever.this.currentPage)
                        .limit(ClientCode.maxPerPage)
                        .forEach((item) -> System.out.println(item + System.lineSeparator()));

                System.out.println("---PAGE " + (Retriever.this.currentPage + 1) +
                        " OF " + Retriever.this.totalPages + "---");
            }
        }

        public class Categories implements RetrieveStrategy {
            @Override
            public void retrieve() {
                String uri = resource + "/v1/browse/categories";
                String contents = SpotifyInteraction.getContents(uri, token);
                Retriever.this.retrievedContents = Parser.parseCategories(categoriesIds);
                Retriever.this.retrievedContents.stream()
                        .skip(ClientCode.maxPerPage * Retriever.this.currentPage)
                        .limit(ClientCode.maxPerPage)
                        .forEach((item) -> System.out.println(item + System.lineSeparator()));

                System.out.println("---PAGE " + (Retriever.this.currentPage + 1) +
                        " OF " + Retriever.this.totalPages + "---");
            }
        }

        public class Playlists implements RetrieveStrategy {
            private String name;

            public Playlists(String name) {
                this.name = name;
            }

            @Override
            public void retrieve() {
                String id = categoriesIds.get(this.name);

                if (id != null) {
                    String uri = resource + "/v1/browse/categories/" + id + "/playlists";
                    String contents = SpotifyInteraction.getContents(uri, token);

                    try {
                        Retriever.this.retrievedContents = Parser.parsePlaylists(contents);
                        Retriever.this.retrievedContents.stream()
                                .skip(ClientCode.maxPerPage * Retriever.this.currentPage)
                                .limit(ClientCode.maxPerPage)
                                .forEach((item) -> System.out.println(item + System.lineSeparator()));

                        System.out.println("---PAGE " + (Retriever.this.currentPage + 1) +
                                " OF " + Retriever.this.totalPages + "---");
                    } catch (Exception e) {
                        System.out.println(e.getMessage());
                    }
                } else {
                    System.out.println("Unknown category name.");
                }
            }
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
        if (args.length % 2 != 0) {
            throw new IllegalArgumentException("Wrong format");
        }

        BiConsumer<String, String> parseArg = (arg, value) -> {
            if ("-access".equals(arg)) {
                access = value;
            } else if ("-resource".equals(arg)) {
                resource = value;
            } else if ("-page".equals(arg)) {
                maxPerPage = Integer.parseInt(value);
            }
        };

        for (int i = 0; i < args.length / 2; i += 2) {
            parseArg.accept(args[i], args[i + 1]);
        }
    }

    public static boolean isAccessGranted() {
        return isAccessGranted;
    }
}