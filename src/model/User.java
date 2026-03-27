package model;

import exception.ValidationException;
import util.IdGenerator;

public class User {
    private final Long id;
    private String name;
    private String email;
    private UserRole role;

    public User(String name, String email, UserRole role) {
        setName(name);
        setEmail(email);
        this.role = role;
        this.id = IdGenerator.getIdForClass(User.class);
    }

    public long getId() {
        return id;
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

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", role=" + role +
                '}';
    }
}
