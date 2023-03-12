package socialmedia;

import java.util.ArrayList;

public class User {
    // Could use map which uses handle to access a User object in O(1) time.
    int id;
    String handle;
    ArrayList<String> userPosts = new ArrayList<>();
    ArrayList<String> userComments = new ArrayList<>();
    ArrayList<String> userEndorsements = new ArrayList<>();
    String description;

    public int getId() {
        return this.id;
    }

    public String getHandle() {
        return this.handle;
    }

    public User(String handle, String description) throws IllegalHandleException {
        this.id = BadSocialMedia.getUserCount();
        this.handle = handle;
        this.description = description;
    }

    public User(String handle) {
        this.id = BadSocialMedia.getUserCount();
        this.handle = handle;
    }

    public void createPost(String content) {
        // POST CONSTRUCTOR WITH AUTHOR = THIS.HANDLE
    }

    public void removeAccount() {
        // TODO Iterate through the User's list of posts and delete all of them.
    }

}