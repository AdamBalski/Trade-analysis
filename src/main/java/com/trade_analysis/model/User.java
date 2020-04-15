package com.trade_analysis.model;

import org.hibernate.annotations.GenericGenerator;
import org.springframework.security.core.GrantedAuthority;

import javax.persistence.*;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "Users")
public class User {
    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(
            name = "UUID",
            strategy = "org.hibernate.id.UUIDGenerator"
    )
    @Column(name = "id")
    private UUID id;

    @Column(name = "username")
    private String username;

    @Column(name = "email")
    private String email;

    @Column(name = "password")
    private String password;

    @Column(name = "role")
    @Enumerated(EnumType.STRING)
    private UserRole userRole;

    public User() {
    }

    public User(UUID id, String username, String email, String password, UserRole userRole) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.password = password;
        this.userRole = userRole;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public UserRole getUserRole() {
        return userRole;
    }

    public void setUserRole(UserRole userRole) {
        this.userRole = userRole;
    }

    public List<GrantedAuthority> getGrantedAuthorities() {
        return List.of(getUserRole().getGrantedAuthority());
    }

    public String getLink() {
        return String.format(
                "<a href = '/user/%s' >%s</a>",
                this.getId().toString(),
                this.toString());
    }

    @Override public String toString() {
        return "User{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", userRole=" + userRole +
                '}';
    }

    @Override public boolean equals(Object o) {
        if(this == o) return true;
        if(!(o instanceof User)) return false;

        User user = (User) o;

        if(getId() != null ? !getId().equals(user.getId()) : user.getId() != null) return false;
        if(getUsername() != null ? !getUsername().equals(user.getUsername()) : user.getUsername() != null) return false;
        if(getEmail() != null ? !getEmail().equals(user.getEmail()) : user.getEmail() != null) return false;
        if(getPassword() != null ? !getPassword().equals(user.getPassword()) : user.getPassword() != null) return false;
        return getUserRole() == user.getUserRole();
    }

    @Override public int hashCode() {
        int result = getId() != null ? getId().hashCode() : 0;
        result = 31 * result + (getUsername() != null ? getUsername().hashCode() : 0);
        result = 31 * result + (getEmail() != null ? getEmail().hashCode() : 0);
        result = 31 * result + (getPassword() != null ? getPassword().hashCode() : 0);
        result = 31 * result + (getUserRole() != null ? getUserRole().hashCode() : 0);
        return result;
    }
}