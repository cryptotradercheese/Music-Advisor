package advisor.controller;

import advisor.model.Code;
import advisor.model.Parser;
import advisor.model.Server;
import advisor.model.SpotifyInteraction;
import advisor.model.Retriever;
import advisor.view.ApiDisplay;
import advisor.view.Printer;

import java.util.Map;
import java.util.Scanner;
import java.util.function.BiConsumer;

public class Controller {
    private String access = "https://accounts.spotify.com";
    private String resource = "https://api.spotify.com";
    private String redirectUri = "http://localhost:8080";
    private String clientId = "f938a23eaa914af89b3c4c60c1fbd9a4";
    private int maxPerPage = 5;

    private MenuStrategy strategy;
    private String token = "";

    public Controller(String[] args) {
        parseArgs(args);
    }

    public void setStrategy(MenuStrategy strategy) {
        this.strategy = strategy;
    }

    public void execute () {
        this.strategy.execute();
    }

    public interface MenuStrategy {
        void execute();
    }

    public class AccessGranted implements MenuStrategy {
        private Scanner scanner = new Scanner(System.in);
        private String musicType;

        public AccessGranted(String musicType) {
            this.musicType = musicType;
        }

        @Override
        public void execute() {
            Retriever retriever = new Retriever(resource, token);
            ApiDisplay apiDisplay = new ApiDisplay(maxPerPage);

            if ("new".equals(this.musicType)) {
                apiDisplay.setRetrievedContents(retriever.retrieveNew());
                apiDisplay.display();
                paginate(apiDisplay);
            } else if ("featured".equals(this.musicType)) {
                apiDisplay.setRetrievedContents(retriever.retrieveFeature());
                apiDisplay.display();
                paginate(apiDisplay);
            } else if ("categories".equals(this.musicType)) {
                apiDisplay.setRetrievedContents(retriever.retrieveCategories());
                apiDisplay.display();
                paginate(apiDisplay);
            } else if (this.musicType.matches("^playlists .+")) {
                String category = this.musicType.replace("playlists ", "");
                Map<String, String> categoriesIds = Parser.parseCategoriesIds(
                        SpotifyInteraction.getContents(resource + "/v1/browse/categories", token)
                );
                String id = categoriesIds.get(category);

                if (id != null) {
                    try {
                        apiDisplay.setRetrievedContents(retriever.retrievePlaylists(id));
                        apiDisplay.display();
                        paginate(apiDisplay);
                    } catch (Exception e) {
                        Printer.displayError(e);
                    }
                } else {
                    Printer.displayIdError();
                }
            }
        }

        private void paginate(ApiDisplay apiDisplay) {
            String input;
            while (!"exit".equals(input = scanner.nextLine())) {
                try {
                    if ("prev".equals(input)) {
                        apiDisplay.prev();
                        apiDisplay.display();
                    } else if ("next".equals(input)) {
                        apiDisplay.next();
                        apiDisplay.display();
                    }
                } catch (Exception e) {
                    Printer.displayError(e);
                }
            }

            System.exit(0);
        }
    }

    public class Auth implements MenuStrategy {
        @Override
        public void execute() {
            Server server = new Server();
            Code code = server.start();

            Printer.link(access, clientId, redirectUri);
            while (!code.isCodeReceived());
            Printer.makingRequest();
            String response = SpotifyInteraction.getTokenData(code.getCode(), access);
            Printer.success();
            server.stop();

            token = Parser.parseToken(response);
        }
    }

    public class Exit implements MenuStrategy {
        @Override
        public void execute() {
            Printer.exit();
            System.exit(0);
        }
    }

    public class NoAccess implements MenuStrategy {
        @Override
        public void execute() {
            Printer.noAccess();
        }
    }

    private void parseArgs(String[] args) {
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

        for (int i = 0; i < args.length; i += 2) {
            parseArg.accept(args[i], args[i + 1]);
        }
    }
}
