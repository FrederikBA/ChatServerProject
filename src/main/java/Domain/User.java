package Domain;

import java.util.ArrayList;
import java.util.List;

/**
 * CREATED BY Janus @ 2021-03-09 - 09:31
 **/
public class User {
    private String name;


    public User(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return name;
    }

}
