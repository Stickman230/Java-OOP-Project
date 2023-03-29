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
      Creates the constructor for the Comment class and adds to the TotalNumberOfComments counter 
        
      @param author the author of the comment
      @param message the message of the comment
      @param previous the previous post or comment you are commenting on
      @param socialmedia the socialmedia object**/
    public Comment(User author, String message, Post previous, SocialMedia socialmedia) {
        super(author, message, socialmedia);
        this.pointer = previous;
        this.message = message;
        previous.postComments.add(this);
        this.originalPost.totalNumberOfComments++;
        this.pointer.totalNumberOfComments++;
    }
    /** Method to use when trying to delete a comment **/
    public void deleteComment() {
        this.author.userComments.remove(this);
        this.originalPost.postComments.remove(this);
        this.originalPost.totalNumberOfComments--;
    }
    /** Method to get original post commenting on 
        @return the original post **/
    public Post getOriginalPost() {
        return this.originalPost;
    }
}
