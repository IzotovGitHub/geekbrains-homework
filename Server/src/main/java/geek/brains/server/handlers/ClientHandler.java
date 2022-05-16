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
import java.util.Set;
import java.util.stream.Collectors;

import static geek.brains.server.constt.BodyKeys.MESSAGE;
import static geek.brains.server.constt.BodyKeys.*;
import static geek.brains.server.constt.Command.*;
import static geek.brains.server.constt.ConnectionStatus.*;
import static geek.brains.server.entities.types.UserType.EMPTY_USER;
import static java.lang.String.format;
import static java.lang.String.join;
import static java.util.Objects.nonNull;

public class ClientHandler {

    private static final int MAX_AUTH_COUNT = 5;

    private final Connection connection;
    private User user;
    private int authCount = 0;

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
                    registration(body);
                } else if (AUTH.equals(command)) {
                    authentication(body);
                } else if (UPDATE_USER_LIST.equals(command)) {
                    updateUserList();
                } else if (END.equals(command)) {
                    executeEnd();
                } else if (!CONNECTION.equals(connection.getStatus())) {
                    connection.sendErrMessage("Пожалуйста, пройдите авторизацию");
                } else if (WRITE.equals(command)) {
                    executeWriteCommand(body);
                } else if (SEND_ALL.equals(command)) {
                    executeSendAllCommand(body);
                }
            }
        }).start();
    }

    private void registration(Map<String, String> body) {
        connection.setStatus(REGISTRATION);
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
            connection.sendData(new Data(LOG_IN_OK, Map.of(USER_NAME, user.getUsername())));
        }
    }

    private void authentication(Map<String, String> body) {
        if (authCount >= MAX_AUTH_COUNT) {
            connection.sendData(new Data(CLOSE_CONNECTION));
            connection.sendErrMessage("Превышено колличество попыток");
            connection.close();
            return;
        }

        connection.setStatus(AUTHENTICATION);
        String login = body.get(LOGIN);
        String password = body.get(PASSWORD);

        user = userService.findByLogin(login);
        if (user.getConnection() != null && CONNECTION.equals(user.getConnection().getStatus())) {
            authCount++;
            connection.setStatus(OPEN);
            connection.sendErrMessage(format("Пользователя с логином \"%s\" уже авторизован", login));
        } else if (user.equals(EMPTY_USER.getUser())) {
            authCount++;
            connection.setStatus(OPEN);
            connection.sendErrMessage(format("Пользователя с логином \"%s\" не существует", login));
        } else if (password == null || !password.equals(user.getPassword())) {
            authCount++;
            connection.setStatus(OPEN);
            connection.sendErrMessage("Введен неверный пароль");
        } else {
            authCount = 0;
            connection.connect();
            connection.sendData(new Data(AUTH_OK, Map.of(USER_NAME, user.getUsername())));
            connectionService.patch(user.getUsername(), connection);
        }
    }

    private void updateUserList() {
        Set<User> allUsers = userService.getAllUsers();

        Map<String, Connection> userNameConnectionMap = allUsers.stream()
                .filter(user -> CONNECTION.equals(user.getConnection().getStatus()))
                .collect(Collectors.toMap(User::getUsername, User::getConnection));

        String userNames = join(",", userNameConnectionMap.keySet());

        userNameConnectionMap
                .values()
                .forEach(connection -> connection.sendData(new Data(UPDATE_USER_LIST, Map.of(USER_NAMES, userNames))));
    }

    private void executeEnd() {
        if (CONNECTION.equals(connection.getStatus())) {
            connectionService.getAllExistExcept(connection)
                    .forEach(recipientConnection ->
                            recipientConnection.sendData(new Data(END, Map.of(SENDER, user.getUsername()))));
            connection.sendData(new Data(CLOSE_CONNECTION));
            connection.close();
            connectionService.patch(user.getUsername(), connection);
        } else {
            connection.sendData(new Data(CLOSE_CONNECTION));
            connection.close();
        }
    }

    private void executeWriteCommand(Map<String, String> body) {
        String userName = body.get(USER_NAME);
        User recipient = userService.findByUserName(userName);
        recipient.getConnection().sendData(new Data(Command.MESSAGE, Map.of(
                SENDER, user.getUsername(),
                MESSAGE, body.get(MESSAGE))));
    }

    private void executeSendAllCommand(Map<String, String> body) {
        connectionService.getAllExistExcept(connection)
                .forEach(recipientConnection ->
                        recipientConnection.sendData(
                                new Data(Command.MESSAGE, Map.of(
                                        SENDER, user.getUsername(),
                                        MESSAGE, body.get(MESSAGE)))));
    }
}
