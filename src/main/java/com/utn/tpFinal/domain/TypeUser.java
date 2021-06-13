package com.utn.tpFinal.domain;

public enum TypeUser
{
    CLIENT("Client"),
    EMPLOYEE("Employee");

    TypeUser(String descryption) {
        this.descryption = descryption;
    }

    private String descryption;
}
