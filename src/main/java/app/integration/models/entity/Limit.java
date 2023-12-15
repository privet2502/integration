package app.integration.models.entity;

import app.integration.models.enumiration.ExpenseCategoryDto;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public abstract class Limit {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    Long id;

    Long amount;

    LocalDateTime createdAt;

    @ManyToOne
    @JoinColumn(name = "limit_to_account")
    Account account;

    boolean active;

    @Column(name = "expense_category")
    @Enumerated(EnumType.STRING)
    ExpenseCategoryDto expenseCategoryDto;

}
