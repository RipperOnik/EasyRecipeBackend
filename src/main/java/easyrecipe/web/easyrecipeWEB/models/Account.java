package easyrecipe.web.easyrecipeWEB.models;

public class Account {
    private String login;
    private String password;

    public String getLogin() {
        return login;
    }

    public String getPassword() {
        return password;
    }

    public Account(String login, String password) {
        this.login = login;
        this.password = password;
    }

    public Account(){}

    public void setLogin(String login) {
        this.login = login;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
