package socialmedia;

import java.util.ArrayList;
import java.io.Serializable;

/**
 * Post is the parent class for endorsment and comment and
 * is used to create an actionable post on our social media platform. An
 * instance of this class is a post.
 *
 * @author Jeremy & Maxime
 * @version 1.0
 */
public class Post implements Serializable {
    int id;
    User author;
    String message;
    int totalNumberOfComments = 0;
    boolean isLast = true;

    ArrayList<Endorsement> postEndorsments = new ArrayList<>();
    ArrayList<Comment> postComments = new ArrayList<>();

    /**
     * The constructor class for Post to get a author,
     * a messsage and the socialmedia object*
     * 
     * @param author      the author of the post
     * @param message     the message inputed by the user for the post
     * @param socialmedia the socialmedia object
     **/
    public Post(User author, String message, SocialMedia socialmedia) {
        this.author = author;
        this.message = message;
        this.id = socialmedia.getPostCount();
        socialmedia.postCount++;
    }

    /**
     * Method to get the post Id
     * 
     * @return the numerical id
     **/
    public int getId() {
        return this.id;
    }

    /**
     * Method to get the numberof endorsments of the post
     * 
     * @return the number of endorsments
     **/
    public int getNumberOfEndorsments() {
        return this.postEndorsments.size();
    }

    /**
     * Method to get the number of comments of this post
     * 
     * @return the number of comments
     **/
    public int getNumberOfComments() {
        return this.totalNumberOfComments;
    }

    /**
     * Method to get the message inpute by the user
     * 
     * @return the message string
     **/
    public String getMessage() {
        return this.message;
    }

    /**
     * Method to get th author of the post
     * 
     * @return the author
     **/
    public User getAuthor() {
        return this.author;
    }
}
