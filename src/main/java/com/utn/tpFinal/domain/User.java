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
@Entity
@Table(name = "Users")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, property = "TypeUser",visible = true)
@JsonSubTypes({
        @JsonSubTypes.Type(value = Client.class, name = "Client"),
        @JsonSubTypes.Type(value = Admin.class, name = "Employee")
})
public abstract class User
{
    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    @Column(name = "id_user")
    private Integer userId;

    @Column(name = "user_name")
    private String userName;

    @Column(name = "password")
    private String password;

    /*@AccessType(AccessType.Type.PROPERTY)
    public abstract TypeUser typeUser();
*/
    public enum TypeUser{
        EMPLOYEE,
        CLIENT
    }
}
