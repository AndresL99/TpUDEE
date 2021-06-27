package com.utn.tpFinal.repository;

import com.utn.tpFinal.domain.Invoice;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface InvoiceRepository extends JpaRepository<Invoice,Integer> {

    @Query(value = "SELECT * FROM INVOICES I " +
            "JOIN RESIDENCES R ON I.id_residence = R.id_residence " +
            "JOIN CLIENTS C ON R.dni_client = C.dni_client",nativeQuery = true)
    Page<Invoice> findByDNIClient(@Param("dni_client")Integer dni, Pageable pageable);

    @Query(value = "SELECT I FROM Invoice" +
            "JOIN Residence R " +
            "ON I.residenceId = R.residenceId" +
            "JOIN Client C" +
            "ON R.client.userId = c.userId " +
            "WHERE c.userId = :idClient"+
            "AND I.initialDate BETWEEN :start AND :end",nativeQuery = true)
    Page<Invoice> findByClientBetweenDates(Integer idClient, Date start, Date end, Pageable pageable);


    @Query(value = "SELECT * FROM INVOICE" +
            "INNER JOIN RESIDENCE R" +
            "ON I.residenceId =?2" +
            "INNER JOIN CLIENT C" +
            "ON C.userId =?1 " +
            "WHERE paid = false  ", nativeQuery = true)
    Page<Invoice> findAllResidenceClientUserId(Integer idClient, Integer idResidences, Pageable pageable);

    //web 3
    Page<Invoice> findByResidence_ClientAndIsPaidIsFalse(Integer idClient, Pageable pageable);

}
