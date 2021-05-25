package com.utn.tpFinal.domain;


import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "Invoices")
public class Invoice {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id_invoice")
    private Integer invoiceId;

    @Column(name="is_paid")
    private Boolean isPaid;

    @Column(name="due_date")
    private Date duelDate;

    @Column(name="first_reading")
    private Float firstReading;

    @Column(name="last_reading")
    private Float lastReading;

    @Column(name="total_cons_kw")
    private Float totalConsumptionKwh;

    @Column(name="initial_date")
    private Date initialDate;

    @Column(name="last_date")
    private Date lastDate;

    @Column(name="total_amount")
    private Float totalAmount;



    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="residence_id", nullable = false)
    @JsonBackReference
    private Residence residence;






}
