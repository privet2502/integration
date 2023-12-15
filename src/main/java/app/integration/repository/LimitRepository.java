package app.integration.repository;

import app.integration.models.entity.Limit;
import app.integration.models.enumiration.ExpenseCategoryDto;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LimitRepository extends JpaRepository<Limit, Long> {

    Optional<Limit> findFirstByAccount_AccountNumberAndActiveIsTrueAndExpenseCategoryDto(String account_accountNum,
            ExpenseCategoryDto expenseCategoryDto);

}
