package giedronowicz;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class Client {
    private static Logger logger = Logger.of(Client.class);

    public static void main(String... args) throws IOException {
        Socket socket = new Socket("localhost", 4999);

        PrintWriter out = new PrintWriter(socket.getOutputStream());
        BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        UIConsole console = new UIConsole();

        mainLoop:
        while (true) {
            // request
            String request = console.getRequest();
            switch (request) {
                case "exit" -> {
                    exit(out);
                    break mainLoop;
                }
                case "help", "h" -> console.help();
                default -> sendRequest(out, request);
            }


            // response
            String response = in.readLine();
            logger.info(response, "Server");
            if(response.equals("off"))
                break;

        }

        socket.close();
        out.close();
        in.close();
        System.exit(0);


    }


    public static void exit(PrintWriter out) {
        logger.info("Sending the exit request");
        out.println("exit");
        out.flush();
    }

    public static void sendNumber(PrintWriter out) {
        int number = getNumber();
        logger.info("Sending the number = " + number);
        out.println(number);
        out.flush();

    }
    public static void sendRequest(PrintWriter out, String request) {
        logger.info("Sending request: " + request);
        out.println(request);
        out.flush();
    }
    public static int getNumber() {
        Scanner scanner = new Scanner(System.in);
        logger.info("Type Your number = ", false);
        int number = 0;

        try {
            number = scanner.nextInt();
        } catch (Exception e) {
            logger.error("That is not a number");
        }

        return number;
    }
}
