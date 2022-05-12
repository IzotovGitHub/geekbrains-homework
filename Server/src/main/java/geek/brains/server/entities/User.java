package geek.brains.server.entities;

import geek.brains.server.connections.Connection;

import java.util.Objects;

public class User {
    private final String login;
    private final String password;
    private final String username;
    private final Connection connection;
    private String status;

    public User(String login, String password, String username, Connection connection) {
        this.login = login;
        this.password = password;
        this.username = username;
        this.connection = connection;
    }

    public User(String login, String password) {
        this(login, password, null, null);
    }

    public User() {
        this(null, null);
    }

    public String getLogin() {
        return login;
    }

    public String getPassword() {
        return password;
    }

    public String getUsername() {
        return username;
    }

    public Connection getConnection() {
        return connection;
    }


    public String getStatus() {
        return status;
    }

    public User copy() {
        return new User(this.login, this.password, this.username, this.connection);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(login, user.login)
                && Objects.equals(password, user.password)
                && Objects.equals(connection, user.connection)
                && Objects.equals(username, user.username);
    }

    @Override
    public int hashCode() {
        return Objects.hash(login, password, username, connection);
    }


}
