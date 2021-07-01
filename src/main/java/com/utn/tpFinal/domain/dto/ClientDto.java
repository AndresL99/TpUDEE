package com.utn.tpFinal.domain.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ClientDto {

    private Integer clientId;

    private String email;

    private Integer dni;

    private String firstName;

    private String lastName;
}
