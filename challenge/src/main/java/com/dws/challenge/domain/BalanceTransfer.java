package com.dws.challenge.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.math.BigDecimal;
import jakarta.validation.constraints.DecimalMin;
import lombok.Data;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

/**
 * BalanceTransfer model class to bind transaction details for transfer of funds
 */
@Data
public class BalanceTransfer {

    /**
     * From account id should not be null and empty
     */
    @NotNull
    @NotEmpty
    @JsonProperty("fromAccountId")
    private String fromAccountId;

    /**
     * To account id should not be null and empty
     */
    @NotNull
    @NotEmpty
    @JsonProperty("toAccountId")
    private String toAccountId;

    /**
     * Amount to transfer should not be less than or equal to 0
     */
    @NotNull
    @DecimalMin(value = "0.0", inclusive = false,message = "Minimum value to transfer is 1")
    @JsonProperty("amount")
    private BigDecimal amount;

    public BalanceTransfer() {
    }
}
