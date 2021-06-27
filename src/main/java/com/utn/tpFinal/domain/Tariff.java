package com.utn.tpFinal.domain;


import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Builder
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

    @OneToOne( fetch = FetchType.EAGER)
    @JoinColumn(name = "id_residence")
    private Residence residence;
}
