import com.sun.net.httpserver.*;
import java.net.InetSocketAddress;
import java.io.*;

public class myserver_2 {
    public static void main(String[] args) throws IOException {
        HttpServer server = HttpServer.create(new InetSocketAddress(8080), 0);
        server.createContext("/hello", new HelloHandler());
        server.createContext("/bye", new ByeHandler());
        server.createContext("/Users", new UsersHandler());

        server.setExecutor(null);
        server.start();

        System.out.println("Server started on port 8080");
    }

    // /hello route Handler
    static class HelloHandler implements HttpHandler {
        @Override
        public void handle(HttpExchange exchange) throws IOException {
            try {
                String path = exchange.getRequestURI().getPath();
                String parts[] = path.split("/");
                String response = "";

                if (parts[2].equals("ankit"))
                    response = "Hello Ankit";
                else
                    response = "Hello Guest";

                exchange.sendResponseHeaders(200, response.length());
                OutputStream os = exchange.getResponseBody();
                os.write(response.getBytes());
                os.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    // /bye route Handler
    static class ByeHandler implements HttpHandler {
        @Override
        public void handle(HttpExchange exchange) throws IOException {
            sendResponse(exchange, "Bye Route hit!");
        }
    }

    // /Users route Handler
    static class UsersHandler implements HttpHandler {
        @Override
        public void handle(HttpExchange exchange) throws IOException {
            sendResponse(exchange, "Users Route hit!");
        }
    }

    // common Response Trigger
    static void sendResponse(HttpExchange exchange, String response) throws IOException {
        try {
            exchange.sendResponseHeaders(200, response.length());
            System.out.println(exchange.getRequestURI().getPath());
            OutputStream os = exchange.getResponseBody();
            os.write(response.getBytes());
            os.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}