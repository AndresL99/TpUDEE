package com.utn.tpFinal.repository;

import com.utn.tpFinal.domain.Measurement;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.Date;

@Repository
public interface MeasurementRepository extends JpaRepository<Measurement,Integer>
{


    @Query(value = "SELECT m.* " +
            "FROM measurements m " +
            "INNER JOIN meters me " +
            "ON me.id_measurement = m.id_measurement " +
            "INNER JOIN residences r " +
            "WHERE r.id_residence = :residenceId AND m.measurement_date BETWEEN :start AND :end ",nativeQuery = true)
    Page<Measurement> getMeasurementByResidenceAndRank(@Param("residenceId") Integer residenceId,@Param("start") LocalDateTime start, @Param("end") LocalDateTime end, Pageable pageable);


    @Query(value = "SELECT m.* " +
            "FROM measurements m " +
            "INNER JOIN meters me " +
            "ON me.id_measurement = m.id_measurement " +
            "INNER JOIN residences r " +
            "on me.id_meter = r.id_meter " +
            "INNER JOIN clients c " +
            "WHERE c.id_client = :idClient AND m.measurement_date BETWEEN :start AND :end ",nativeQuery = true)
    Page<Measurement> getMeasuremtByRank(@Param("idClient") Integer idClient, @Param("start") LocalDateTime start, @Param("end") LocalDateTime end , Pageable pageable);

}
