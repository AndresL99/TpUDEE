package com.utn.tpFinal.domain;


import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
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
    private LocalDate duelDate;

    @NonNull
    @Column(name="first_reading")
    private Float firstReading;

    @NonNull
    @Column(name="last_reading")
    private Float lastReading;

    @NonNull
    @Column(name="total_cons_kw")
    private Float totalConsumptionKwh;

    @NonNull
    @Column(name="initial_date")
    private LocalDate initialDate;

    @NonNull
    @Column(name="last_date")
    private LocalDate lastDate;

    @NonNull
    @Column(name="total_amount")
    private Float totalAmount;

    @NonNull
    @ManyToOne
    @JoinColumn(name = "residence",nullable = false, updatable = false)
    @JsonIgnore
    @JsonBackReference
    private Residence residence;


}
