package giedronowicz.client;

import giedronowicz.console.Logger;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;

public class ClientTCP implements IClient<Socket>{

    private static final Logger logger = Logger.of(ClientTCP.class);

    private Socket socket;
    private PrintWriter out;
    private BufferedReader in;

    public ClientTCP() throws IOException {
        this( new Socket("localhost", 4999) );
    }
    public ClientTCP( Socket socket ) throws IOException {
        logger.info("New instance");
        this.socket = socket;
        out = new PrintWriter(socket.getOutputStream());
        in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
    }

    @Override
    public void bind(InetSocketAddress address) throws IOException {
        this.socket.bind(address);
    }

    @Override
    public void bind(String address, int port) throws IOException {
        this.getSocket().bind( new InetSocketAddress(address,port) );
    }

    @Override
    public void close() throws IOException {
        logger.info("Closing client's socket");
        this.getInput().close();
        this.getOutput().close();
        this.getSocket().close();
    }

    @Override
    public String read() throws IOException {
        logger.info("Reading ...");
        return this.getInput().readLine();
    }

    @Override
    public void write(String request) throws IOException {
        logger.info("Writing request: " + request);
        if(this.getOutput() == null ) throw new IOException("Output stream is null");
        this.getOutput().println(request);
        this.getOutput().flush();
    }

    @Override
    public InetAddress getAddress() {
        return this.getSocket().getInetAddress();
    }

    @Override
    public int getPort() {
        return this.getSocket().getPort();
    }

    public BufferedReader getInput() {
        return this.in;
    }

    public PrintWriter getOutput() {
        return this.out;
    }

    @Override
    public Socket getSocket() {
        return this.socket;
    }

    @Override
    public String toString() {
        return "ClientTCP{" +
                "socket=" + socket +
                '}';
    }



    public void exit() throws IOException {
        this.write("exit");
    }
}
