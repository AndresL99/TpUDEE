package com.utn.tpFinal.repository;

import com.utn.tpFinal.domain.Invoice;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface InvoiceRepository extends JpaRepository<Invoice,Integer> {

    @Query(value = "SELECT * FROM INVOICES I " +
            "JOIN RESIDENCES R ON I.id_residence = R.id_residence " +
            "JOIN CLIENTS C ON R.dni_client = C.dni_client",nativeQuery = true)
    Page<Invoice> findByDNIClient(@Param("dni_client")Integer dni, Pageable pageable);


}
