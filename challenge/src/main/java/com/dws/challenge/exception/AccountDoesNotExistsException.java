package com.dws.challenge.exception;

/**
 * Exception class for Account does not exists
 * @author Rohit Yadbole
 */
public class AccountDoesNotExistsException extends RuntimeException {

  public AccountDoesNotExistsException(String message) {
    super(message);
  }
}
