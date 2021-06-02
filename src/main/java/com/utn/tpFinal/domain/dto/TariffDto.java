package com.utn.tpFinal.domain.dto;

import lombok.Data;
import lombok.NoArgsConstructor;



@Data
@NoArgsConstructor
public class TariffDto {

    private Integer tariffId;

    private String tariffName;

    private Float tariffValue;
}
