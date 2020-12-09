package giedronowicz.server;

import giedronowicz.client.RequestHandler;
import giedronowicz.console.Logger;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;

public class ServerUDP {
    private static final Logger logger = Logger.of(ServerUDP.class);

    public static void main(String...args) throws IOException {
        DatagramSocket socket = new DatagramSocket(5000);
        logger.info("Server running");

        while (true) {
            byte[] buffer = new byte[1024];
            DatagramPacket packet = new DatagramPacket(buffer, buffer.length);

            socket.receive(packet);
            logger.info("Received packet");

            logger.info("Sending response");
            socket.send(RequestHandler.handle(packet));

        }
    }

}
