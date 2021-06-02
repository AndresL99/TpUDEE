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


    @OneToMany(cascade = CascadeType.ALL, mappedBy = "brand", fetch = FetchType.LAZY)
    //@JoinColumn(name ="brand")
    private List<Model> modelList;


}
