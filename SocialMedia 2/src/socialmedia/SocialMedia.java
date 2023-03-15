package socialmedia;

import java.io.IOException;
import java.util.ArrayList;

/**
 * SocialMedia is a minimally compiling, but non-functioning implementor of
 * the SocialMediaPlatform interface.
 * 
 * @author Diogo Pachec
 * @version 1.0
 */
public class SocialMedia implements SocialMediaPlatform {
    ArrayList<User> allUsers = new ArrayList<>();
    static int userCount = 0;

    ArrayList<Post> allPosts = new ArrayList<>();
    int postCount = 0;

    @Override
    public int createAccount(String handle) throws IllegalHandleException, InvalidHandleException {
        // TODO Auto-generated method stub
        User newAcc;
        for (User user : allUsers) {
            if (user.getHandle() == handle) {
                throw new IllegalHandleException("Account handle already in use");
            }
        }
        if (handle == "") {
            throw new InvalidHandleException("The handle you provided is empty");
        }
        for (int i = 0; i < handle.length(); i++) {
            if (handle.charAt(i) == ' ' || handle.length() > 100) {
                throw new InvalidHandleException("The handle you provided is incorect");
            }
        }
        newAcc = new User(handle);
        allUsers.add(newAcc);
        userCount++;
        return newAcc.getId();
    }

    @Override
    public int createAccount(String handle, String description) throws IllegalHandleException, InvalidHandleException {
        // TODO Store the list of users in the social media class.
        User newAcc;
        for (User user : allUsers) {
            if (user.getHandle() == handle) {
                throw new IllegalHandleException("Account handle already in use");
            }
        }
        if (handle == "") {
            throw new InvalidHandleException("The handle you provided is empty");
        }
        for (int i = 0; i < handle.length(); i++) {
            if (handle.charAt(i) == ' ' || handle.length() > 100) {
                throw new InvalidHandleException("The handle you provided is incorect");
            }
        }
        newAcc = new User(handle, description);
        allUsers.add(newAcc);
        userCount++;
        return newAcc.getId();
    }

    @Override
    public void removeAccount(int id) throws AccountIDNotRecognisedException {
        // TODO Auto-generated method stub
        for (User user : allUsers) {
            if (user.getId() == id) {
                user.removeAccount();
            }
        }
        throw new AccountIDNotRecognisedException("Account Id does not exist in the system");
    }

    @Override
    public void removeAccount(String handle) throws HandleNotRecognisedException {
        // TODO Auto-generated method stub
        for (User user : allUsers) {
            if (user.getHandle() == handle) {
                this.allUsers.remove(user);
                for (Post post : user.userPosts) {
                    try {
                        this.deletePost(post.getId());
                    } catch (PostIDNotRecognisedException e) {
                        System.out.println("Error when deletinh posts associated to account");
                    }
                }
                for (Comment comm : user.userComments) {
                    // TODO delete comment (also remove it from the post's records)

                }

                // TODO Iterate through list of endorsements.
            }
            throw new HandleNotRecognisedException("Handle does not exist in the system");
        }
    }

    @Override
    public void changeAccountHandle(String oldHandle, String newHandle)
            throws HandleNotRecognisedException, IllegalHandleException, InvalidHandleException {
        if (newHandle == "") {
            throw new InvalidHandleException("The new handle you provided is empty");
        }
        for (int i = 0; i < newHandle.length(); i++) {
            if (newHandle.charAt(i) == ' ' || newHandle.length() > 100) {
                throw new InvalidHandleException("The new handle you provided is incorect");
            }
        }
        for (User user : allUsers) {
            if (oldHandle == user.getHandle()) {
                user.handle = newHandle;
            } else if (newHandle == user.getHandle()) {
                throw new IllegalHandleException("Account handle already in the system");
            }
        }
        throw new HandleNotRecognisedException("Handle does not exist in the system");
    }

    @Override
    public void updateAccountDescription(String handle, String description) throws HandleNotRecognisedException {
        for (User user : allUsers) {
            if (user.getHandle() == handle) {
                user.description = description;
            }
        }
        throw new HandleNotRecognisedException("Handle does not exist in the system");
    }

    @Override
    public String showAccount(String handle) throws HandleNotRecognisedException {
        String output = "";
        for (User user : allUsers) {
            if (user.getHandle() == handle) {
                output = "ID: " + user.getId() + "\nHandle: "
                        + user.getHandle() + "\nDescription: " + user.getDescription()
                        + "\nPost count: " + user.getNumberOfInteractions() + "\nEndorse count: "
                        + user.getReceivedEndorsment();
                break;
            }
        }
        if (output != "") {
            return output;
        } else {
            throw new HandleNotRecognisedException("Handle does not exist in the system");
        }

    }

    @Override
    public int createPost(String handle, String message) throws HandleNotRecognisedException, InvalidPostException {
        if (message == "" || message.length() > 100) {
            throw new InvalidPostException("The messsage you tried to put is not allowed");
        }
        for (User user : allUsers) {
            if (user.getHandle() == handle) {
                Post newPost = new Post(handle, message);
                allPosts.add(newPost);
                newPost.id = postCount++;
                user.userPosts.add(newPost);
                return newPost.id;
            }
        }
        throw new HandleNotRecognisedException("Handle does not exist in the system");
    }

    @Override
    public int endorsePost(String handle, int id)
            throws HandleNotRecognisedException, PostIDNotRecognisedException, NotActionablePostException {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public int commentPost(String handle, int id, String message) throws HandleNotRecognisedException,
            PostIDNotRecognisedException, NotActionablePostException, InvalidPostException {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public void deletePost(int id) throws PostIDNotRecognisedException {
        // TODO Auto-generated method stub
        for (Post post : allPosts) {
            if (post.getId() == id) {
                this.allPosts.remove(post);
                for (Comment comm : post.postComments) {
                    // post.postComments.remove(comm);

                    // comm.getAuthor().userComments.remove(comm);
                    comm.message = "The original content was removed from the system and is no longer available.";

                }
                // TODO ADD SAME FUNCTIONALITY FOR ENDORSEMENTS

                break;
            }
        }
        throw new PostIDNotRecognisedException();
    }

    @Override
    public String showIndividualPost(int id) throws PostIDNotRecognisedException {
        String output = "";
        for (Post post : allPosts) {
            if (post.getId() == id) {
                output = "ID : " + id + "\nAccount : "
                        + post.author.getHandle() + "\nNo of endorsements : " 
                        + post.getNumberOfEndorsments() + "\nNo of comments : "
                        + post.getNumberOfComments() + "\nMessage : " + post.getMessage(); 
                break;
            }
        }
        if (output != "") {
            return output;
        } else {
            throw new PostIDNotRecognisedException("Post Id does not exist in the system");
        }
    }

    @Override
    public StringBuilder showPostChildrenDetails(int id)
            throws PostIDNotRecognisedException, NotActionablePostException {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public int getNumberOfAccounts() {
        return allUsers.size();
    }

    @Override
    public int getTotalOriginalPosts() {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public int getTotalEndorsmentPosts() {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public int getTotalCommentPosts() {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public int getMostEndorsedPost() {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public int getMostEndorsedAccount() {
        User max = allUsers.get(0);
        int maxVal = max.getReceivedEndorsment();
        for (User user : allUsers) {
            if (user.getReceivedEndorsment() > maxVal) {
                max = user;
                maxVal = user.getReceivedEndorsment();
            }
        }
        return max.getId();
    }

    @Override
    public void erasePlatform() {
        // TODO Auto-generated method stub
    }

    @Override
    public void savePlatform(String filename) throws IOException {
        // TODO Auto-generated method stub

    }

    @Override
    public void loadPlatform(String filename) throws IOException, ClassNotFoundException {
        // TODO Auto-generated method stub

    }

    static public int getUserCount() {
        return userCount;
    }
}
