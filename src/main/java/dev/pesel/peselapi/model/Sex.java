package dev.pesel.peselapi.model;

public enum Sex {
    MALE, FEMALE;

    public static Sex fromString(String v) {
        if (v == null) return null;
        return switch (v.trim().toLowerCase()) {
            case "m", "male" -> MALE;
            case "f", "female" -> FEMALE;
            default -> throw new IllegalArgumentException("Unsupported sex: " + v);
        };
    }
}