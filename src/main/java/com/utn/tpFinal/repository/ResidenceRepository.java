package com.utn.tpFinal.repository;

import com.utn.tpFinal.domain.Residence;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ResidenceRepository extends JpaRepository<Residence,Integer> {
    Page<Residence> findByClientClientId(Integer idClient, Pageable pageable);
    Optional<Residence> findByMeter_MeterId(Integer meterId);
}
