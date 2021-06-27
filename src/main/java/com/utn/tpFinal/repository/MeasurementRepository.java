package com.utn.tpFinal.repository;

import com.utn.tpFinal.domain.Measurement;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Date;

@Repository
public interface MeasurementRepository extends JpaRepository<Measurement,Integer>
{


    @Query(value = "SELECT m* " +
            "FROM measurements " +
            "INNER JOIN meters me " +
            "ON me.id_measurement = m.id_measurement" +
            "INNER JOIN residences r" +
            "WHERE r.id_residence = :idResidence AND m.measurement_date BETWEEN :start AND :end",nativeQuery = true)
    Page<Measurement> getMeasurementByResidenceAndRank(Integer idResidence, Date start, Date end, Pageable pageable);


    @Query(value = "SELECT m* " +
            "FROM measurements " +
            "INNER JOIN meters me " +
            "ON me.id_measurement = m.id_measurement" +
            "INNER JOIN residences r" +
            "on r.id_meter = m.id_meter" +
            "INNER JOIN CLIENT c" +
            "WHERE r.id_client = :idClient AND m.measurement_date BETWEEN :start AND :end",nativeQuery = true)
    Page<Measurement> getMeasuremtByRank(Integer idClient, Date start, Date end ,Pageable pageable);

}
