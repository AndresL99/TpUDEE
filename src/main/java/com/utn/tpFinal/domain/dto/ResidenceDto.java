package com.utn.tpFinal.domain.dto;


import com.utn.tpFinal.domain.Address;
import com.utn.tpFinal.domain.Meter;
import com.utn.tpFinal.domain.Tariff;
import com.utn.tpFinal.domain.User;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ResidenceDto {
    Integer residenceId;
    User client;
    Address address;
    Meter meter;
    Tariff tariff;
}
