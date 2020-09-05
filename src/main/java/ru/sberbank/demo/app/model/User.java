package ru.sberbank.demo.app.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Entity
@Table(name = "users")
@EqualsAndHashCode
@JsonIgnoreProperties({"hibernateLazyInitializer"})
public class User implements IEntity {

    private final static int LOGIN_MIN_THRESHOLD = 5;
    private final static int LOGIN_MAX_THRESHOLD = 30;

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(
            name = "UUID",
            strategy = "org.hibernate.id.UUIDGenerator"
    )
    @Column(name = "id", updatable = false, nullable = false)
    @NotNull
    private UUID id;

    @Column(nullable = false)
    @Email
    @NotBlank(message = "User's email mustn't be blank")
    private String email;

    @Column(nullable = false)
    @NotBlank(message = "User's login mustn't be blank")
    @Size(min = LOGIN_MIN_THRESHOLD, max = LOGIN_MAX_THRESHOLD, message = "login must be in range from " + LOGIN_MIN_THRESHOLD + " to " + LOGIN_MAX_THRESHOLD)
    private String login;

    @Column(nullable = false)
    @NotBlank(message = "User's password mustn't be blank")
    private String password;

    @Column(nullable = false)
    @NotNull
    private LocalDateTime created;

    public User() {
        this.created = LocalDateTime.now();
    }

    public User(String email, String login, String password) {
        this.email = email;
        this.login = login;
        this.password = password;
        this.created = LocalDateTime.now();
    }

}
