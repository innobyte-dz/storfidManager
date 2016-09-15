package wolfsoft.invincible.model;

/**
 * Created by lamine on 5/1/2015.
 */

import android.annotation.SuppressLint;

import java.io.Serializable;

@SuppressLint("UseValueOf")
@SuppressWarnings("serial")
public class UserContext implements Serializable {

    private int userId;
    private String firstName;
    private String lastName;
    private String email;
    private String phone;
    private String sessionId;
    private String refreshToken;
    private String userPermissions;
    private String action;

    public UserContext() {

    }

    public UserContext(int id, String firstName, String lastName, String email,
                       String phone, String sessionId, String refreshToken) {
        this.userId = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.phone = phone;
        this.sessionId = sessionId;
    }

    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public String getRefreshToken() {
        return refreshToken;
    }

    public void setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }

    public String getUserPermissions() {
        return userPermissions;
    }

    public void setUserPermissions(String userPermissions) {
        this.userPermissions = userPermissions;
    }
}