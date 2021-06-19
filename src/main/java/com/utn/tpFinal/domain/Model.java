package com.utn.tpFinal.domain;


import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "models")
public class Model {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_model")
    private Integer modelId;

    @Column(name = "model")
    private String model;


    @ManyToOne
    @JoinColumn(name = "brand", nullable = false, updatable = false)
    private Brand brand;


    @OneToMany(cascade = CascadeType.ALL, mappedBy = "model", fetch = FetchType.LAZY)
    private List<Meter> meterList;


}
