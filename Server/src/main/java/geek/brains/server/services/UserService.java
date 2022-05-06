package geek.brains.server.services;



public class UserService {

    private static UserService userService;

    private UserService(){}

    public static UserService getInstance(){
        if(userService == null){
            userService = new UserService();
        }
        return  userService;
    }
}
