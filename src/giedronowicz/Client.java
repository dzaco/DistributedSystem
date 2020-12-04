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
        Scanner keyboard = new Scanner(System.in);

        mainLoop:
        while (true) {
            System.out.println(">");
            // request
            displayMenu();
            System.out.print("Action: ");
            switch (keyboard.next()) {
                case "0" -> {
                    exit(out);
                    break mainLoop;
                }
                case "off" -> sendRequest(out, "off");
                case "1" -> sendNumber(out);
                default -> {
                    logger.error("Unknown request\tLunch main feature - power number");
                    sendNumber(out);
                }
            }


            // response
            String str = in.readLine();
            logger.info(str, "Server");
            if(str.equals("off"))
                break;

        }

        socket.close();
        out.close();
        in.close();
        System.exit(0);


    }

    public static void displayMenu() {
        System.out.format("%10s %n", "MENU");
        System.out.format("%-4s %-10s %n", "0.", "Exit");
        System.out.format("%-4s %-10s %n", "1.", "Number");
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
