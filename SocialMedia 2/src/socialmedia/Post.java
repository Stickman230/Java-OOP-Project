package socialmedia;

import java.util.ArrayList;

public class Post {
    int id;
    User author;
    String message;
    static boolean canInteract = true;

    // TODO change the types of the list to endorsement and post.
    ArrayList<Post> postEndorsments = new ArrayList<>();
    ArrayList<Comment> postComments = new ArrayList<>();

    public int getId() {
        return this.id;
    }

    public int getNumberOfEndorsments() {
        return this.postEndorsments.size();
    }

    public int getNumberOfComments() {
        return this.postComments.size();
    }

    public Post(User author, String message, SocialMedia socialmedia) {
        this.author = author;
        this.message = message;
        this.id = socialmedia.getPostCount();
        socialmedia.postCount++;
    }

    public String getMessage() {
        return this.message;
    }

    public User getAuthor() {
        return this.author;
    }
}
