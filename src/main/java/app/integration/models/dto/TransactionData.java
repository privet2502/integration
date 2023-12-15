package app.integration.models.dto;

import java.time.LocalDateTime;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.FieldDefaults;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@FieldDefaults(level = AccessLevel.PRIVATE)
public class TransactionData {
    Long id;
    String counterpartyAccountNumber;
    String toAccountNumber;
    String currency;
    Double sum;
    Double remainder;
    String expenseCategory;
    LocalDateTime datetime;
    Double limitAmount;
    LocalDateTime limitCreatedAt;
    String limitCurrency;
}
