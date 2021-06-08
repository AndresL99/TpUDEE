package com.utn.tpFinal.exception;

public class InvoiceNotExistExpection extends ApiError
{
    public InvoiceNotExistExpection()
    {
        this.setMessage("Invoice not exists..");
    }

    @Override
    public String getMessage() {
        return super.getMessage();
    }
}
