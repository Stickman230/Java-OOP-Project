package socialmedia;

/**
 * Comment is a childclass of Post used to generate comments
 * with implemented methods and variables
 *
 * @author Jeremy & Maxime
 * @version 1.0
 */

public class Comment extends Post {
    Post originalPost;
    Post pointer;

    /**
     * Constructor method for the Comment class, adds to the TotalNumberOfComments
     * counter
     * 
     * @param author      the author of the comment
     * @param message     the message of the comment
     * @param previous    the previous post or comment you are commenting on
     * @param socialmedia the socialmedia object
     **/
    public Comment(User author, String message, Post previous, SocialMedia socialmedia) {
        super(author, message, socialmedia);
        this.pointer = previous;
        this.message = message;
        this.pointer.isLast = false;
        this.author.userComments.add(this);
        socialmedia.allComments.add(this);
        previous.postComments.add(this);
    }

    /** Method to use when trying to delete a comment **/
    public void deleteComment() {
        if (this.originalPost == null && this.pointer == null) {
            this.author.userComments.remove(this);
        } else if (this.originalPost == null) {
            this.author.userComments.remove(this);
            this.pointer.totalNumberOfComments--;
        } else {
            this.author.userComments.remove(this);
            this.originalPost.postComments.remove(this);
            this.originalPost.totalNumberOfComments--;
            this.pointer.totalNumberOfComments--;
        }
        return;
    }

    /**
     * Method to get original post commenting on
     * 
     * @return the original post object
     **/
    public Post getOriginalPost() {
        return this.originalPost;
    }
}
