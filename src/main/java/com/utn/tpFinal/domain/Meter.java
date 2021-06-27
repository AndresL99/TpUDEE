package com.utn.tpFinal.domain;


import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
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

    @ManyToOne
    @JoinColumn(name = "id_model", nullable = false, updatable = false)
    private Model model;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "meter", fetch = FetchType.LAZY)
    private List<Measurement> measurementList;

    @OneToOne(mappedBy = "meter",fetch = FetchType.LAZY)
    @ToString.Exclude
    private Residence residence;


}
