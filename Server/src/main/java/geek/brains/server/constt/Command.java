package geek.brains.server.constt;

public enum Command {
    AUTH("/auth"),
    AUTH_OK("/auth-ok"),
    LOG_IN("/log-in"),
    END("/end"),
    WRITE("/w"),
    SEND_ALL("/send-all"),
    MESSAGE("/message"),
    ERR_MESSAGE("/err-message"),
    UPDATE_USER_LIST("/update-user-list"),
    CLOSE_CONNECTION("/close-connection"),
    HELP("/help");


    private String command;

    Command(String command) {
        this.command = command;
    }

    public String getCommand() {
        return command;
    }
}
