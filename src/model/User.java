package model;

import exception.ValidationException;
import util.IdGenerator;

import java.util.Comparator;

public class User {
    public static final Comparator<User> BY_ID = Comparator.comparing(User::getUserId);

    private final Long userId;
    private String name;
    private String email;
    private UserRole role;

    public User(String name, String email, UserRole role) {
        setName(name);
        setEmail(email);
        this.role = role; // TODO: fix
        this.userId = IdGenerator.getIdForClass(User.class);
    }

    public long getUserId() {
        return userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        if (name == null || name.isBlank()) {
            throw new ValidationException();
        }
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        if (email == null || email.isBlank()) {
            throw new ValidationException();
        }
        this.email = email;
    }

    public UserRole getRole() {
        return role;
    }

    public void setRole(UserRole role) {
        this.role = role;
    }
}
