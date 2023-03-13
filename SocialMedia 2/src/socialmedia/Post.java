package socialmedia;

import java.util.ArrayList;

public class Post {
    int id;
    String author;
    String message;

    // TODO change the types of the list to endorsement and post.
    ArrayList<Post> postEndorsments = new ArrayList<>();
    ArrayList<Comment> postComments = new ArrayList<>();

    public int getId() {
        return this.id;
    }

    public Post(String author, String message) {
        this.author = author;
        this.message = message;
    }
}
