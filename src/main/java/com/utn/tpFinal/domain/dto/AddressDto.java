package com.utn.tpFinal.domain.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;

@Data
@NoArgsConstructor
public class AddressDto {

    private Integer addressId;

    private String streetName;

    private Integer streetNumber;
}
