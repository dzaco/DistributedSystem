package giedronowicz;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Optional;

public class ClientHandler implements Runnable {

    private final Socket client;
    private final BufferedReader in;
    private final PrintWriter out;
    private static final Logger logger = Logger.of(ClientHandler.class);

    public ClientHandler(Socket clientSocket) throws IOException {
        client = clientSocket;
        in = new BufferedReader( new InputStreamReader(client.getInputStream()));
        out = new PrintWriter( client.getOutputStream() );
    }

    @Override
    public void run() {

        logger.info("Handle client: " + client);
        try {
            while(true) {
                String request = in.readLine();
                if(request == null) continue;
                if (request.equals("exit")){
                    logger.info("One of the clients is off now");
                    Server.remove(client);
                }
                else if(request.equals("off")) {
                    Server.off();
                    System.exit(0);
                }

                Server.extractNumber(request)
                        .ifPresent(number -> {
                            Server.sendMsg(out, Server.powMsg(number));
                        });
            }

        }
        catch (IOException e){
            logger.error("Throws IOException");
        }
        finally {
            out.close();
            try {
                in.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

    public Socket getClient() {
        return this.client;
    }

}
