package geek.brains.server.connections;

import geek.brains.server.constt.ConnectionStatus;
import geek.brains.server.network.Data;

import java.io.*;
import java.net.Socket;
import java.util.Map;
import java.util.Objects;

import static geek.brains.server.constt.BodyKeys.MESSAGE;
import static geek.brains.server.constt.Command.END;
import static geek.brains.server.constt.Command.ERR_MESSAGE;
import static geek.brains.server.constt.ConnectionStatus.*;

public class Connection {

    private Socket socket;

    private InputStream in;
    private OutputStream out;

    private ConnectionStatus status = OPEN;

    public Connection(Socket socket) {
        try {
            this.socket = socket;
            this.in = socket.getInputStream();
            this.out = socket.getOutputStream();
        } catch (IOException e) {
            throw new RuntimeException("Проблемы при создании соединения");
        }
    }

    public static Connection create(String host, int port) {
        try {
            Socket s = new Socket(host, port);
            return new Connection(s);
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("Невозможно установить сетевое соединение");
        }
    }

    public Data waitData() {
        try {
            return (Data) new ObjectInputStream(in).readObject();
        } catch (IOException e) {
            System.err.println("Соединение прервано");
            return new Data(END);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public void sendData(Data data) {
        try {
            new ObjectOutputStream(out).writeObject(data);
        } catch (IOException e) {
            System.err.println("Ошибка при отправке сообщения");
        }
    }

    public void sendErrMessage(String message) {
        try {
            new ObjectOutputStream(out).writeObject(new Data(ERR_MESSAGE, Map.of(MESSAGE, message)));
        } catch (IOException e) {
            System.err.println("Ошибка при отправке сообщения");
        }
    }

    public ConnectionStatus getStatus() {
        return status;
    }

    public void setStatus(ConnectionStatus status) {
        this.status = status;
    }

    public void connect() {
        status = CONNECTION;
    }

    public void close() {
        status = CLOSE;
        try {
            if (in != null) {
                in.close();
            }
            if (out != null) {
                out.close();
            }
            if (socket != null) {
                socket.close();
            }
        } catch (IOException e) {
            System.err.println("Ошибка при закрытии соединения");
            e.printStackTrace();
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Connection)) return false;
        Connection that = (Connection) o;
        return Objects.equals(socket, that.socket)
                && Objects.equals(in, that.in)
                && Objects.equals(out, that.out)
                && getStatus() == that.getStatus();
    }

    @Override
    public int hashCode() {
        return Objects.hash(socket, in, out, getStatus());
    }
}
