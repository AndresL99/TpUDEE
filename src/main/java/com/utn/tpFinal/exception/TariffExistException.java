package com.utn.tpFinal.exception;

import org.springframework.http.HttpStatus;

public class TariffExistException extends ApiError
{
    public TariffExistException()
    {
        this.setHttpStatus(HttpStatus.CONFLICT);
        this.setMessage("Tariff is already exist.");
    }

}
