package giedronowicz.server;

import java.io.IOException;
import java.lang.ref.Reference;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.function.Consumer;

public class Request {
    private final String raw;
    private Map<Index,String> map;

    private enum Index {
        COMMAND, PARAM, OPTION;
    }

    public Request(String raw) throws IOException {
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
