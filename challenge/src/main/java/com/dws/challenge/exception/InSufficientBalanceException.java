package com.dws.challenge.exception;
/**
 * Exception class for InSufficient Balance
 * @author Rohit Yadbole
 */
public class InSufficientBalanceException extends RuntimeException {

  public InSufficientBalanceException(String message) {
    super(message);
  }
}
