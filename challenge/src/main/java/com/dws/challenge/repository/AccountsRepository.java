package com.dws.challenge.repository;

import com.dws.challenge.domain.Account;
import com.dws.challenge.domain.BalanceTransfer;
import com.dws.challenge.exception.DuplicateAccountIdException;

public interface AccountsRepository {

  void createAccount(Account account) throws DuplicateAccountIdException;

  Account getAccount(String accountId);

  void clearAccounts();

  BalanceTransfer transferAmount(Account fromAccount, Account toAccount, BalanceTransfer balanceTransfer);
}
