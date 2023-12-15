package app.integration.models.entity;

import app.integration.models.enumiration.CurrencyTypeDto;
import app.integration.models.enumiration.ExpenseCategoryDto;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.time.LocalDateTime;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.FieldDefaults;

@Entity
@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "transactions")
@FieldDefaults(level = AccessLevel.PRIVATE)
@ToString
public class TransactionEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    Double sum;

    @Enumerated(EnumType.STRING)
    CurrencyTypeDto currency;

    @Column(name = "expense_category")
    @Enumerated(EnumType.STRING)
    ExpenseCategoryDto expenseCategoryDto;

    LocalDateTime datetime;

    @ManyToOne
    @JoinColumn(name = "transaction_to_account")
    Account account;

    @ManyToOne
    @JoinColumn(name = "transaction_to_counterparty_account")
    Account counterpartyAccount;

    @ManyToOne
    @JoinColumn(name = "transaction_to_limit")
    Limit limit;

    boolean limitExceeded;

    Double remainder;
}
