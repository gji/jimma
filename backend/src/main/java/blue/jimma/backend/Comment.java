package blue.jimma.backend;

import java.awt.image.BufferedImage;

/**
 * Created by Mickey on 4/11/2015.
 */
public class Comment {

    String text;
    BufferedImage img;
    int votes;
    User author;
    Post post;

    public Comment(String text, BufferedImage pic, int votes, User author, Post post) {
        this.text = text;
        this.img = img;
        this.votes = votes;
        this.author = author;
        this.post = post;
    }

    public Comment(String text, BufferedImage pic, User author, Post post) {
        this(text, pic, 0, author, post);
    }

    public Comment(String text, User author, Post post) {
        this(text, null, 0, author, post);
    }



}
