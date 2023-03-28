package socialmedia;

/**
 * Endorsment is a childclass of Post used to generate endorsments 
 * with implemented methods and variables. It is used to get an original post 
 * and create an endorsment post for this post
 *
 * @author Jeremy & Maxime
 * @version 1.0
 */

public class Endorsement extends Post {
    
    Post originalPost;
    /** Endorsment constuctor class gets the original post and 
    creates an endorsment post with message format
    
    @param author author of the endorsment
    @param original the original post being endorsed
    @param socialmedia **/
    public Endorsement(User author, Post original, SocialMedia socialmedia) {
        super(author, "Empty Endorsement", socialmedia);
        this.originalPost = original;
        this.message = "EP@" + author.getHandle() + ": " + original.getMessage();
    }
    /** Method to delete endorsments **/
    public void deleteEndorsement() {
        originalPost.postEndorsments.remove(this);
        this.author.userEndorsements.remove(this);
    }
}
