package com.utn.tpFinal.repository;

import com.utn.tpFinal.domain.Measurement;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;

@Repository
public interface MeasurementRepository extends JpaRepository<Measurement,Integer> {


    Page<Measurement> getMeasurementByResidenceAndRank(Integer idResidence, LocalDateTime from, LocalDateTime to, Pageable pageable);
}
