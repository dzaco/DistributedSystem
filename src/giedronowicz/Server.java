package giedronowicz;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Server {
    private static final Logger logger = Logger.of(Server.class);
    private static List<ClientHandler> clients = new ArrayList<>();
    private static ExecutorService pool = Executors.newFixedThreadPool(4);

    public static void main(String... args) throws IOException {
        ServerSocket server = new ServerSocket(4999);

        while (true) {
            logger.info("Waiting for a Client...");
            // waiting for socket
            IClient<Socket> client = new ClientTCP( server.accept() );
            logger.info("Client connected");

            ClientHandler clientHandler = new ClientHandler(client);
            clients.add(clientHandler);
            logger.info("Total number of clients: " + clients.size());

            pool.execute(clientHandler);
        }


    }

    public static void send(IClient client, String response) {
        logger.info("Response message: " + response);
        client.getOutput().println(response);
        client.getOutput().flush();
    }

    public static void remove(IClient client) {
        clients.removeIf(clientHandler -> clientHandler.getClient() == client);
        logger.info("Removed client from list");
        logger.info("Total number of clients: " + clients.size() );
    }

    public static void off() throws IOException {
        for (ClientHandler clientHandler : clients) {
            send(clientHandler.getClient(), "off");
        }
    }
}
