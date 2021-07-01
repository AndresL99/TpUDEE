package com.utn.tpFinal.domain;


import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Builder
@Table(name = "meters")
public class Meter {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_meter")
    private Integer meterId;

    @Column(name = "serial_number")
    private String serialNumber;

    @Column(name = "password")
    private String password;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_model" ,updatable = false)
    @JsonBackReference
    private Model model;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "id_measurement")
    private List<Measurement> measurementList;

    @OneToOne(fetch = FetchType.LAZY)
    @ToString.Exclude
    private Residence residence;


}
