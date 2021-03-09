package Service;

import Domain.User;

import java.util.ArrayList;
import java.util.List;

/**
 * CREATED BY Janus @ 2021-03-09 - 09:36
 **/
public class UserService {
    List<User> users;
    User user1 = new User("Janus");
    User user2 = new User("Gustav");
    User user3 = new User("Frederik");



    public UserService() {
        users = new ArrayList<>();
        addUserToList(user1);
        addUserToList(user2);
        addUserToList(user3);
    }


    public void addUserToList(User user){
        users.add(user);

    }

    public List<User> getUsers() {
        return users;
    }
}
