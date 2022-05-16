package geek.brains.server.constt;

public enum Command {
    LOG_IN,
    LOG_IN_OK,
    AUTH,
    AUTH_OK,
    END,
    WRITE,
    SEND_ALL,
    MESSAGE,
    ERR_MESSAGE,
    UPDATE_USER_LIST,
    CLOSE_CONNECTION;
}
