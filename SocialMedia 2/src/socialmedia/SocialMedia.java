package socialmedia;

import java.io.*;
import java.util.ArrayList;
import java.util.Iterator;

/**
 * SocialMedia is a compiling and fully functioning implementor of
 * the SocialMediaPlatform interface.
 * 
 * @author Jeremy Shorter, Maxime Reynaud
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
        for (int i = 0; i < allUsers.size(); i++) {
            User user = allUsers.get(i);
            if (user.getId() == id) {
                for (int y = 0; y < user.userPosts.size(); y++) {
                    Post post = user.userPosts.get(y);

                    try {
                        this.deletePost(post.getId());
                    } catch (PostIDNotRecognisedException e) {
                        System.out.println("Error when deleting posts associated to account");
                        continue;
                    }
                }

                for (int y = 0; y < user.userComments.size(); y++) {
                    Comment comm = user.userComments.get(y);
                    comm.deleteComment();
                    user.userComments.remove(comm);
                    y--;
                }

                for (int y = 0; y < user.userEndorsements.size(); y++) {
                    Endorsement endor = user.userEndorsements.get(y);
                    endor.deleteEndorsement();
                }

                this.allUsers.remove(user);
                return;
            }
        }
        throw new AccountIDNotRecognisedException("Account Id does not exist in the system");
    }

    @Override
    public void removeAccount(String handle) throws HandleNotRecognisedException {
        for (int i = 0; i < allUsers.size(); i++) {
            User user = allUsers.get(i);
            if (user.getHandle() == handle) {
                for (int y = 0; y < user.userPosts.size(); y++) {
                    Post post = user.userPosts.get(y);
                    try {
                        this.deletePost(post.getId());
                    } catch (PostIDNotRecognisedException e) {
                        System.out.println("Error when deleting posts associated to account");
                        continue;
                    }
                }

                for (int y = 0; y < user.userComments.size(); y++) {
                    Comment comm = user.userComments.get(y);
                    comm.deleteComment();
                    user.userComments.remove(comm);
                    y--;
                }

                for (int y = 0; y < user.userEndorsements.size(); y++) {
                    Endorsement endor = user.userEndorsements.get(y);
                    endor.deleteEndorsement();
                }

                this.allUsers.remove(user);
                return;
            }
        }
        throw new HandleNotRecognisedException();
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
            if (newHandle.equals(user.getHandle())) {
                throw new IllegalHandleException("New account handle already in the system");
            }
        }
        for (User user : allUsers) {
            if (oldHandle.equals(user.getHandle())) {
                user.handle = newHandle;
                return;
            }
        }
        throw new HandleNotRecognisedException("Handle does not exist in the system");
    }

    @Override
    public void updateAccountDescription(String handle, String description) throws HandleNotRecognisedException {
        for (User user : allUsers) {
            if (user.getHandle().equals(handle)) {
                user.description = description;
                return;
            }
        }
        throw new HandleNotRecognisedException("Handle does not exist in the system");
    }

    @Override
    public String showAccount(String handle) throws HandleNotRecognisedException {
        String output = "";
        for (User user : allUsers) {
            if (user.getHandle().equals(handle)) {
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
            throw new InvalidPostException("The messsage you tried to post is not allowed");
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
                throw new NotActionablePostException("The id you want to use is of an unactionable post");
            }
        }
        for (Endorsement endorsement : allEndorsements) {
            if (endorsement.getId() == id) {
                throw new NotActionablePostException("The id you want to use is of an unactionable post");
            }
        }
        throw new PostIDNotRecognisedException("The Post Id des nt exist in the system");

    }

    @Override
    public int commentPost(String handle, int id, String message) throws HandleNotRecognisedException,
            PostIDNotRecognisedException, NotActionablePostException, InvalidPostException {
        if (message.length() > 100 || message == "") {
            throw new InvalidPostException("The message you inputed is not valid");
        }
        for (Post post : allPosts) {
            if (post.getId() == id) {
                for (User user : allUsers) {
                    if (user.getHandle() == handle) {
                        User author = user;
                        Comment newComment = new Comment(author, message, post, this);
                        newComment.originalPost = post;
                        newComment.originalPost.totalNumberOfComments++;
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
                        newComment.originalPost.totalNumberOfComments++;
                        newComment.pointer.totalNumberOfComments++;
                        return newComment.getId();
                    }
                }
                throw new HandleNotRecognisedException("The handle does not exist in the system");
            }
        }

        for (Endorsement endorsement : allEndorsements) {
            if (endorsement.getId() == id) {
                throw new NotActionablePostException("The id you want to use is of an unactionable post");
            }
        }
        throw new PostIDNotRecognisedException("The Post Id des not exist in the system");

    }

    @Override
    public void deletePost(int id) throws PostIDNotRecognisedException {
        Iterator<Post> postIterator = allPosts.iterator();
        while (postIterator.hasNext()) {
            Post post = postIterator.next();
            if (post.getId() == id) {

                try {
                    postIterator.remove();
                    post.author.userPosts.remove(post);
                } catch (Exception e) {
                    System.out.println("Exception caught: Post not found in System files.");
                }
                for (Comment comm : post.postComments) {
                    comm.message = "The original content was removed from the system and is no longer available.";
                    comm.originalPost = null;
                }
                Iterator<Endorsement> endorIterator = post.postEndorsments.iterator();
                while (endorIterator.hasNext()) {
                    Endorsement endor = endorIterator.next();
                    endorIterator.remove();
                    endor.deleteEndorsement();
                }
                post.postComments.clear();
                return;
            }
        }
        throw new PostIDNotRecognisedException();
    }

    @Override
    public String showIndividualPost(int id) throws PostIDNotRecognisedException {
        String output = "";
        for (Post post : allPosts) {
            if (post.getId() == id) {
                output = "\nID: " + id + "\nAccount: "
                        + post.author.getHandle() + "\nNo. endorsements: "
                        + post.getNumberOfEndorsments() + " | No.comments: "
                        + post.getNumberOfComments() + "\n " + post.getMessage() + "\n";
                break;
            }
        }
        for (Comment comm : allComments) {
            if (comm.getId() == id) {
                output = "\nID: " + id + "\nAccount: "
                        + comm.author.getHandle() + "\nNo. endorsements: "
                        + comm.getNumberOfEndorsments() + " | No.comments: "
                        + comm.getNumberOfComments() + "\n " + comm.getMessage() + "\n";
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
                showPost(post, -1, sb);
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

    /**
     * Recursive method to be used by showPostChildrenDetails to print tree like
     * comment structures while respecting indentation.
     * 
     * @param post  the originlal post
     * @param depth the number of comments between a comment and the original post
     * @param sb    the stringBuilder used in showPostChildrenDetails
     **/
    public void showPost(Post post, int depth, StringBuilder sb) {
        String out = "";
        depth++;
        String indent = "";
        for (int i = 0; i < depth; i++) {
            indent += "\t";
        }

        if (depth > 0) {
            out = "\t" + "ID: " + post.getId() + "\n" + indent + "Account: "
                    + post.author.getHandle() + "\n" + indent + "No. endorsements: "
                    + post.getNumberOfEndorsments() + " | No.comments: "
                    + post.getNumberOfComments() + "\n" + indent + post.getMessage() + "\n";

        } else {
            out = "\nID: " + post.getId() + "\n" + indent + "Account: "
                    + post.author.getHandle() + "\n" + indent + "No. endorsements: "
                    + post.getNumberOfEndorsments() + " | No.comments: "
                    + post.getNumberOfComments() + "\n" + indent + post.getMessage() + "\n";
        }
        if (!post.isLast) {
            out += indent + "|" + "\n" + indent + "| >";
        }
        sb.append(out);
        for (Comment comment : post.postComments) {
            showPost(comment, depth, sb);
        }
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
        ObjectOutputStream oos = null;
        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(filename);
            oos = new ObjectOutputStream(fos);
            oos.writeObject(allUsers);
            oos.writeObject(allPosts);
            oos.writeObject(allComments);
            oos.writeObject(allEndorsements);
            oos.writeObject(userCount);
            oos.writeObject(postCount);
        } catch (IOException e) {
            System.out.println("IOException thrown while trying to deserialise platform");
        } finally {
            if (oos != null) {
                oos.close();
            }
            if (fos != null) {
                fos.close();
            }
        }
    }

    @Override
    @SuppressWarnings("unchecked")
    public void loadPlatform(String filename) throws IOException, ClassNotFoundException {

        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(filename))) {
            try {
                this.allUsers = (ArrayList<User>) in.readObject();
                this.allPosts = (ArrayList<Post>) in.readObject();
                this.allComments = (ArrayList<Comment>) in.readObject();
                this.allEndorsements = (ArrayList<Endorsement>) in.readObject();
            } catch (ClassNotFoundException e) {
                System.out.println("Class not found during deserialisation");
            }

            this.userCount = (int) in.readObject();
            this.postCount = (int) in.readObject();
        } catch (IOException e) {
            System.out.println("IO Exception thrown while attempting to deserialise.");
        }
    }

    /**
     * Method used to get the next available unique User id on the platform
     * 
     * @return next available id
     **/
    public int getUserCount() {
        return this.userCount;
    }

    /**
     * Method used to get the next available unique Post id on the platform
     * 
     * @return the next available id
     **/
    public int getPostCount() {
        return this.postCount;
    }
}
