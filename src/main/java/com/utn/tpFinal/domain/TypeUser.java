package com.utn.tpFinal.domain;

public enum TypeUser
{
    CLIENT("Client"),
    ADMIN("Admin");

    private String descryption;

    TypeUser(String descryption) {
        this.descryption = descryption;
    }

    public String getDescryption() {
        return descryption;
    }
}
