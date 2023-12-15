package app.integration.models.entity;

import app.integration.models.enumiration.CurrencyTypeDto;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import java.util.List;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.ToString.Exclude;
import lombok.experimental.FieldDefaults;
import app.integration.models.dto.AccountNumberGenerator;
import app.integration.models.dto.GenerateNumbers;

@Entity
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
@ToString
public class Account implements GenerateNumbers {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Column(unique = true, name = "account_number", nullable = false, length = 10)
    String accountNumber;

    String fullName;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "account")
    @Exclude
    List<TransactionEntity> transactionEntities;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "account", fetch = FetchType.LAZY)
    @Exclude
    List<Limit> limits;

    @Enumerated(EnumType.STRING)
    CurrencyTypeDto currency;

    public CurrencyTypeDto getCurrency() {
        return currency;
    }

    @Override
    public String generate() {
        return AccountNumberGenerator.getInstance().generate("4400", 10);
    }

    public void setDefaultAccountNum() {
        this.accountNumber = generate();
    }

    public Account(String fullName) {
        this.fullName = fullName;
        this.accountNumber = generate();
        this.currency = CurrencyTypeDto.USD;
    }
}
