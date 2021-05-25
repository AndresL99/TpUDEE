package com.utn.tpFinal.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Data
@NoArgsConstructor

@Entity
@Table(name = "admins")
public class Admin extends User
{
    @Column(name = "email_admin")
    private String email;

    @Column(name = "dni_admin")
    private Integer dni;

    @Column(name = "first_name_admin")
    private String firstName;

    @Column(name = "last_name_admin")
    private String lastName;

    @Override
    public TypeUser typeUser() {
        return TypeUser.ADMIN;
    }
}
