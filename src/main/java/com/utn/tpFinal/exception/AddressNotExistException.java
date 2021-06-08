package com.utn.tpFinal.exception;

public class AddressNotExistException extends ApiError
{
    public AddressNotExistException()
    {
        this.setMessage("Address not exist..");
    }

    @Override
    public String getMessage() {
        return getMessage();
    }
}
