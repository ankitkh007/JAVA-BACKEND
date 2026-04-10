import com.fasterxml.jackson.databind.*;
import com.sun.net.httpserver.*;
import java.net.InetSocketAddress;
import java.util.*;
import java.io.*;

public class mini_project {
    public static void main(String[] args) throws IOException {
        HttpServer server = HttpServer.create(new InetSocketAddress(8080), 0);
        server.createContext("/users", new UsersHandler());
        server.setExecutor(null);
        server.start();
        System.out.println("Server started on port 8080");
    }

    static Map<Integer, User> users = new HashMap<>();
    static ObjectMapper mapper = new ObjectMapper();

    static class UsersHandler implements HttpHandler {
        @Override
        public void handle(HttpExchange exchange) throws IOException {
            String method = exchange.getRequestMethod();

            if (method.equals("GET"))
                handleGET(exchange);
            else if (method.equals("POST"))
                handlePOST(exchange);
            else {
                exchange.sendResponseHeaders(405, -1);
            }
        }

        private void handleGET(HttpExchange exchange) throws IOException {
            String path = exchange.getRequestURI().getPath();
            String parts[] = path.split("/");

            if (parts.length == 2) // That means /users --> return all users
            {
                String json = mapper.writeValueAsString(users.values());
                exchange.getResponseHeaders().add("Content-type", "application/json");
                exchange.sendResponseHeaders(200, json.getBytes().length);
                OutputStream os = exchange.getResponseBody();
                os.write(json.getBytes());
                os.close();
            } else if (parts.length == 3) // That means /users/{id} --> return that particuar user only
            {
                try {
                    int id = Integer.parseInt(parts[2]); // extracted particular user id

                    if (users.containsKey(id)) {
                        String json = mapper.writeValueAsString(users.get(id));
                        exchange.getResponseHeaders().add("Content-type", "application/json");
                        exchange.sendResponseHeaders(200, json.getBytes().length);
                        OutputStream os = exchange.getResponseBody();
                        os.write(json.getBytes());
                        os.close();
                    } else {
                        String response = "User Not Found.";
                        exchange.sendResponseHeaders(404, response.length());
                        OutputStream os = exchange.getResponseBody();
                        os.write(response.getBytes());
                        os.close();
                    }
                } catch (NumberFormatException e) {
                    String response = "Invalid User id";
                    exchange.sendResponseHeaders(400, response.length());
                    OutputStream os = exchange.getResponseBody();
                    os.write(response.getBytes());
                    os.close();
                }
            } else {
                String response = "Invalid Path";
                exchange.sendResponseHeaders(400, response.length());
                OutputStream os = exchange.getResponseBody();
                os.write(response.getBytes());
                os.close();
            }
        }

        private void handlePOST(HttpExchange exchange) throws IOException {
            try {
                InputStream is = exchange.getRequestBody();
                User user = mapper.readValue(is, User.class);
                String response = "";
                if (!users.containsKey(user.id)) {
                    users.put(user.id, user);
                    response = "User Added";
                    exchange.sendResponseHeaders(201, response.length());
                } else {
                    response = "User already exists!";
                    exchange.sendResponseHeaders(409, response.length());
                }
                OutputStream os = exchange.getResponseBody();
                os.write(response.getBytes());
                os.close();
            } catch (Exception e) {
                String error = "Invalid JSON";
                exchange.sendResponseHeaders(400, error.length());
                OutputStream os = exchange.getResponseBody();
                os.write(error.getBytes());
                os.close();
            }
        }
    }
}
