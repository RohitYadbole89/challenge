package com.dws.challenge.web;

import com.dws.challenge.domain.Account;
import com.dws.challenge.domain.BalanceTransfer;
import com.dws.challenge.service.AccountsService;
import com.dws.challenge.service.NotificationService;
import com.dws.challenge.service.BalanceTransferService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;
import java.util.concurrent.CompletableFuture;

/**
 * Controller class to handle payment transfer
 * @author Rohit Yadbole
 */
@RestController
@RequestMapping("/v1/transfer")
@Slf4j
public class TransferController {

  @Autowired
  AccountsService accountsService;

  @Autowired
  BalanceTransferService balanceTransferService;

  @Autowired
  NotificationService notificationService;

  /**
   * Method to transfer amount to other and send payment notification to debtor and creditor
   * @param balanceTransfer
   * @return CompletableFuture<ResponseEntity>
   */
  @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE,produces = MediaType.APPLICATION_JSON_VALUE)
  public CompletableFuture<ResponseEntity> transfer(@RequestBody @Valid BalanceTransfer balanceTransfer) {

      CompletableFuture<BalanceTransfer> txn = balanceTransferService.processTransaction(balanceTransfer);
      return txn.thenApply(resultantTxn->{
        Account fromAccount=accountsService.getAccount(resultantTxn.getFromAccountId());
        notificationService.notifyAboutTransfer(fromAccount,"Amount "+resultantTxn.getAmount()+" transferred to Account id "+resultantTxn.getToAccountId());

        Account toAccount=accountsService.getAccount(resultantTxn.getToAccountId());
        notificationService.notifyAboutTransfer(toAccount,"Amount "+resultantTxn.getAmount()+" received from Account id "+resultantTxn.getFromAccountId());
        return CompletableFuture.completedFuture(resultantTxn);
      }).thenApply(ResponseEntity::ok);

  }

}
