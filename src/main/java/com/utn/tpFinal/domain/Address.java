package com.utn.tpFinal.domain;

import lombok.*;

import javax.persistence.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Builder
@Table(name= "address" )
public class Address {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_address")
    private Integer addressId;

    @Column(name ="name_address")
    private String streetName;

    @Column(name = "number_address")
    private Integer streetNumber;

    @OneToOne(mappedBy = "address",fetch = FetchType.LAZY)
    @ToString.Exclude
    private Residence residence;





}
