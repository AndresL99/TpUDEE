package com.utn.tpFinal.domain.dto;


import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class AdminDto {

    private String email;

    private Integer dni;

    private String firstName;

    private String lastName;
}
