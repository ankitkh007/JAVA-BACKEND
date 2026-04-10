package server;

import repository.UserRepository;
import service.UserService;
import controller.UsersHandler;
import com.sun.net.httpserver.*;

import java.io.*;
import java.net.InetSocketAddress;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Server {
    public static void main(String[] args) throws IOException {
        UserRepository repository = new UserRepository();
        UserService service = new UserService(repository);

        HttpServer server = HttpServer.create(new InetSocketAddress(8080), 0);
        server.createContext("/users", new UsersHandler(service));
        ExecutorService executor = Executors.newFixedThreadPool(1);
        server.setExecutor(executor);
        server.start();
        System.out.println("Server started on port 8080");
    }
}
