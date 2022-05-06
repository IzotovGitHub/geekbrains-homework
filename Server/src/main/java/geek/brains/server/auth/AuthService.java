package geek.brains.server.auth;

public interface AuthService {

    String getNickByLoginPass(String login, String pass);
}
