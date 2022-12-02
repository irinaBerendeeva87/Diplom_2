package org.example.user;

public class Credentials {
    private String login;
    private String password;

    public static Credentials from(User user) {
        return new Credentials(user.getLogin(), user.getPassword());
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Credentials(String login, String password) {
        this.login = login;
        this.password = password;
    }


}
