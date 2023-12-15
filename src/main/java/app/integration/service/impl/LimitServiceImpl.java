package app.integration.service.impl;

import app.integration.exception.BadRequestException;
import app.integration.factory.LimitFactory;
import app.integration.models.entity.Account;
import app.integration.models.entity.Limit;
import app.integration.models.enumiration.ExpenseCategoryDto;
import app.integration.repository.AccountRepository;
import app.integration.repository.LimitRepository;
import app.integration.service.LimitService;
import java.time.LocalDateTime;
import java.util.Optional;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class LimitServiceImpl implements LimitService {

    LimitRepository limitRepository;
    AccountRepository accountRepository;
    LimitFactory limitFactory;

    public Limit addLimit(String accountNum, Long limit, String expenseCategory) {
        Optional<Account> account = accountRepository.findAccountByAccountNumber(accountNum);

        if (account.isEmpty()) {
            throw new BadRequestException("Invalid account number");
        }

        Optional<Limit> oldActiveLimit = limitRepository
                .findFirstByAccount_AccountNumberAndActiveIsTrueAndExpenseCategoryDto(
                        accountNum,
                        Enum.valueOf(
                                ExpenseCategoryDto.class,
                                expenseCategory
                        )
                );

        if (oldActiveLimit.isPresent()) {
            oldActiveLimit.get().setActive(false);
            limitRepository.save(oldActiveLimit.get());
        }

        Limit entity = limitFactory.createInstance(
                Enum.valueOf(
                                ExpenseCategoryDto.class,
                                expenseCategory
                        )
                        .name()
        );
        entity.setAccount(account.get());
        entity.setCreatedAt(LocalDateTime.now());
        entity.setExpenseCategoryDto(
                Enum.valueOf(
                        ExpenseCategoryDto.class,
                        expenseCategory
                ));
        entity.setAmount(limit);
        entity.setActive(true);

        return limitRepository.save(entity);
    }

    public Limit getLimit(String accountNum, String expenseCategory) {
        Optional<Account> account = accountRepository.findAccountByAccountNumber(accountNum);

        if (account.isEmpty()) {
            throw new BadRequestException("Invalid account number");
        }
        Optional<Limit> limit = limitRepository
                .findFirstByAccount_AccountNumberAndActiveIsTrueAndExpenseCategoryDto(
                        accountNum,
                        Enum.valueOf(
                                ExpenseCategoryDto.class,
                                expenseCategory
                        )
                );
        System.out.println("limit = " + limit);
        System.out.println("limit = " + limit.isPresent());
        if (limit.isPresent()) {
            return limit.get();
        } else {
            throw new BadRequestException("Limit is not present");
        }

    }

    @Override
    public void setLimitForNewAccount(Account account) {
        limitRepository.save(createLimitInstance(account, ExpenseCategoryDto.SERVICE));
        limitRepository.save(createLimitInstance(account, ExpenseCategoryDto.GOOD));
    }

    @Override
    public void deleteAccount(String accountNum) throws BadRequestException {
        if (accountNum == null || accountNum.isEmpty()){
            throw new BadRequestException("Account num cannot be null");
        }

        Optional<Account> account = accountRepository.findAccountByAccountNumber(accountNum);

        if (account.isEmpty()){
            throw new BadRequestException(String.format("Account with num %s not found", accountNum));
        }
        accountRepository.delete(account.get());
    }

    Limit createLimitInstance(Account account, ExpenseCategoryDto category) {
        Limit limit = limitFactory.createInstance(category.name());
        limit.setAccount(account);
        limit.setActive(true);
        limit.setAmount(1000L);
        limit.setCreatedAt(LocalDateTime.now());
        limit.setExpenseCategoryDto(category);
        return limit;
    }


}
