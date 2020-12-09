package giedronowicz.server;

import giedronowicz.console.Logger;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.lang.ref.Reference;
import java.util.*;
import java.util.function.Consumer;
import java.util.stream.Stream;

public class Request {
    private static final Logger logger = Logger.of(Request.class);
    private String raw;
    private Map<Index,String> map;

    public Request(byte[] data) throws IOException {
        List<Byte> tmp = new ArrayList<>();
        for (byte b : data) {
            if( b != 0 )
                tmp.add(b);
            else break;
        }

        byte[] bytes = new byte[tmp.size()];
        for (int i = 0; i < bytes.length; i++) {
            bytes[i] = tmp.get(i);
        }

        this.init( new String(bytes) );

    }

    private enum Index {
        COMMAND, PARAM, OPTION;
    }

    public Request(String raw) throws IOException {

        this.init(raw);
    }
    private void init(String raw) throws IOException {
        logger.info("init request from raw sentence: " + raw);
        if(raw == null)
            throw new IOException("Request is null");
        this.raw = raw;

        String[] data = this.raw.split(" ");
        this.map = new HashMap<>();
        for (int i = 0; i < data.length; i++) {
            map.put(Index.values()[i], data[i]);
        }
    }

    public String getRaw() {
        return this.raw;
    }

    public String getCommand() {
        return this.map.get(Index.COMMAND);
    }
    public Optional<String> getParam() {
        return this.get(Index.PARAM);
    }
    public Optional<String> getOption() {
        return this.get(Index.OPTION);
    }

    private Optional<String> get(Index index) {
        return this.map.get(index) != null ?
                Optional.ofNullable(this.map.get(index)) : Optional.empty();
    }

}
