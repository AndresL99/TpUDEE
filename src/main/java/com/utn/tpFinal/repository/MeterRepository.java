package com.utn.tpFinal.repository;

import com.utn.tpFinal.domain.Meter;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MeterRepository extends JpaRepository<Meter,Integer> {
}
