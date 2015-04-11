package blue.jimma.backend;

/**
 * Created by gji on 4/11/15.
 */
public class User {
    int karma;
    String username;

    public User(String username) {
        this.username = username;
        karma = 0;
    }

    public String getUsername() {
        return this.username;
    }
}
