package com.utn.tpFinal.domain.dto;


import com.utn.tpFinal.domain.Meter;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class ModelDto {

    private Integer modelId;

    private String model;

    private List<Meter> meterList;
}
