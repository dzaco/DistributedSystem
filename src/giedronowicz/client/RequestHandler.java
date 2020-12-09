package giedronowicz.client;

import giedronowicz.console.Logger;
import giedronowicz.server.PowService;
import giedronowicz.server.Request;

import java.io.IOException;
import java.net.DatagramPacket;

public class RequestHandler {
    private static final Logger logger = Logger.of(RequestHandler.class);

    public static DatagramPacket handle(DatagramPacket packet) {
        try {
            Request request = new Request(packet.getData());
            byte[] buffer = handle(request).getBytes();
            var responsePacket = new DatagramPacket(buffer, buffer.length,
                    packet.getAddress(), packet.getPort());
            return responsePacket;

        } catch (IOException e) {
            logger.error("Cannot extract request from packet");
            return new DatagramPacket( new byte[]{}, 0,
                    packet.getAddress(),packet.getPort());
        }
    }

    public static String handle(Request request) {
        return switch (request.getCommand()) {
            case "exit" -> {
                logger.info("One of the clients is off now");
//                ServerTCP.remove(client);
                yield request.getCommand();
            }
            case "pow" -> {
                var res = request.getParam().map(PowService::pow);
                if(res.isPresent()) yield String.valueOf(res.get());
                else {
                    logger.error("Command need parameter");
                    yield "Command need parameter";
                }
            }
            default -> {
                logger.error("Unknown command");
                yield "Unknown command";
            }
        };
    }

    public static String handle(ClientTCP client) {
        try {
            return RequestHandler.handle( new Request(client.read()) );

        } catch (IOException e) {
            logger.error("Cannot read");
            return "Cannot read request";
        }
    }
}
