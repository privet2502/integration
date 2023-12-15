package app.integration.repository;

import app.integration.models.entity.TransactionEntity;
import app.integration.models.enumiration.ExpenseCategoryDto;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface TransactionRepository extends JpaRepository<TransactionEntity, Long> {

    Optional<TransactionEntity> findFirstByAccount_AccountNumberAndExpenseCategoryDtoOrderByDatetimeDesc(
            String account_accountNumber, ExpenseCategoryDto expenseCategoryDto);

    List<TransactionEntity> findByAccount_AccountNumberContainingIgnoreCaseAndExpenseCategoryDto(String accountNumber,
            ExpenseCategoryDto expenseCategoryDto, Pageable pageable);

    List<TransactionEntity> findByAccount_AccountNumberContainingIgnoreCase(String account_accountNumber, Pageable pageable);

    List<TransactionEntity> findByExpenseCategoryDto(ExpenseCategoryDto expenseCategoryDto, Pageable pageable);

    long countByExpenseCategoryDto(ExpenseCategoryDto expenseCategoryDto);
}
