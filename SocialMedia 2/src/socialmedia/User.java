package socialmedia;

import java.util.ArrayList;

/**
 * User is a class used to make users, instances of this class are user accounts  
 *
 * @author Jeremy & Maxime
 * @version 1.0
 *
 **/

public class User {
    int id;
    String handle;
    ArrayList<Post> userPosts = new ArrayList<>();
    ArrayList<Comment> userComments = new ArrayList<>();
    ArrayList<Endorsement> userEndorsements = new ArrayList<>();
    String description;
    
    /** User constructor class that buids acounts with individual: 
        Id and Handle 
        @param handle a user chosen unique handle
        @param platform the social media object **/
    public User(String handle, SocialMedia platform) {
        this.id = platform.getUserCount();
        this.handle = handle;
    }

    /** User second constructor class, when the option to add a description is added 
        this is the constructor tha will be used
        @param handle a user chosen unique handle
        @param description a optional description 
        @param platform the social media object 
        @throws IllegalHandleException Thrown when attempting to assign an account handle empty or 
                having more than the system limit of characters **/
    public User(String handle, String description, SocialMedia platform) throws IllegalHandleException {
        this.id = platform.getUserCount();
        this.handle = handle;
        this.description = description;
    }

    /** A Method to get the id of a user
    @return the numerical id **/
    public int getId() {
        return this.id;
    }
    
    /**A Method to get the handle of a user
       @return Unnique user handle**/
    public String getHandle() {
        return this.handle;
    }

    /** A Method to get the account description
        @return account description**/
    public String getDescription() {
        return this.description;
    }
    
    /** A Method to get the number of created object a
        user has as post, endorsment and comments
        @retun added number Of post, endorsment and comments **/
    public int getNumberOfInteractions() {
        return userPosts.size() + userComments.size() + userEndorsements.size();
    }
    
    /**A Method to get the number of recieved endorsments
        @return endcount numerical revceivd endorsment value**/
    public int getReceivedEndorsment() {
        int endcount = 0;
        for (Post post : userPosts) {
            endcount += post.postEndorsments.size();
        }
        return endcount;
    }
}
