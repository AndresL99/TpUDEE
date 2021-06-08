package com.utn.tpFinal.exception;

public class ClientNotExistException extends ApiError
{
    public ClientNotExistException()
    {
        this.setMessage("Client not exist.");
    }

    @Override
    public String getMessage() {
        return getMessage();
    }
}
