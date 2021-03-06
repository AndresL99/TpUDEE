package com.utn.tpFinal.domain;


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
@Table(name = "tariffs")
public class Tariff {

    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    @Column(name = "id_tariff")
    private Integer tariffId;
    @Column(name ="name")
    private String tariffName;
    @Column(name ="value")
    private Float tariffValue;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "tariff", fetch = FetchType.LAZY)
    private List<Residence> residenceList;
}
