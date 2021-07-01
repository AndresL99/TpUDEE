package com.utn.tpFinal.repository;

import com.utn.tpFinal.domain.Invoice;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;

@Repository
public interface InvoiceRepository extends JpaRepository<Invoice,Integer> {

    @Query(value = "SELECT * FROM INVOICES I " +
            "JOIN RESIDENCES R ON I.id_residence = R.id_residence " +
            "JOIN CLIENTS C ON R.dni_client = C.dni_client",nativeQuery = true)
    Page<Invoice> findByDNIClient(@Param("dni_client")Integer dni, Pageable pageable);

    @Query(value = "SELECT i.* FROM invoices i " +
            "JOIN residences r ON i.id_residence = r.id_residence " +
            "JOIN clients c ON r.id_client = c.id_client " +
            "WHERE c.id_client = :idClient AND i.initial_date BETWEEN :start AND :end " ,nativeQuery = true)
    Page<Invoice> findByClientBetweenDates(Integer idClient, LocalDateTime start, LocalDateTime end , Pageable pageable);
    Page<Invoice>findByResidence_Client_ClientIdAndInitialDateBetween(@Param("idClient") Integer idClient, @Param("start") LocalDateTime start, @Param("end") LocalDateTime end, Pageable pageable);


    @Query(value = "SELECT i.* FROM invoices i " +
            "JOIN residences r " +
            "ON r.id_residence =i.id_residence "+
            "WHERE i.is_paid = false AND  r.id_residence = :idResidences AND r.id_client = :idClient ", nativeQuery = true)
    Page<Invoice> findAllResidenceClientUserId(Integer idResidences, Integer idClient,Pageable pageable);


    //web 3
    //Page<Invoice> findByResidence_ClientAndIsPaidIsFalse(Integer idClient, Pageable pageable);
    Page<Invoice>findByResidence_Client_ClientIdAndAndIsPaidFalse(Integer idClient,Pageable pageable);

    @Query(value = "SELECT i.* FROM invoices i "+
                    "JOIN residences r ON i.id_residence = r.id_residence "+
                    "JOIN clients c ON r.id_client = c.id_client "+
                    "WHERE i.is_paid = false AND c.id_client = :idClient AND r.id_residence = :idResidence", nativeQuery = true)
    Page<Invoice>findAllByResidence_ResidenceIdAndAndResidence_ClientClientIdAndAndIsPaidFalse(Integer idClient,Integer idResidence,Pageable pageable);
}
