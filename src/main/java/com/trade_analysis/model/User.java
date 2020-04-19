package com.trade_analysis.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Type;
import org.springframework.security.core.GrantedAuthority;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;
import java.util.UUID;

@Getter @Setter @AllArgsConstructor @NoArgsConstructor
@Entity
@Table(name = "customers")
public class User {

    @Id
    @Type(type="uuid-binary")
    @GeneratedValue
    @Column(name = "ID", nullable = false, columnDefinition = "BINARY(64)")
    private UUID id;

    @Column(name = "USERNAME", nullable = false, columnDefinition = "TINYTEXT")
    private String username;

    @Column(name = "EMAIL", nullable = false, columnDefinition = "TINYTEXT")
    private String email;

    @Column(name = "PASSWORD", nullable = false, columnDefinition = "TEXT")
    private String password;

    @Enumerated(EnumType.STRING)
    @Column(name = "ROLE", nullable = false)
    private UserRole userRole;

    public List<GrantedAuthority> getGrantedAuthorities() {
        return List.of(getUserRole().getGrantedAuthority());
    }

    public String getLink() {
        return String.format(
                "<a href = '/user/%s' >%s</a>",
                this.getId().toString(),
                this.toString());
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", userRole=" + userRole +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if(this == o) return true;
        if(!(o instanceof User)) return false;

        User user = (User) o;

        if(getId() != null ? !getId().equals(user.getId()) : user.getId() != null) return false;
        if(getUsername() != null ? !getUsername().equals(user.getUsername()) : user.getUsername() != null) return false;
        if(getEmail() != null ? !getEmail().equals(user.getEmail()) : user.getEmail() != null) return false;
        if(getPassword() != null ? !getPassword().equals(user.getPassword()) : user.getPassword() != null) return false;
        return getUserRole() == user.getUserRole();
    }

    @Override
    public int hashCode() {
        int result = getId() != null ? getId().hashCode() : 0;
        result = 31 * result + (getUsername() != null ? getUsername().hashCode() : 0);
        result = 31 * result + (getEmail() != null ? getEmail().hashCode() : 0);
        result = 31 * result + (getPassword() != null ? getPassword().hashCode() : 0);
        result = 31 * result + (getUserRole() != null ? getUserRole().hashCode() : 0);
        return result;
    }
}