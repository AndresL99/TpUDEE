package com.utn.tpFinal.exception;

public class MeterNotExistException extends ApiError {

    public MeterNotExistException()
    {
        this.setMessage("Meter not exist.");
    }

    @Override
    public String getMessage() {
        return getMessage();
    }
}
