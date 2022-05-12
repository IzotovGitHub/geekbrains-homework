package geek.brains.server.network;

import geek.brains.server.constt.Command;

import java.io.Serializable;
import java.util.Map;

import static java.util.Collections.emptyMap;

public class Data implements Serializable {

    private final Command COMMAND;

    private Map<String, String> body = emptyMap();

    public Data() {
        COMMAND = null;
    }

    public Data(final Command COMMAND) {
        this(COMMAND, emptyMap());
    }

    public Data(Command COMMAND, Map<String, String> body) {
        this.COMMAND = COMMAND;
        this.body = body;
    }

    public Command getCommand() {
        return COMMAND;
    }

    public Map<String, String> getBody() {
        return body;
    }

    public void setBody(Map<String, String> body) {
        this.body = body;
    }
}
