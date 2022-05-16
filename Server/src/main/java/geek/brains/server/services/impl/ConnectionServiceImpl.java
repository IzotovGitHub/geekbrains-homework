package geek.brains.server.services.impl;

import geek.brains.server.connections.Connection;
import geek.brains.server.entities.User;
import geek.brains.server.services.ConnectionService;
import geek.brains.server.services.UserService;

import java.util.Set;

import static geek.brains.server.constt.ConnectionStatus.CONNECTION;
import static geek.brains.server.entities.types.UserType.EMPTY_USER;
import static java.util.Collections.emptySet;
import static java.util.stream.Collectors.toSet;

public class ConnectionServiceImpl implements ConnectionService {

    private static ConnectionService connectionService;

    private ConnectionServiceImpl() {
    }

    private final UserService userService = UserServiceImpl.getInstance();

    @Override
    public Set<Connection> getAllExistExcept(Connection connection) {
        Set<Connection> connections = userService.getAllUsers()
                .stream()
                .map(User::getConnection)
                .filter(c -> CONNECTION.equals(c.getStatus()))
                .collect(toSet());

        if (connections.remove(connection)) {
            return connections;
        }
        return emptySet();
    }

    @Override
    public Connection patch(String userName, Connection connection) {
        User oldUser = userService.findByUserName(userName);
        if (!EMPTY_USER.getUser().equals(oldUser)) {
            User newUser = new User(oldUser.getUsername(),
                    oldUser.getLogin(),
                    oldUser.getPassword(),
                    connection);
            userService.update(oldUser, newUser);
        }
        return connection;
    }

    public static ConnectionService getInstance() {
        if (connectionService == null) {
            connectionService = new ConnectionServiceImpl();
        }
        return connectionService;
    }
}
