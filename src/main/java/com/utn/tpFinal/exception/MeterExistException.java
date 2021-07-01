package com.utn.tpFinal.exception;

import org.springframework.http.HttpStatus;

public class MeterExistException extends ApiError
{
    public MeterExistException()
    {
        this.setHttpStatus(HttpStatus.CONFLICT);
        this.setMessage("Meter already exist..");
    }

    @Override
    public String getMessage() {
        return super.getMessage();
    }
}
