package com.trade_analysis.model;

import com.trade_analysis.dtos.UserSignUpDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import javax.persistence.*;
import java.util.List;
import java.util.UUID;

import static com.trade_analysis.model.UserRole.USUAL;

@Getter @Setter @AllArgsConstructor @NoArgsConstructor
@Entity
@Table(name = "user", schema = "public")
public class User {
    @Id
    @Column(name = "id", nullable = false, columnDefinition = "uuid")
    private UUID id = UUID.randomUUID();

    @Column(name = "username", nullable = false, columnDefinition = "varchar(30)")
    private String username;

    @Column(name = "email", nullable = false, columnDefinition = "varchar(90)")
    private String email;

    @Column(name = "password", nullable = false, columnDefinition = "text")
    private String password;

    @Enumerated(EnumType.STRING)
    @Column(name = "user_role", nullable = false, columnDefinition = "varchar(15)")
    private UserRole userRole;

    public static User valueOf(UserSignUpDto userSignUpDto) {
        String password = new BCryptPasswordEncoder(10).encode(userSignUpDto.getPassword1());

        return new User(UUID.randomUUID(),
                userSignUpDto.getUsername(),
                userSignUpDto.getEmail(),
                password,
                USUAL);
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

    @Override
    public String toString() {
        return new StringBuilder()
                .append("User{id='")
                .append(id.toString())
                .append("',username='")
                .append(username)
                .append("',email='")
                .append(email)
                .append("',password='")
                .append(password)
                .append("',userRole='")
                .append(userRole.name())
                .append("'}")
                .toString();
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