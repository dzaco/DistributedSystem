package giedronowicz.client;

import giedronowicz.console.Logger;
import giedronowicz.console.UIConsole;

import java.io.IOException;

public class ClientSite {
    private static Logger logger = Logger.of(ClientSite.class);

    public static void main(String... args) throws IOException {

        IClient client = new ClientUDP();
        UIConsole console = new UIConsole();

        mainLoop:
        while (true) {
            // request
            String request = console.getRequest();
            switch (request) {
                case "exit" -> {
                    client.exit();
                    break mainLoop;
                }
                case "help", "h" -> console.help();
                default -> client.write(request);
            }


            // response
            String response = client.read();
            logger.info(response, "Server");
            if(response.equals("off"))
                break;

        }

        client.close();
        System.exit(0);


    }
}
