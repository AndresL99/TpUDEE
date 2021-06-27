package com.utn.tpFinal.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ConsumeptionAndCostDTO {

    private Float TotalKwh ;
    private Float TotalCost;
}
