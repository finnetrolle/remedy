package ru.trollsmedjan.remedy.dto.request;

/**
 * Created by finnetrolle on 31.07.2015.
 */
public class AuthDTO {

    private String username;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Override
    public String toString() {
        return "AuthDTO{" +
                "username='" + username + '\'' +
                '}';
    }
}
