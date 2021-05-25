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
@Table(name = "clients")
public class Client extends User
{

    @Column(name = "email_client")
    private String email;

    @Column(name = "dni_client")
    private Integer dni;

    @Column(name = "first_name_client")
    private String firstName;

    @Column(name = "last_name_client")
    private String lastName;

    @Override
    public TypeUser typeUser() {
        return TypeUser.CLIENT;
    }
}
