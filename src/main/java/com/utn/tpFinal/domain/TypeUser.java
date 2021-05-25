package com.utn.tpFinal.domain;

public enum TypeUser
{
    CLIENT("Client"),
    ADMIN("Admin");

    private String descryption;

    TypeUser(String descryption) {
        this.descryption = descryption;
    }

    public static TypeUser find(final String value) {
        for (TypeUser v : values()) {
            if (v.toString().equalsIgnoreCase(value)) {
                return v;
            }
        }
        throw new IllegalArgumentException(String.format("Invalid TypeUser: %s", value));
    }

    public String getDescryption() {
        return descryption;
    }
}
