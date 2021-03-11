package Service;

import Domain.User;

import java.util.ArrayList;
import java.util.List;

public class UserService {
    List<User> users;
    User user1 = new User("Janus");
    User user2 = new User("Gustav");
    User user3 = new User("Frederik");
    User user4 = new User("Kurt");
    User user5 = new User("Lone");

    public UserService() {
        users = new ArrayList<>();
        addUserToList(user1);
        addUserToList(user2);
        addUserToList(user3);
        addUserToList(user4);
        addUserToList(user5);
    }

    public void addUserToList(User user) {
        users.add(user);
    }

    public List<User> getUsers() {
        return users;
    }

    public List<String> getUsernames() {
        List<String> stringList = new ArrayList<>();
        for (User u : getUsers()) {
            stringList.add(u.getName());
        }
        return stringList;
    }
}
