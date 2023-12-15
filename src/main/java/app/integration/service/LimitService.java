package app.integration.service;

import app.integration.models.entity.Account;
import app.integration.models.entity.Limit;
import app.integration.models.enumiration.ExpenseCategoryDto;

public interface LimitService {

    Limit addLimit(String accountNum, Long limit, String expenseCategory);

    Limit getLimit(String accountNum, String expenseCategory);

    void setLimitForNewAccount(Account account);

    void deleteAccount(String accountNum) throws Exception;
}
