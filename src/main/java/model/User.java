package model;

import javax.persistence.Entity;
import javax.persistence.Id;

/**
 * Created by ShubU on 3/13/2017.
 */

@Entity
public class User {
    private String username;
    private UserType type;
    private String email;
    private String password;
    @Id
    private int id;

    public int getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public UserType getType() {
        return type;
    }

    public void setType(UserType type) {
        this.type = type;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
