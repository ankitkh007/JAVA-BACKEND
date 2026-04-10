package controller;

import service.UserService;
import model.User;

import com.sun.net.httpserver.*;
import com.fasterxml.jackson.databind.ObjectMapper;
//import java.util.*;
import java.io.*;

public class UsersHandler implements HttpHandler {
    private UserService service;
    private ObjectMapper mapper = new ObjectMapper();

    public UsersHandler(UserService service) {
        this.service = service;
    }

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        String method = exchange.getRequestMethod();

        if (method.equals("GET"))
            handleGET(exchange);
        else if (method.equals("POST"))
            handlePOST(exchange);
        else
            exchange.sendResponseHeaders(405, -1);
    }

    private void handleGET(HttpExchange exchange) throws IOException {
        String path = exchange.getRequestURI().getPath();
        String parts[] = path.split("/");

        if (parts.length == 2) {
            String json = mapper.writeValueAsString(service.getAllUsers());
            exchange.getResponseHeaders().add("Content-Type", "application/json");
            exchange.sendResponseHeaders(200, json.getBytes().length);
            OutputStream os = exchange.getResponseBody();
            os.write(json.getBytes());
            os.close();
        } else if (parts.length == 3) {
            try {
                int id = Integer.parseInt(parts[2]);
                User user = service.getUserById(id);
                if (user != null) {
                    String json = mapper.writeValueAsString(user);
                    exchange.getResponseHeaders().add("Content-Type", "application/json");
                    exchange.sendResponseHeaders(200, json.getBytes().length);
                    OutputStream os = exchange.getResponseBody();
                    os.write(json.getBytes());
                    os.close();
                } else {
                    String response = "User NOT FOUND";
                    exchange.sendResponseHeaders(404, response.length());
                    OutputStream os = exchange.getResponseBody();
                    os.write(response.getBytes());
                    os.close();
                }
            } catch (NumberFormatException e) {
                String response = "Invalid User Id";
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

            if (service.createUser(user)) {
                response = "User Added";
                exchange.sendResponseHeaders(201, response.length());
            } else {
                response = "User Already Exists";
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
