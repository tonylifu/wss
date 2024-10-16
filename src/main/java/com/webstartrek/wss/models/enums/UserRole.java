package com.webstartrek.wss.models.enums;

public enum UserRole {
    ADMIN("ADMIN"),
    TEACHER("TEACHER"),
    CASHIER("CASHIER"),
    BURSAR("BURSAR"),
    ADMISSION_OPS("ADMISSION_OPS"),
    SECURITY("SECURITY"),
    OPERATION("OPERATION"),
    NONE("NONE");

    private final String displayName;

    UserRole(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }

    @Override
    public String toString() {
        return displayName;
    }

    public static UserRole fromString(String value) {
        for (UserRole userRole : UserRole.values()) {
            if (userRole.displayName.equalsIgnoreCase(value)) {
                return userRole;
            }
        }
        throw new IllegalArgumentException("Unknown role: " + value);
    }
}
