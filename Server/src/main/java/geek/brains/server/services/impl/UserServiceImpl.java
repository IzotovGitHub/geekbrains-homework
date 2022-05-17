package geek.brains.server.services.impl;

import geek.brains.server.db.MapUserDB;
import geek.brains.server.entities.User;
import geek.brains.server.services.UserService;

import java.util.HashSet;
import java.util.Set;

import static geek.brains.server.entities.types.UserType.EMPTY_USER;

public class UserServiceImpl implements UserService {

    private static UserService userService;

    private static final MapUserDB userDB = MapUserDB.getInstance();

    private UserServiceImpl() {
    }

    @Override
    public User findByLogin(String login) {
        return userDB.getAll().values().stream()
                .filter(user -> user.getLogin().equals(login))
                .findFirst()
                .orElse(EMPTY_USER.getUser());
    }

    @Override
    public User findByUserName(String userName) {
        return userDB.getByKey(userName);
    }

    @Override
    public void patch(User user) {
        userDB.putUser(user);
    }

    @Override
    public boolean update(User oldUser, User newUser) {
        return userDB.update(oldUser.getUsername(), newUser);
    }

    @Override
    public Set<User> getAllUsers() {
        return new HashSet<>(userDB.getAll().values());
    }

    public static UserService getInstance() {
        if (userService == null) {
            userService = new UserServiceImpl();
        }
        return userService;
    }
}
