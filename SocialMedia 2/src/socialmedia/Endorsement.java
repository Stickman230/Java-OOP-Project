package socialmedia;

/**
 * Endorsment is a childclass of Post used to generate endorsements
 * with implemented methods and variables. It is used to create an endorsment
 * post for a given original post. Instances of this class are endorsements.
 *
 * @author Jeremy & Maxime
 * @version 1.0
 */
public class Endorsement extends Post {
    Post originalPost;

    /**
     * Endorsment constuctor class gets the original post and
     * creates an endorsment post with message format
     * 
     * @param author      author of the endorsment
     * @param original    the original post being endorsed
     * @param socialmedia the socialmedia object
     **/
    public Endorsement(User author, Post original, SocialMedia socialmedia) {
        super(author, "Empty Endorsement", socialmedia);
        this.originalPost = original;
        this.message = "EP@" + author.getHandle() + ": " + original.getMessage();
        this.originalPost.postEndorsments.add(this);
        this.author.userEndorsements.add(this);
    }

    /** Method to delete endorsments **/
    public void deleteEndorsement() {
        originalPost.postEndorsments.remove(this);
        this.author.userEndorsements.remove(this);
    }
}
