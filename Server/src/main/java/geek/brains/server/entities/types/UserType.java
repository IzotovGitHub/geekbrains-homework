package geek.brains.server.entities.types;

import geek.brains.server.entities.User;

public enum UserType {

    EMPTY_USER(new User());

    private User user;

    UserType(User user) {
        this.user = user;
    }

    public User getUser() {
        return user;
    }
}
