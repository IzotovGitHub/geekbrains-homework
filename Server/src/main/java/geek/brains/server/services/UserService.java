package geek.brains.server.services;


import geek.brains.server.entities.User;

import java.util.Set;

public interface UserService {

    User findByLogin(String login);

    User findByUserName(String userName);

    void patch(User user);

    boolean update(User oldUser, User newUser);

    Set<User> getAllUsers();
}
