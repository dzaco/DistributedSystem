package giedronowicz;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.Executor;
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
            Socket client = server.accept();
            logger.info("Client connected");

            ClientHandler clientHandler = new ClientHandler(client);
            clients.add(clientHandler);
            logger.info("Total number of clients: " + clients.size());

            pool.execute(clientHandler);
        }


    }

    public static Optional<Integer> extractNumber(String request) {
        int number = 0;
        try { // is number ?
            number = Integer.parseInt(request);
            logger.info("Client sent the number = " + number);

            return Optional.of(number);
        } catch (Exception e) {
            logger.error("Request is not a number");
            return Optional.empty();
        }
    }

    public static String powMsg(int number){
        logger.info("Calculate power for number = " + number);
        return number + "^2 = " + number * number;
    }

    public static void sendMsg(PrintWriter out, String msg) {
        logger.info("Response message: " + msg);
        out.println(msg);
        out.flush();
    }

    public static void remove(Socket client) {
        clients.removeIf(clientHandler -> clientHandler.getClient() == client);
        logger.info("Removed client from list");
        logger.info("Total number of clients: " + clients.size() );
    }

    public static void off() throws IOException {
        for (ClientHandler client : clients) {
            PrintWriter out = new PrintWriter( client.getClient().getOutputStream() );
            sendMsg(out, "off");
        }
    }
}
