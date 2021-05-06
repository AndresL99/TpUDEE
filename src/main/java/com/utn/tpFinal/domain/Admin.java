package com.utn.tpFinal.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "admins")
public class Admin extends User
{
    @Column(name = "admin_email")
    private String email;

    @Column(name = "admin_dni")
    private Integer dni;

    @Column(name = "admin_first_name")
    private String firstName;

    @Column(name = "admin_last_name")
    private String lastName;

    @Override
    public TypeUser typeUser() {
        return TypeUser.ADMIN;
    }
}
