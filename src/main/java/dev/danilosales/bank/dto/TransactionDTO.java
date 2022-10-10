package dev.danilosales.bank.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import dev.danilosales.bank.validations.account.AccountExists;
import dev.danilosales.bank.validations.operationtype.OperationTypeExists;
import lombok.*;

import javax.money.MonetaryAmount;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

@Getter @Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TransactionDTO {

    @JsonProperty("transaction_id")
    private Long id;

    @JsonProperty("account_id")
    @NotNull
    @AccountExists
    private Long accountId;

    @JsonProperty("operation_type")
    @NotNull
    @OperationTypeExists
    private Integer operationType;

    @NotNull
    @Positive
    private MonetaryAmount value;
}
