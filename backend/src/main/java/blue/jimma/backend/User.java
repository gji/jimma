package blue.jimma.backend;

/**
 * Created by gji on 4/11/15.
 */
public class User {
    static int userCount = 0;
    int id;
    int karma;
    String username;

    public User(String username) {
        id = userCount;
        userCount++;
        this.username = username;
    }

    public int getId() {return id; }
}
