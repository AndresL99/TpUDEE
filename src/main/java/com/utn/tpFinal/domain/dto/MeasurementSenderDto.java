package com.utn.tpFinal.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MeasurementSenderDto {

    String serialNumber;
    float kw;
    String date;
    String password;
}
