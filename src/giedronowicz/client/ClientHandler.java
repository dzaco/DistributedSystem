package giedronowicz.client;

import giedronowicz.console.Logger;
import giedronowicz.server.Request;
import giedronowicz.server.ServerTCP;

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

                String response = RequestHandler.handle(request);

                ServerTCP.send(client, response);
                
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
