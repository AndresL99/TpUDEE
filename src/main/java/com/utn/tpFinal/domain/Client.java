package com.utn.tpFinal.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "clients")
public class Client
{

    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    @Column(name = "id_client")
    private Integer clientId;
    @NotNull
    @Email
    @Column(name = "email_client")
    private String email;

    @NotNull
    @Positive
    @Column(name = "dni_client")
    private Integer dni;

    @NotNull
    @Column(name = "first_name_client")
    private String firstName;

    @NotNull
    @Column(name = "last_name_client")
    private String lastName;

    @OneToOne
    @JoinColumn(name = "username",foreignKey = @ForeignKey(name="FK_clients_users"))
    private User user;


    @OneToMany(cascade = CascadeType.ALL, mappedBy = "client", fetch = FetchType.LAZY)
    private List<Residence> residenceList;

}
