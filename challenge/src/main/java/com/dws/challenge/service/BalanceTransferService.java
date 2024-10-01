package com.dws.challenge.service;

import com.dws.challenge.domain.BalanceTransfer;
import com.dws.challenge.exception.AccountDoesNotExistsException;
import com.dws.challenge.exception.InSufficientBalanceException;

import java.util.concurrent.CompletableFuture;
/**
 * Interface payment transfer
 * @author Rohit Yadbole
 */
public interface BalanceTransferService {
    CompletableFuture<BalanceTransfer> processTransaction(BalanceTransfer balanceTransfer) throws AccountDoesNotExistsException, InSufficientBalanceException;
}
