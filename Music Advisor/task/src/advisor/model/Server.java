package advisor.model;

import com.sun.net.httpserver.HttpServer;

import java.io.IOException;

import java.net.InetSocketAddress;

public class Server {
    private Code code = new Code();
    private HttpServer server;

    public Code start() {
        try {
            this.server = HttpServer.create();
            this.server.bind(new InetSocketAddress(8080), 0);
            createContext();
            this.server.start();

            return this.code;
        } catch (IOException e) {
            e.printStackTrace();
            throw new IllegalStateException();
        }
    }

    private void createContext() {
        this.server.createContext("/", (exchange) -> {
            String msg = "Authorization code not found. Try again.";
            String query = exchange.getRequestURI().getQuery();
            if (query != null && query.matches("^code=.+")) {
                msg = "Got the code. Return back to your program.";
                this.code.setCode(query.replaceFirst("^code=", ""));
            }
            exchange.sendResponseHeaders(200, msg.length());
            exchange.getResponseBody().write(msg.getBytes());
            exchange.close();
        });
    }

    public void stop() {
        this.server.stop(1);
    }
}
