package app.integration.repository;

import app.integration.models.entity.Account;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountRepository extends JpaRepository<Account, Long> {


    Optional<Account> findAccountByAccountNumber(String accountNumber);
}
