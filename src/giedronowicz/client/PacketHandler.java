package giedronowicz.client;

import giedronowicz.console.Logger;
import giedronowicz.server.Request;

import java.io.IOException;
import java.net.DatagramPacket;

public class PacketHandler implements Runnable{
    private static final Logger logger = Logger.of(PacketHandler.class);
    private final DatagramPacket packet;

    public PacketHandler(DatagramPacket packet) {
        this.packet = packet;
    }

    public DatagramPacket getPacket() {
        return packet;
    }

    @Override
    public void run() {
        logger.info("Handle packet: " + packet);

        while (true) {
            try {
                Request request = new Request( packet.getData() );
                logger.info("Request: " + request);



            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }
}
