package giedronowicz.client;

import giedronowicz.console.Logger;

import java.io.IOException;
import java.net.*;

public class ClientUDP implements IClient<DatagramSocket> {
    private static final Logger logger = Logger.of(ClientUDP.class);
    private final DatagramSocket socket;
    private final SocketAddress serverAddress;


    public ClientUDP() throws SocketException {
        this( new InetSocketAddress("localhost", 5000) , new DatagramSocket() );
    }
    public ClientUDP(DatagramSocket socket) {
        this( new InetSocketAddress("localhost", 5000) , socket);
    }
    public ClientUDP( SocketAddress serverAddress, DatagramSocket socket ) {
        logger.info("New instance");
        this.serverAddress = serverAddress;
        this.socket = socket;
    }



    @Override
    public void bind(InetSocketAddress address) throws IOException {
        this.getSocket().bind(address);
    }

    @Override
    public void bind(String address, int port) throws IOException {
        this.getSocket().bind( new InetSocketAddress(address, port) );
    }

    @Override
    public void close() throws IOException {
        logger.info("Closing client's socket");
        this.getSocket().close();
    }

    @Override
    public String read() throws IOException {
        byte[] buffer = new byte[1024];
        DatagramPacket packet = new DatagramPacket(buffer, buffer.length);

        this.getSocket().receive(packet);
        return new String(packet.getData());
    }

    @Override
    public void write(String request) throws IOException {
        logger.info("Writing request: " + request);

        byte[] buffer = request.getBytes();
        var packet = new DatagramPacket(buffer, buffer.length, this.serverAddress);

        this.getSocket().send(packet);
    }

    @Override
    public InetAddress getAddress() {
        return this.getSocket().getInetAddress();
    }

    @Override
    public int getPort() {
        return this.getSocket().getPort();
    }

    @Override
    public DatagramSocket getSocket() {
        return this.socket;
    }

    @Override
    public void exit() throws IOException {
        this.write("exit");
    }
}
