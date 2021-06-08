package com.utn.tpFinal.exception;

import org.springframework.http.HttpStatus;

public class TariffNotExistException extends ApiError
{
    @Override
    public String getMessage() {
        return "Tariff not exist..";
    }
}
