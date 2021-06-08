package com.utn.tpFinal.exception;

import org.springframework.http.HttpStatus;

public class ResidenceNotExistException extends ApiError
{
    @Override
    public String getMessage() {
        return "Residence already exist.";
    }
}
