package giedronowicz;

import java.io.IOException;
import java.lang.ref.Reference;
import java.util.Optional;
import java.util.function.Consumer;

public class Request {
    private final String raw;
    private String[] data;
    private int commandIndex, paramIndex, optionIndex;

    public Request(String raw) throws IOException {
        if(raw == null)
            throw new IOException("Request is null");
        this.raw = raw;
        this.data = this.raw.split(" ");
        this.commandIndex = 0;
        this.paramIndex = data.length  > 0 ? 1 : -1;
        this.optionIndex = data.length > 1 ? 2 : -1;
    }

    public String getRaw() {
        return this.raw;
    }

    public String getCommand() {
        return data[commandIndex];
    }

    public Optional<String> getParam() {
        return paramIndex != -1 ? Optional.of(data[paramIndex]) : Optional.empty();
    }

    public Optional<String> getOption() {
        return optionIndex != -1 ? Optional.of(data[optionIndex]) : Optional.empty();
    }



}
