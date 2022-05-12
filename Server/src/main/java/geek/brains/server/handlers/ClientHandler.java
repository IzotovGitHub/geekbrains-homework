package geek.brains.server.handlers;

import geek.brains.server.connections.Connection;
import geek.brains.server.constt.Command;
import geek.brains.server.entities.User;
import geek.brains.server.network.Data;
import geek.brains.server.services.ConnectionService;
import geek.brains.server.services.UserService;
import geek.brains.server.services.impl.ConnectionServiceImpl;
import geek.brains.server.services.impl.UserServiceImpl;

import java.util.Map;
import java.util.stream.Collectors;

import static geek.brains.server.constt.BodyKeys.*;
import static geek.brains.server.constt.BodyKeys.MESSAGE;
import static geek.brains.server.constt.Command.*;
import static geek.brains.server.constt.ConnectionStatus.*;
import static java.util.Objects.nonNull;

public class ClientHandler {

    private final Connection connection;
    private User user;

    private final UserService userService = UserServiceImpl.getInstance();
    private final ConnectionService connectionService = ConnectionServiceImpl.getInstance();

    private ClientHandler(Connection connection) {
        this.connection = connection;
    }

    public static ClientHandler newClientHandler(Connection connection) {
        return new ClientHandler(connection);
    }

    public void handle() {
        new Thread(() -> {
            while (!CLOSE.equals(connection.getStatus())) {
                Data data = connection.waitData();
                Map<String, String> body = data.getBody();
                Command command = data.getCommand();

                if (LOG_IN.equals(command)) {
                    registration(data);
                } else if (AUTH.equals(command)) {

                } else if (!CONNECTION.equals(connection.getStatus())) {
                    connection.sendErrMessage("Пожалуйста, пройдите авторизацию");
                } else if (WRITE.equals(command)) {
                    String userName = body.get(USER_NAME);
                    User recipient = userService.findByUserName(userName);
                    recipient.getConnection().sendData(new Data(Command.MESSAGE, Map.of(
                            SENDER, user.getUsername(),
                            MESSAGE, body.get(MESSAGE))));
                } else if (END.equals(command)) {
                    connectionService.getAllExistExcept(connection)
                            .forEach(recipientConnection ->
                                    recipientConnection.sendData(new Data(END, Map.of(SENDER, user.getUsername()))));
                    connection.sendData(new Data(CLOSE_CONNECTION));
                    connection.close();
                    connectionService.patch(user.getUsername(), connection);
                } else if (SEND_ALL.equals(command)) {
                    connectionService.getAllExistExcept(connection)
                            .forEach(recipientConnection ->
                                    recipientConnection.sendData(
                                            new Data(Command.MESSAGE, Map.of(
                                                    SENDER, user.getUsername(),
                                                    MESSAGE, body.get(MESSAGE)))));
                } else if (UPDATE_USER_LIST.equals(command)) {
                    String userNames = userService.getAllUsers().stream()
                            .filter(user -> CONNECTION.equals(user.getConnection().getStatus()))
                            .map(User::getUsername)
                            .collect(Collectors.joining(","));
                    connection.sendData(new Data(UPDATE_USER_LIST, Map.of(USER_NAMES, userNames)));
                }
            }
        }).start();
    }

    private void registration(Data data) {
        connection.setStatus(REGISTRATION);
        while (REGISTRATION.equals(connection.getStatus())) {
            Map<String, String> body = data.getBody();
            String login = body.get(LOGIN);
            String password = body.get(PASSWORD);
            String userName = body.get(USER_NAME);

            User userFromDB = userService.findByUserName(userName);
            if (nonNull(userName) && userName.equals(userFromDB.getUsername())) {
                connection.setStatus(OPEN);
                connection.sendErrMessage("Пользователь с таким именем уже существует");
            } else if (nonNull(login) && login.equals(userFromDB.getLogin())) {
                connection.setStatus(OPEN);
                connection.sendErrMessage("Пользователь с таким логином уже существует");
            } else if (password == null || password.length() <= 5) {
                connection.setStatus(OPEN);
                connection.sendErrMessage("Пароль не соответствует парольной политике");
            } else {
                user = new User(login, password, userName, connection);
                userService.patch(user);
                connection.connect();
                connection.sendData(new Data(AUTH_OK, Map.of(USER_NAME, user.getUsername())));
            }
        }
    }
}
