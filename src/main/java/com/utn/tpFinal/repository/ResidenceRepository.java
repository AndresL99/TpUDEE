package com.utn.tpFinal.repository;

import com.utn.tpFinal.domain.Residence;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ResidenceRepository extends JpaRepository<Residence,Integer> {
    Page<Residence> findByUserId(Integer idClient, Pageable pageable);
    Residence findByMeterId(Integer meterId,Pageable pageable);
}
