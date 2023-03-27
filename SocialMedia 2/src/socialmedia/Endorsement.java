package socialmedia;

public class Endorsement extends Post {
    Post originalPost;

    public Endorsement(User author, Post original, SocialMedia socialmedia) {
        super(author, "Empty Endorsement", socialmedia);
        this.originalPost = original;
        this.message = "EP@" + author.getHandle() + ": " + original.getMessage();
    }

    public void deleteEndorsement() {
        originalPost.postEndorsments.remove(this);
        this.author.userEndorsements.remove(this);
    }
}
