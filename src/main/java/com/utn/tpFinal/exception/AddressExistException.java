package com.utn.tpFinal.exception;

import org.springframework.http.HttpStatus;

public class AddressExistException extends ApiError
{
    public AddressExistException()
    {
        this.setHttpStatus(HttpStatus.CONFLICT);
        this.setMessage("Address already exist..");
    }

    @Override
    public String getMessage() {
        return getMessage();
    }
}
