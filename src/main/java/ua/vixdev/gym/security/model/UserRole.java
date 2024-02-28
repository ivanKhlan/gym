package ua.vixdev.gym.security.model;

public enum UserRole {

    ROLE_USER("USER"),
    ROLE_ADMIN("ADMIN");

    private String role;

    UserRole(String role) {
        this.role = role;
    }

    public String getRole() {
        return role;
    }
}
