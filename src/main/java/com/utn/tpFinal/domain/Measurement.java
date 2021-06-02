package com.utn.tpFinal.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "measurements")
public class Measurement {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_measurement ")
    private Integer measurementId;

    @Column(name= "measurement_date")
    private Date dateMeasurement;

    @Column(name = "kwh_measurement")
    private Float measurementKwh;

    @ManyToOne
    @JoinColumn(name = "meter", nullable = false, updatable = false)
    private Meter meter;

}
