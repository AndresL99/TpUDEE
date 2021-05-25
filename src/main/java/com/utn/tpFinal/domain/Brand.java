package com.utn.tpFinal.domain;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "brands")
public class Brand {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_brand")
    private Integer brandId;

    @Column(name = "brand")
    private String brand;

    @OneToMany(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_model")
    private List<Model> modelList;
}
