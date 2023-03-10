package socialmedia;

public class Comment extends Post {
    User author;
    int id;
    Post originalPost;
    String message;

    public User getAuthor() {
        return this.author;
    }

    public Comment(User author, String message, Post original) {
        super(author, message);
        this.originalPost = original;
        this.message = "Commenting on post #" + original.getId();
    }
}
