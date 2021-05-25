package com.utn.tpFinal.domain;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.AccessType;

import javax.persistence.*;


@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, property = "typeUser", visible = true)
@JsonSubTypes({
        @JsonSubTypes.Type(value = Client.class, name ="CLIENT"),
        @JsonSubTypes.Type(value = Admin.class, name ="ADMIN")
})

@Entity
@Table(name = "Users")
public abstract class User
{
    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    @Column(name = "id_user")
    private String userId;

    @Column(name = "user_name")
    private String userName;

    @Column(name = "password")
    private String password;

    @AccessType(AccessType.Type.PROPERTY)
    public abstract TypeUser typeUser();





}
