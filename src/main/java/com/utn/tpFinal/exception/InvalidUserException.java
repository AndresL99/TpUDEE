package com.utn.tpFinal.exception;

import org.springframework.http.HttpStatus;

import java.util.List;

public class InvalidUserException extends ApiError
{
    public InvalidUserException()
    {
        setHttpStatus(HttpStatus.CONFLICT);
        setMessage("Invalid Password or User name");
    }

}
