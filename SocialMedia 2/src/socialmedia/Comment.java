package socialmedia;

public class Comment extends Post {
    User author;
    int id;
    Post originalPost;
    String message;

    public Comment(User author, String message, Post original, SocialMedia socialmedia) {
        super(author, message, socialmedia);
        this.originalPost = original;
        this.message = "Commenting on post #" + original.getId() + "\n\"" + original.getMessage() + "\" \n Comment:\n"
                + message;
    }

    public void deleteComment() {
        this.author.userComments.remove(this);
        this.originalPost.postComments.remove(this);
    }
}
