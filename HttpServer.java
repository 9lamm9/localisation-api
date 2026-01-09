package server;

import java.net.*;
import db.DatabaseManager;

public class HttpServer {

    public static DatabaseManager db;

    public static void main(String[] args) throws Exception {

        db = new DatabaseManager();
        ServerSocket server = new ServerSocket(8080);

        System.out.println("Serveur SAE302 actif sur le port 8080");

        while (true) {
            Socket client = server.accept();
            new Thread(new ClientHandler(client)).start();
        }
    }
}
