package com.utn.tpFinal.repository;

import com.utn.tpFinal.domain.Measurement;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@Repository
public interface MeasurementRepository extends JpaRepository<Measurement,Integer> {

    @Query(value = "SELECT m* " +
            "FROM MEASUREMENTS " +
            "INNER JOIN METER me " +
            "ON me.id_measurement = m.id_measurement" +
            "INNER JOIN RESIDENCES R" +
            "WHERE R.idResidence = :idResidence AND m.DATE BETWEEN :start AND :end",nativeQuery = true)
    Page<Measurement> getMeasurementByResidenceAndRank(Integer idResidence, LocalDateTime from, LocalDateTime to, Pageable pageable);


    @Query(value = "SELECT m* " +
            "FROM MEASUREMENTS " +
            "INNER JOIN METER me " +
            "ON me.id_measurement = m.id_measurement" +
            "INNER JOIN RESIDENCES R" +
            "on R.id_meter = M.id_meter" +
            "INNER JOIN CLIENT C" +
            "WHERE R.id_user = :idClient AND m.DATE BETWEEN :start AND :end",nativeQuery = true)
    Page<Measurement> getMeasuremtByRank(Integer idClient, Date start, Date end ,Pageable pageable);


}
