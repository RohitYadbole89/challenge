package com.dws.challenge.domain;

import lombok.Data;

/**
 * Custom error class to handle error or exception scenario's and respond client with correct info
 * @author Rohit Yadbole
 */
@Data
public class CustomError {

    /**
     * error description
     */
    public String errorDesc;
}
