package app.integration.repository;

import app.integration.models.entity.LimitExceedTransaction;
import java.util.List;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LimitExceedTransactionRepository extends JpaRepository<LimitExceedTransaction, Long> {

    List<LimitExceedTransaction> findByAccountNumber(String accountNumber);

    List<LimitExceedTransaction> findByAccountNumber(String accountNumber, Pageable page);
}
