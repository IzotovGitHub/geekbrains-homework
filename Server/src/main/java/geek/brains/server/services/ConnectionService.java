package geek.brains.server.services;

import geek.brains.server.connections.Connection;

import java.util.Set;

public interface ConnectionService {

    Set<Connection> getAllExistExcept(Connection connection);

    Connection patch(String userName, Connection connection);
}
