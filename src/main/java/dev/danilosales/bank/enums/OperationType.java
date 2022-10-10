package dev.danilosales.bank.enums;

import com.fasterxml.jackson.annotation.JsonValue;

import java.util.Arrays;

public enum OperationType {

    BUY_IN_CASH(1, "Compra à vista", false),
    FINANCIAL_BUY(2, "Compra parcelada", false),
    WITHDRAW(3, "Saque", false),
    PAYMENT(4, "Pagamento", true);



    private Integer code;

    private String description;

    private boolean positive;

    OperationType(int code, String description, boolean positive) {
        this.code = code;
        this.description = description;
        this.positive = positive;
    }

    @JsonValue
    public Integer getCode() {
        return code;
    }

    public String getDescription() {
        return description;
    }

    public boolean isPositive() {
        return positive;
    }

    public static OperationType getByCode(int code) {
        return Arrays.stream(values())
                .filter(item -> item.code == code)
                .findFirst()
                .orElseThrow(IllegalArgumentException::new);
    }
}
