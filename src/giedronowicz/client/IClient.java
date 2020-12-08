package giedronowicz.client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.InetSocketAddress;

public interface IClient<T> {

    void bind(InetSocketAddress address) throws IOException;
    void bind(String address, int port) throws IOException;
    void close() throws IOException;
    String read() throws IOException;
    void write(String request);
    InetAddress getAddress();
    int getPort();
    BufferedReader getInput();
    PrintWriter getOutput();
    T getSocket();
    void exit();
}
