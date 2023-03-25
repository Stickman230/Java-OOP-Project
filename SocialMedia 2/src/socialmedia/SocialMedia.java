package socialmedia;

import java.io.*;
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
    int userCount = 0;

    ArrayList<Post> allPosts = new ArrayList<>();
    int postCount = 0;

    ArrayList<Comment> allComments = new ArrayList<>();
    ArrayList<Endorsement> allEndorsements = new ArrayList<>();

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
        newAcc = new User(handle, this);
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
        newAcc = new User(handle, description, this);
        allUsers.add(newAcc);
        userCount++;
        return newAcc.getId();
    }

    @Override
    public void removeAccount(int id) throws AccountIDNotRecognisedException {
        // TODO Auto-generated method stub
        for (User user : allUsers) {
            if (user.getId() == id) {
                this.allUsers.remove(user);
                for (Post post : user.userPosts) {
                    try {
                        this.deletePost(post.getId());
                    } catch (PostIDNotRecognisedException e) {
                        System.out.println("Error when deleting posts associated to account");
                    }
                }
                for (Comment comm : user.userComments) {
                    comm.deleteComment();
                }
                // TODO Iterate through list of endorsements.
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
                        System.out.println("Error when deleting posts associated to account");
                    }
                }
                for (Comment comm : user.userComments) {
                    comm.deleteComment();
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
                Post newPost = new Post(user, message, this);
                allPosts.add(newPost);
                user.userPosts.add(newPost);
                return newPost.id;
            }
        }
        throw new HandleNotRecognisedException("Handle does not exist in the system");
    }

    @Override
    public int endorsePost(String handle, int id)
            throws HandleNotRecognisedException, PostIDNotRecognisedException, NotActionablePostException {

        for (Post post : allPosts) {
            if (post.getId() == id) {
                for (User user : allUsers) {
                    if (user.getHandle() == handle) {
                        User author = user;
                        Endorsement newEndorsement = new Endorsement(author, post, this);
                        return newEndorsement.getId();
                    }

                }
                throw new HandleNotRecognisedException("The handle does not exist in the system");
            }
        }
        for (Comment comment : allComments) {
            if (comment.getId() == id) {
                throw new NotActionablePostException("The id you want to use is of a unactionable post");
            }
        }
        for (Endorsement endorsement : allEndorsements) {
            if (endorsement.getId() == id) {
                throw new NotActionablePostException("The id you want to use is of a unactionable post");
            }
        }
        throw new PostIDNotRecognisedException("The Post Id des nt exist in thne system");

    }

    @Override
    public int commentPost(String handle, int id, String message) throws HandleNotRecognisedException,
            PostIDNotRecognisedException, NotActionablePostException, InvalidPostException {
        // TODO Auto-generated method stub
        if (message.length() > 100 || message == "") {
            throw new InvalidPostException("The message you inputed is not valid");
        }
        for (Post post : allPosts) {
            if (post.getId() == id) {
                for (User user : allUsers) {
                    if (user.getHandle() == handle) {
                        User author = post.getAuthor();
                        Comment newComment = new Comment(author, message, post, this);
                        newComment.originalPost = post;
                        newComment.originalPost.totalNumberOfComments--;
                        return newComment.getId();
                    }
                }
                throw new HandleNotRecognisedException("The handle does not exist in the system");
            }
        }

        for (Comment comment : allComments) {
            if (comment.getId() == id) {
                for (User user : allUsers) {
                    if (user.getHandle() == handle) {
                        User author = comment.getAuthor();
                        Comment newComment = new Comment(author, message, comment, this);
                        newComment.originalPost = comment.getOriginalPost();
                        return newComment.getId();
                    }
                }
                throw new HandleNotRecognisedException("The handle does not exist in the system");
            }
        }

        for (Endorsement endorsement : allEndorsements) {
            if (endorsement.getId() == id) {
                throw new NotActionablePostException("The id you want to use is of a unactionable post");
            }
        }
        throw new PostIDNotRecognisedException("The Post Id des nt exist in thne system");

    }

    @Override
    public void deletePost(int id) throws PostIDNotRecognisedException {
        // TODO Auto-generated method stub
        for (Post post : allPosts) {
            if (post.getId() == id) {
                try {
                    this.allPosts.remove(post);
                    post.author.userPosts.remove(post);
                } catch (Exception e) {
                    System.out.println("Exception caught: Post not found in System files.");
                }
                for (Comment comm : post.postComments) {
                    comm.message = "The original content was removed from the system and is no longer available.";
                    comm.originalPost = null;
                }
                // TODO ADD SAME FUNCTIONALITY FOR ENDORSEMENTS

                post.postComments.clear();
                post.postEndorsments.clear();

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
                output = "ID: " + id + "\nAccount: "
                        + post.author.getHandle() + "\nNo. endorsements: "
                        + post.getNumberOfEndorsments() + " | No.comments: "
                        + post.getNumberOfComments() + "\n " + post.getMessage();
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
        StringBuilder sb = new StringBuilder();
        for (Post post : allPosts) {
            if (post.getId() == id) {
                sb.append(showIndividualPost(id)).append("\n");
                for (Post comment : allComments) {
                    // classer par ordre croissant
                    sb.append(" | > ").append(showIndividualPost(comment.getId())).append("\n");
                }
            }
        }
        for (Endorsement endorsement : allEndorsements) {
            if (endorsement.getId() == id) {
                throw new NotActionablePostException("The id you want to use is of a unactionable post");
            }
        }
        if (sb.length() == 0) {
            throw new PostIDNotRecognisedException("Post Id does not exist in the system");
        }
        return sb;
    }

    @Override
    public int getNumberOfAccounts() {
        return allUsers.size();
    }

    @Override
    public int getTotalOriginalPosts() {
        return allPosts.size();
    }

    @Override
    public int getTotalEndorsmentPosts() {
        return allEndorsements.size();
    }

    @Override
    public int getTotalCommentPosts() {
        return allComments.size();
    }

    @Override
    public int getMostEndorsedPost() {
        Post maxPost = allPosts.get(0);
        int currentMax = maxPost.getNumberOfEndorsments();
        for (Post post : allPosts) {
            if (post.getNumberOfEndorsments() > currentMax) {
                maxPost = post;
                currentMax = maxPost.getNumberOfEndorsments();
            }
        }
        return maxPost.getId();
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
        this.allComments.clear();
        this.allUsers.clear();
        this.allPosts.clear();
        this.allEndorsements.clear();

        this.postCount = 0;
        this.userCount = 0;
    }

    @Override
    public void savePlatform(String filename) throws IOException {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(filename))) {
            oos.writeObject(allUsers);
            oos.writeObject(allPosts);
            oos.writeObject(allComments);
            oos.writeObject(allEndorsements);
            oos.writeObject(userCount);
            oos.writeObject(postCount);
        }
    }

    @Override
    public void loadPlatform(String filename) throws IOException, ClassNotFoundException {

        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(filename))) {
            this.allUsers = (ArrayList<User>) in.readObject();
            this.allPosts = (ArrayList<Post>) in.readObject();
            this.allComments = (ArrayList<Comment>) in.readObject();
            this.allEndorsements = (ArrayList<Endorsement>) in.readObject();

            this.userCount = (int) in.readObject();
            this.postCount = (int) in.readObject();
        }
    }

    public int getUserCount() {
        return this.userCount;
    }

    public int getPostCount() {
        return this.postCount;
    }
}
