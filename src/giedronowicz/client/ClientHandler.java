package giedronowicz.client;

import giedronowicz.console.Logger;
import giedronowicz.server.PowService;
import giedronowicz.server.Request;
import giedronowicz.server.Server;

import java.io.IOException;

public class ClientHandler implements Runnable {

    private final IClient client;
//    private final BufferedReader in;
//    private final PrintWriter out;
    private static final Logger logger = Logger.of(ClientHandler.class);

    public ClientHandler(IClient clientSocket) throws IOException {
        client = clientSocket;
    }

    public IClient getClient() {
        return this.client;
    }

    @Override
    public void run() {

        logger.info("Handle client: " + client);
        try {
            while(true) {

                Request request = new Request(client.read());
                logger.info("Request: " + request);

                String response = switch (request.getCommand()) {
                    case "exit" -> {
                        logger.info("One of the clients is off now");
                        Server.remove(client);
                        yield request.getCommand();
                    }
                    case "off" -> {
                        logger.info("Client " + client + " shutdown this server");
                        Server.off();
                        yield request.getCommand();
                    }
                    case "pow" -> {
                        var res = request.getParam().map(PowService::pow);
                        if(res.isPresent()) yield String.valueOf(res.get());
                        else throw new IOException("Failed work with param");
                    }

                    default -> {
                        logger.error("Unknown command");
                        yield "Unknown command";
                    }
                };

                Server.send(client, response);
                
            }

        }
        catch (IOException e){
            logger.error("Throws IOException");
            logger.error(e.getMessage());
        }
        finally {
            try {
                client.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }



}
