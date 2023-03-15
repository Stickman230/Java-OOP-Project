package socialmedia;

import java.util.ArrayList;

public class User {
    // Could use map which uses handle to access a User object in O(1) time.
    int id;
    String handle;
    ArrayList<Post> userPosts = new ArrayList<>();
    ArrayList<String> userComments = new ArrayList<>();
    ArrayList<String> userEndorsements = new ArrayList<>();
    String description;

    public int getId() {
        return this.id;
    }

    public String getHandle() {
        return this.handle;
    }

    public String getDescription(){
        return this.description;
    }

    public int getNumberOfInteractions(){
        return userPosts.size() + userComments.size() + userEndorsements.size();
    }

    public int getReceivedEndorsment(){
        int endcount = 0;
        for (Post post : userPosts) {
            endcount += post.postEndorsments.size();
        }
        return endcount;
    }

    public User(String handle, String description) throws IllegalHandleException {
        this.id = SocialMedia.getUserCount();
        this.handle = handle;
        this.description = description;
    }

    public User(String handle) {
        this.id = SocialMedia.getUserCount();
        this.handle = handle;
    }

    public void createPost(String content) {
        // POST CONSTRUCTOR WITH AUTHOR = THIS.HANDLE
    }
}
