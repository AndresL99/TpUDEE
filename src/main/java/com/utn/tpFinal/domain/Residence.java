package com.utn.tpFinal.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

import static javax.persistence.CascadeType.ALL;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "Residences")
public class Residence {

    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    @Column (name = "residence_id")
    private Integer residenceId;


    @ManyToOne
    @JoinColumn(name = "client", nullable = false, updatable = false)
    @JsonBackReference
    private User client;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_address")
    private Address address;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name ="id_meter")
    private Meter meter;

    @ManyToOne
    @JoinColumn(name = "tariff", nullable = false, updatable = false)
    @JsonBackReference
    private Tariff tariff;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "residence", fetch = FetchType.LAZY)
    private List<Invoice> invoiceList;




}
