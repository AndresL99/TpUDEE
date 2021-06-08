package com.utn.tpFinal.exception;

import org.springframework.http.HttpStatus;

public class InvoiceExistException extends ApiError
{
    public InvoiceExistException()
    {
        this.setHttpStatus(HttpStatus.CONFLICT);
        this.setMessage("Invoice already exist..");
    }

    @Override
    public String getMessage() {
        return super.getMessage();
    }
}
