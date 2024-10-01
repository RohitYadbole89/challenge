package com.dws.challenge.repository;

import com.dws.challenge.domain.Account;
import com.dws.challenge.domain.BalanceTransfer;
import com.dws.challenge.exception.DuplicateAccountIdException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Repository
@Slf4j
public class AccountsRepositoryInMemory implements AccountsRepository {

    private final Map<String, Account> accounts = new ConcurrentHashMap<>();

    @Override
    public void createAccount(Account account) throws DuplicateAccountIdException {
        Account previousAccount = accounts.putIfAbsent(account.getAccountId(), account);
        if (previousAccount != null) {
            throw new DuplicateAccountIdException(
                    "Account id " + account.getAccountId() + " already exists!");
        }
    }

    @Override
    public Account getAccount(String accountId) {
        return accounts.get(accountId);
    }

    @Override
    public void clearAccounts() {
        accounts.clear();
    }

    /**
     *  Payment transfer is done and account balance reconciliation is done
     * @param fromAccount
     * @param toAccount
     * @param balanceTransfer
     * @return BalanceTransfer
     */
    @Override
    public BalanceTransfer transferAmount(Account fromAccount, Account toAccount, BalanceTransfer balanceTransfer) {
        log.info(Thread.currentThread().getName()+"- Transferring  amount {} from Account {} to Account {} ", balanceTransfer.getAmount(),balanceTransfer.getFromAccountId(),balanceTransfer.getToAccountId());
        BigDecimal remainingBalance=fromAccount.getBalance().subtract(balanceTransfer.getAmount());
        fromAccount.setBalance(remainingBalance);

        BigDecimal totalAmount=toAccount.getBalance().add(balanceTransfer.getAmount());
        toAccount.setBalance(totalAmount);

        accounts.put(fromAccount.getAccountId(),fromAccount);
        accounts.put(toAccount.getAccountId(),toAccount);

        return balanceTransfer;
    }

}
