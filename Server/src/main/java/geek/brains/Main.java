package geek.brains;

import geek.brains.server.Server;

public class Main {
    public static void main(String[] args) {
        new Server().start(8080);
    }
}
