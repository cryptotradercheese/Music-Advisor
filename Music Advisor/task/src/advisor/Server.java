package advisor;

import com.sun.net.httpserver.HttpServer;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.URI;
import java.net.http.*;

public class Server {
    private String code;
    private HttpServer server;

    public void start() {
        try {
            this.server = HttpServer.create();
            this.server.bind(new InetSocketAddress(8080), 0);
            server.start();
        } catch (IOException e) {
            e.printStackTrace();
            throw new IllegalStateException();
        }
    }

    public void createContext() {
        this.server.createContext("/", (exchange) -> {
            String msg = "Authorization code not found. Try again.";
            String query = exchange.getRequestURI().getQuery();
            if (query != null && query.matches("^code=.+")) {
                msg = "Got the code. Return back to your program.";
                this.code = query.replaceFirst("^code=", "");
            }
            exchange.sendResponseHeaders(200, msg.length());
            exchange.getResponseBody().write(msg.getBytes());
            exchange.close();
        });
    }

    public void stop() {
        this.server.stop(1);
    }

    public String getTokenData(String code, String url) {
        HttpRequest request = HttpRequest
                .newBuilder()
                .header("Content-Type", "application/x-www-form-urlencoded")
                .POST(HttpRequest.BodyPublishers.ofString("grant_type=authorization_code&code=" + code + "&redirect_uri=http://localhost:8080&client_id=f938a23eaa914af89b3c4c60c1fbd9a4&client_secret=bbd31e461da84025b1f9210204073be4"))
                .uri(URI.create(url + "/api/token"))
                .build();

        try {
            HttpClient client = HttpClient.newBuilder().build();
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            this.code = null;
            return response.body();
        } catch (IOException | InterruptedException e) {
            throw new IllegalStateException(e);
        }
    }

    public String getCode() {
        if (isCodeReceived()) {
            return this.code;
        } else {
            throw new IllegalStateException("Code isn't received");
        }
    }

    public boolean isCodeReceived() {
        if (this.code != null) {
            return true;
        } else {
            return false;
        }
    }
}
