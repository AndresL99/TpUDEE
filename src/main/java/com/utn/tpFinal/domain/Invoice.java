package com.utn.tpFinal.domain;


import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Builder
@Table(name = "invoices")
public class Invoice {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id_invoice")
    private Integer invoiceId;

    @NonNull
    @Column(name="is_paid")
    private Boolean isPaid;

    @NonNull
    @Column(name="due_date")
    private LocalDateTime duelDate;

    @NonNull
    @Column(name="first_read")
    private Float firstReading;

    @NonNull
    @Column(name="last_read")
    private Float lastReading;

    @NonNull
    @Column(name="total_cons_kw")
    private Float totalConsumptionKwh;

    @NonNull
    @Column(name="initial_date")
    private LocalDateTime initialDate;

    @NonNull
    @Column(name="last_date")
    private LocalDateTime lastDate;

    @NonNull
    @Column(name="total_amount")
    private Float totalAmount;

    @NonNull
    @ManyToOne(fetch = FetchType.LAZY,cascade =  CascadeType.ALL)
    @JoinColumn(name = "id_residence",nullable = false, updatable = false)
    @JsonBackReference
    private Residence residence;


}
