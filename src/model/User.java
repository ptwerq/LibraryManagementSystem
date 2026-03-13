package model;

import exception.ValidationException;

public class User {
    private static long UserIdCounter;

    private final Long id;
    private String name;
    private String email;
    private UserRole role;

    public User(String name, String email, String inputToChooseRole) {
        setName(name);
        setEmail(email);
        setRole(inputToChooseRole);
        this.id = UserIdCounter++;
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

    public void setRole(String inputToChooseRole) {
        this.role = switch (inputToChooseRole.toUpperCase().trim()) {
            case "TEACHER" -> UserRole.TEACHER;
            case "STUDENT" -> UserRole.STUDENT;
            case "GUEST" -> UserRole.GUEST;
            default -> {
                throw new ValidationException();
            }
        };
    }
}
