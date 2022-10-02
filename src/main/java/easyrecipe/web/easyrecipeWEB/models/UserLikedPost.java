package easyrecipe.web.easyrecipeWEB.models;

public class UserLikedPost {
    private String login;
    private String postId;

    public void setLogin(String login) {
        this.login = login;
    }

    public void setPostId(String postId) {
        this.postId = postId;
    }

    public String getLogin() {
        return login;
    }

    public String getPostId() {
        return postId;
    }

    public UserLikedPost(String login, String postId) {
        this.login = login;
        this.postId = postId;
    }
}
