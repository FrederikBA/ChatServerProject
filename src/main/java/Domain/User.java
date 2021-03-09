package Domain;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * CREATED BY Janus @ 2021-03-09 - 09:31
 **/
public class User {
    private String name;


    public User(String name) {
        this.name = name;
    }

    public User(){
    }

    public String getName() {
        return name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(name, user.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }

    @Override
    public String toString() {
        return name;
    }

}
