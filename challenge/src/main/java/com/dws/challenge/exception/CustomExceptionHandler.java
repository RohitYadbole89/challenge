package com.dws.challenge.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

/**
 * Controller advice class for handling different exceptions globally at centralized place
 * @author Rohit Yadbole
 */
@RestControllerAdvice
public class CustomExceptionHandler
{

    /**
     * if the binding value to the model is incorrect as per valition then responds with BAD_REQUEST
     * @param exception
     * @return HttpStatus.BAD_REQUEST
     */
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Map<String,String> handleInvalidArgument(MethodArgumentNotValidException exception)
    {
        Map<String,String>errorMap=new HashMap<>();
        exception.getBindingResult().getFieldErrors().forEach(error->
        {
            errorMap.put("message",error.getDefaultMessage());
        });
        return errorMap;
    }

    /**
     * if there is no sufficient funds available in account then respond BAD_REQUEST with error description
     * @param exception
     * @return HttpStatus.BAD_REQUEST
     */
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(InSufficientBalanceException.class)
    public Map<String,String> handleInSufficientBalanceException(InSufficientBalanceException exception)
    {
        Map<String,String>errorMap=new HashMap<>();
        errorMap.put("message",exception.getMessage());
        return errorMap;
    }

    /**
     * if the account does not exists then respond BAD_REQUEST with error description
     * @param exception
     * @return HttpStatus.BAD_REQUEST
     */
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(AccountDoesNotExistsException.class)
    public Map<String,String> handleAccountDoesNotExistsException(AccountDoesNotExistsException exception)
    {
        Map<String,String>errorMap=new HashMap<>();
        errorMap.put("message",exception.getMessage());
        return errorMap;
    }
}