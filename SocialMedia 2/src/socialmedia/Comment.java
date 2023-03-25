package socialmedia;

public class Comment extends Post {
    Post originalPost;
    Post pointer;

    public Comment(User author, String message, Post original, SocialMedia socialmedia) {
        super(author, message, socialmedia);
        this.pointer = original;
        this.message = message;
        original.postComments.add(this);
        this.originalPost.totalNumberOfComments++;
        this.pointer.totalNumberOfComments++;
    }

    public void deleteComment() {
        this.author.userComments.remove(this);
        this.originalPost.postComments.remove(this);
        this.originalPost.totalNumberOfComments--;
    }

    public Post getOriginalPost() {
        return this.originalPost;
    }
}
