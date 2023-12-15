package app.integration.models.entity;

import app.integration.models.enumiration.CurrencyTypeDto;
import app.integration.models.enumiration.ExpenseCategoryDto;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import java.time.Instant;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Immutable;
import org.hibernate.annotations.Subselect;

/**
 * Mapping for DB view
 */
@Getter
@Setter
@Entity
@Immutable
@Subselect(value = """
        SELECT t.id,
               ca.account_number,
               ta.account_number AS counter_party_account_number,
               t.currency,
               t.sum,
               t.remainder,
               t.expense_category,
               t.datetime,
               CASE
                   WHEN t.expense_category::text = 'GOOD'::text THEN l.amount
                   ELSE sl.amount
                   END           AS limit_sum,
               CASE
                   WHEN t.expense_category::text = 'GOOD'::text THEN l.created_at
                   ELSE sl.created_at
                   END           AS limit_datetime,
               ta.currency       AS limit_currency
        FROM transactions t
                 LEFT JOIN good_limit l ON l.id = t.transaction_to_limit
                 LEFT JOIN account ca ON t.transaction_to_account = ca.id
                 LEFT JOIN account ta ON t.transaction_to_account = ta.id
                 LEFT JOIN service_limit sl ON t.transaction_to_limit = sl.id
        WHERE t.limit_exceeded = true
        ORDER BY t.expense_category, l.created_at
        """)
public class LimitExceedTransaction {

    @Id
    private Long id;

    @JsonProperty("account_number")
    private String accountNumber;

    @JsonProperty("counterparty_account_number")
    private String counterPartyAccountNumber;

    @Enumerated(EnumType.STRING)
    private CurrencyTypeDto currency;

    private Double sum;

    private Double remainder;

    @Enumerated(EnumType.STRING)
    private ExpenseCategoryDto expenseCategory;

    private Instant datetime;

    @JsonProperty("limit_sum")
    private Long limitSum;

    @JsonProperty("limit_datetime")
    private Instant limitDatetime;

    @Enumerated(EnumType.STRING)
    @JsonProperty("limit_currency")
    private CurrencyTypeDto limitCurrency;

}