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
@Table(name = "residences")
public class Residence {

    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    @Column (name = "id_residence")
    private Integer residenceId;


    @ManyToOne
    @JoinColumn(name = "dni_client", nullable = false, updatable = false)
    @JsonBackReference
    private Client client;

    @OneToOne(cascade = ALL)
    @JoinColumn(name = "id_address")
    private Address address;

    @OneToOne(cascade = ALL)
    @JoinColumn(name = "id_meter")
    private Meter meter;

    @OneToOne
    @JoinColumn(name = "id_tariff", nullable = false, updatable = false)
    @JsonBackReference
    private Tariff tariff;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "residence", fetch = FetchType.LAZY)
    private List<Invoice> invoiceList;




}
