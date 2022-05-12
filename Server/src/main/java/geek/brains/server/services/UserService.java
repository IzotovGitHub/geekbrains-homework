package geek.brains.server.services;


import geek.brains.server.entities.User;

import java.util.Set;

public interface UserService {

    User findByLogin(String login);

    User findByUserName(String userName);

    void patch(User user);

    Set<User> getAllUsers();
}
