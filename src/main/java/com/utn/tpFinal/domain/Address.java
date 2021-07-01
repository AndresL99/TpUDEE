package com.utn.tpFinal.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Builder
@Table(name= "addresses" )
public class Address {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_address")
    private Integer addressId;

    @Column(name ="name_address")
    private String streetName;

    @Column(name = "number_address")
    private Integer streetNumber;

    @OneToOne(mappedBy = "address",fetch = FetchType.EAGER,cascade = CascadeType.ALL)
    @ToString.Exclude
    @JsonIgnore
    private Residence residence;





}
