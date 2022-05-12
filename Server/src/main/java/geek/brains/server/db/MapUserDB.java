package geek.brains.server.db;

import geek.brains.server.entities.User;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import static geek.brains.server.entities.types.UserType.EMPTY_USER;
import static java.util.Optional.ofNullable;
import static java.util.stream.Collectors.toMap;

public class MapUserDB {

    private static MapUserDB instance;

    private static final ConcurrentHashMap<String, User> userDB = new ConcurrentHashMap<>();

    private MapUserDB() {
    }

    public synchronized Map<String, User> getAll() {
        return userDB.entrySet().stream()
                .map(entry -> Map.entry(entry.getKey(), entry.getValue().copy()))
                .collect(toMap(Map.Entry::getKey, Map.Entry::getValue));
    }

    public synchronized User getByKey(String key) {
        return ofNullable(userDB.get(key))
                .map(User::copy)
                .orElse(EMPTY_USER.getUser());
    }

    public synchronized User putUser(User user) {
        if (isValidUser(user)) {
            return userDB.putIfAbsent(user.getUsername(), user.copy());
        }
        return user;
    }

    public synchronized static MapUserDB getInstance() {
        if (instance == null) {
            instance = new MapUserDB();
        }
        return instance;
    }

    private synchronized boolean isValidUser(User user) {
        return user != null
                && user.getLogin() != null
                && user.getPassword() != null
                && user.getUsername() != null;
    }

}
