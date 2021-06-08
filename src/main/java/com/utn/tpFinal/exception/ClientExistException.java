package com.utn.tpFinal.exception;

import org.springframework.http.HttpStatus;

public class ClientExistException extends ApiError
{
    public ClientExistException()
    {
        this.setHttpStatus(HttpStatus.CONFLICT);
        this.setMessage("Client already exist.");
    }

    @Override
    public String getMessage() {
        return getMessage();
    }
}
