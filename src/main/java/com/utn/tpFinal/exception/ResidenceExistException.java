package com.utn.tpFinal.exception;

import org.springframework.http.HttpStatus;

public class ResidenceExistException extends ApiError
{
    public ResidenceExistException()
    {
        this.setHttpStatus(HttpStatus.CONFLICT);
        this.setMessage("Residence already exist...");
    }

    @Override
    public String getMessage() {
        return super.getMessage();
    }
}
