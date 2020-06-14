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

    @Column(name = "api_key", columnDefinition = "varchar(20)")
    private String apiKey;

    public static User valueOf(UserSignUpDto userSignUpDto) {
        String password = new BCryptPasswordEncoder(10).encode(userSignUpDto.getPassword1());

        return new User(UUID.randomUUID(),
                userSignUpDto.getUsername(),
                userSignUpDto.getEmail(),
                password,
                USUAL,
                null);
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
                .append("',apiKey='")
                .append(apiKey == null ? "null" : apiKey)
                .append("'}")
                .toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof User)) return false;

        User user = (User) o;

        if (!id.equals(user.id)) return false;
        if (!username.equals(user.username)) return false;
        if (!email.equals(user.email)) return false;
        if (!password.equals(user.password)) return false;
        if (userRole != user.userRole) return false;
        return apiKey != null ? apiKey.equals(user.apiKey) : user.apiKey == null;
    }

    @Override
    public int hashCode() {
        int result = id.hashCode();
        result = 31 * result + username.hashCode();
        result = 31 * result + email.hashCode();
        result = 31 * result + password.hashCode();
        result = 31 * result + userRole.hashCode();
        result = 31 * result + (apiKey != null ? apiKey.hashCode() : 0);
        return result;
    }
}