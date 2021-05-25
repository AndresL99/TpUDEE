package com.utn.tpFinal.domain.dto;


import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;

@Data
@NoArgsConstructor
public class ClientDto {

    private String email;

    private Integer dni;

    private String firstName;

    private String lastName;
}
