package com.utn.tpFinal.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

import static javax.persistence.CascadeType.ALL;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Builder
@Table(name = "residences")
public class Residence {

    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    @Column (name = "id_residence")
    private Integer residenceId;

    @Column(name = "name")
    private String name;

    @ManyToOne
    @JoinColumn(name = "id_client", nullable = false, updatable = false)
    @JsonBackReference
    private Client client;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_address")
    private Address address;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name ="id_meter")
    private Meter meter;

    @ManyToOne
    @JoinColumn(name = "id_tariff", nullable = false, updatable = false)
    @JsonBackReference
    private Tariff tariff;

    @OneToMany(cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    @JoinColumn(name = "id_invoice")
    private List<Invoice> invoiceList;




}
