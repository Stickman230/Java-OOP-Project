package socialmedia;

import java.io.IOException;
import java.util.ArrayList;

/**
 * BadSocialMedia is a minimally compiling, but non-functioning implementor of
 * the SocialMediaPlatform interface.
 * 
 * @author Diogo Pachec
 * @version 1.0
 */
public class BadSocialMedia implements SocialMediaPlatform {
	ArrayList<User> allUsers = new ArrayList<>();
	static int userCount = 0;

	ArrayList<Post> allPosts = new ArrayList<>();
	int postCount = 0;

	@Override
	public int createAccount(String handle) throws IllegalHandleException, InvalidHandleException {
		// TODO Auto-generated method stub
		User newAcc;
		newAcc = new User(handle);
		allUsers.add(newAcc);
		userCount++;
		return newAcc.getId();
	}

	@Override
	public int createAccount(String handle, String description) throws IllegalHandleException, InvalidHandleException {
		// TODO Store the list of users in the social media class.
		User newAcc;
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
	}

	@Override
	public void removeAccount(String handle) throws HandleNotRecognisedException {
		// TODO Auto-generated method stub
		for (User user : allUsers) {
			if (user.getHandle() == handle) {
				user.removeAccount();
			}
		}

	}

	@Override
	public void changeAccountHandle(String oldHandle, String newHandle)
			throws HandleNotRecognisedException, IllegalHandleException, InvalidHandleException {
		for (User user : allUsers) {
			if (oldHandle == user.getHandle()) {
				user.handle = newHandle;
			}
		}

	}

	@Override
	public void updateAccountDescription(String handle, String description) throws HandleNotRecognisedException {
		for (User user : allUsers) {
			if (user.getHandle() == handle) {
				user.description = description;
			}
		}
	}

	@Override
	public String showAccount(String handle) throws HandleNotRecognisedException {
		// TODO ERROR HANDLING FOR UNRECOGNISED HANDLE
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

		return output;
	}

	@Override
	public int createPost(String handle, String message) throws HandleNotRecognisedException, InvalidPostException {
		// TODO Auto-generated method stub
		Boolean canPost = false;
		Post newPost = new Post(handle, message);
		int errType = 0;
		allPosts.add(newPost);
		newPost.id = postCount++;
		for (User user : allUsers) {
			if (handle == user.handle) {
				user.userPosts.add(newPost);
				canPost = true;
				break;
			}
		}
		if (message.length() == 0) {
			canPost = false;
			errType = 1;
		}
		if (canPost) {
			return newPost.id;
		} else {
			this.deletePost(newPost.getId());
			if (errType == 0) {
				throw new HandleNotRecognisedException("Provided handle does not exist.");
			} else {
				throw new InvalidPostException("Provided message is too long (100 characters max).");
			}
		}
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
					// REMOVE COMMENT FROM POST AND ALSO FROM USER'S COMMENT LIST
				}

				return;
			}
		}
		throw new PostIDNotRecognisedException();

	}

	@Override
	public String showIndividualPost(int id) throws PostIDNotRecognisedException {
		// TODO Auto-generated method stub
		return null;
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
