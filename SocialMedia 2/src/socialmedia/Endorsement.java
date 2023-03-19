package socialmedia;

public class Endorsement extends Post {

    User author;
    int id;
    String message;

    public Endorsement(User author, Post original, SocialMedia socialmedia) {
        super(author, "Empty Endorsement", socialmedia);
        this.message = "EP@" + author.getHandle() + ": " + original.getMessage();
    }
}
