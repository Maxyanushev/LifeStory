package com.example.nana.models;

public class UserModel {

    private String username, email, profilePic;

    public UserModel() {
    }

    public UserModel(String username, String email, String profilePic) {
        this.username = username;
        this.email = email;
        this.profilePic = profilePic;
    }

    public String getProfilePic() {
        return profilePic;
    }

    public void setProfilePic(String profilePic) {
        this.profilePic = profilePic;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
