package com.utn.tpFinal.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.utn.tpFinal.domain.dto.MeasurementSenderDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Builder
@Table(name = "measurements")
public class Measurement {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_measurement ")
    private Integer measurementId;

    @Column(name= "measurement_date")
    private LocalDate dateMeasurement;

    @Column(name = "kwh_measurement")
    private Float measurementKwh;

    @ManyToOne
    @JoinColumn(name = "id_meter", nullable = false, updatable = false)
    private Meter meter;


    public static Measurement builderM(MeasurementSenderDTO measureSenderDto)  throws ParseException {
        SimpleDateFormat date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return Measurement.builder()
                .measurementKwh(measureSenderDto.getKw())
                .dateMeasurement(LocalDate.parse(measureSenderDto.getDate()))
                .build();
    }
}
