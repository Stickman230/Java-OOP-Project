package socialmedia;

/**
 * Comment is a childclass of Post used to generate comments 
 * with implemented methods and variables 
 *
 * @author Jeremy & Maxime
 * @version 1.0
 */


public class Comment extends Post {
    
    /** Creates two reference variables to obtain the original Post
        And in the case of a comment on a comment get the previous one  **/
    
    Post originalPost;
    Post pointer;

    public Comment(User author, String message, Post previous, SocialMedia socialmedia) {
        
        /** 
        Creates the constructor for the Comment class and adds to the TotalNumberOfComments counter 
        
        @param author the author of the comment
        @param message the message of the comment
        @param previous the previous post or comment you are commenting on
        @param socialmedia**/

        super(author, message, socialmedia);
        this.pointer = previous;
        this.message = message;
        previous.postComments.add(this);
        this.originalPost.totalNumberOfComments++;
        this.pointer.totalNumberOfComments++;
    }

    public void deleteComment() {
        
        /** Method to use when deleting a comment **/
        this.author.userComments.remove(this);
        this.originalPost.postComments.remove(this);
        this.originalPost.totalNumberOfComments--;
    }

    public Post getOriginalPost() {
        
        /** Method to get the original commented post **/
        return this.originalPost;
    }
}
