package com.utn.tpFinal.domain;


import com.fasterxml.jackson.annotation.JsonBackReference;
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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="id_brand", nullable = false)
    @JsonBackReference
    private Brand brand;

    /*

    @OneToMany(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_meter")
    private List<Meter> meterList;


     */


}
