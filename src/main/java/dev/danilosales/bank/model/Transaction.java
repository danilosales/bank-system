package dev.danilosales.bank.model;


import com.vladmihalcea.hibernate.type.money.MonetaryAmountType;
import dev.danilosales.bank.enums.OperationType;
import dev.danilosales.bank.model.converters.OperationTypeConverter;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Columns;
import org.hibernate.annotations.TypeDef;

import javax.money.MonetaryAmount;
import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Objects;

@Entity(name = "transactions")
@Getter @Setter
@RequiredArgsConstructor
@TypeDef(
        typeClass = MonetaryAmountType.class,
        defaultForType = MonetaryAmount.class
)
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "transactions_seq")
    @SequenceGenerator(name = "transactions_seq", sequenceName = "transactions_seq", allocationSize = 1)
    private Long id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "account_id")
    private Account account;

    @Convert(converter = OperationTypeConverter.class)
    @Column(name = "operation_type")
    private OperationType operationType;

    @Columns(columns = {
            @Column(name = "amount"),
            @Column(name = "currency")
    })
    private MonetaryAmount value;

    @Column(name = "event_date")
    private LocalDateTime eventDate = LocalDateTime.now();

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Transaction that = (Transaction) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
