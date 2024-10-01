package com.dws.challenge.service.impl;

import com.dws.challenge.domain.Account;
import com.dws.challenge.domain.BalanceTransfer;
import com.dws.challenge.exception.AccountDoesNotExistsException;
import com.dws.challenge.exception.InSufficientBalanceException;
import com.dws.challenge.repository.AccountsRepository;
import com.dws.challenge.service.AccountsService;
import com.dws.challenge.service.BalanceTransferService;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import java.math.BigDecimal;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;

/**
 * Implementation class for payment transfer
 * @author Rohit Yadbole
 */

@Service
@Slf4j
public class BalanceTransferServiceImpl implements BalanceTransferService {

  @Getter
  @Autowired
  AccountsRepository accountsRepository;

  @Getter
  @Autowired
  AccountsService accountsService;

  /**
   * This method will process balance transfer transaction
   * @param balanceTransfer
   * @return CompletableFuture<BalanceTransfer>
   * @throws AccountDoesNotExistsException
   * @throws InSufficientBalanceException
   */
  @Async
  @Override
  public CompletableFuture<BalanceTransfer> processTransaction(BalanceTransfer balanceTransfer) throws AccountDoesNotExistsException, InSufficientBalanceException {
    Account fromAccount= getAccountDetails(balanceTransfer.getFromAccountId()).get();
    balanceEnquiry(balanceTransfer);

    if(balanceTransfer.getFromAccountId().equals(balanceTransfer.getToAccountId())) {
      log.error(Thread.currentThread().getName()+"- Cannot transfer amount to same account id " + balanceTransfer.getFromAccountId());
      throw new AccountDoesNotExistsException("Cannot transfer amount to same account id " + balanceTransfer.getFromAccountId());
    }

    Account toAccount=  getAccountDetails(balanceTransfer.getToAccountId()).get();
    BalanceTransfer txn=accountsRepository.transferAmount(fromAccount,toAccount,balanceTransfer);
    return CompletableFuture.completedFuture(txn);

  }

  /**
   * method to check account balance
   * @param balanceTransfer
   * @throws InSufficientBalanceException
   * @throws AccountDoesNotExistsException
   */
  public void balanceEnquiry(BalanceTransfer balanceTransfer) throws InSufficientBalanceException, AccountDoesNotExistsException {
    log.info(Thread.currentThread().getName()+"- Balance Enquiry for account id " + balanceTransfer.getFromAccountId());
    Account account= Optional.of(getAccountDetails(balanceTransfer.getFromAccountId()).get()).get();
    BigDecimal amt=balanceTransfer.getAmount();
    if(account.getBalance().compareTo(amt)<0){
      throw new InSufficientBalanceException("You have Insufficient Account Balance");
    }

  }

  /**
   * Get Account details method
   * @param accountId
   * @return Optional<Account>
   * @throws AccountDoesNotExistsException
   */
  public Optional<Account> getAccountDetails(String accountId) throws AccountDoesNotExistsException {
    log.info(Thread.currentThread().getName()+"- Retrieving account details for Account id {}", accountId);
    Optional<Account> accountDetails= Optional.ofNullable(accountsService.getAccount(accountId));
    accountDetails.orElseThrow(()->new AccountDoesNotExistsException("Account Id: "+accountId+" does not exists"));
    return accountDetails;
  }

}
