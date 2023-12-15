package app.integration.service.impl;

import app.integration.exception.BadRequestException;
import app.integration.models.generated.CurrencyType;
import app.integration.models.generated.ExpenseCategory;
import app.integration.models.generated.Transaction;
import app.integration.models.generated.TransactionRequest;
import app.integration.models.generated.TransactionResponse;
import jakarta.transaction.Transactional;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;
import java.util.Optional;
import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;
import app.integration.models.entity.Account;
import app.integration.models.entity.TransactionEntity;
import app.integration.models.enumiration.ExpenseCategoryDto;
import app.integration.repository.AccountRepository;
import app.integration.repository.TransactionRepository;
import app.integration.service.ExceedService;
import app.integration.service.TransactionService;

@Slf4j
@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class TransactionServiceImpl implements TransactionService {

    TransactionRepository transactionRepository;
    AccountRepository accountRepository;
    ExceedService exceedService;

    @Override
    @Transactional
    public TransactionResponse save(TransactionRequest request) throws DatatypeConfigurationException {

        Transaction transaction = request.getExceed();

        Optional<Account> accountFrom = accountRepository.findAccountByAccountNumber(transaction.getAccountFrom());
        if (accountFrom.isEmpty()) {
            throw new BadRequestException(String.format(
                    "Account with account number: %s not found",
                    transaction.getAccountFrom()
            ));
        }

        Optional<Account> accountTo = accountRepository.findAccountByAccountNumber(transaction.getAccountTo());
        if (accountTo.isEmpty()) {
            throw new RuntimeException(String.format(
                    "Account with counterparty account number: %s not found",
                    transaction.getAccountTo()
            ));
        }

        TransactionEntity entity = new TransactionEntity();

        entity.setAccount(accountFrom.get());
        entity.setCounterpartyAccount(accountTo.get());
        entity.setExpenseCategoryDto(
                Enum.valueOf(
                        ExpenseCategoryDto.class,
                        transaction.getExpenseCategory().name()
                )
        );
        entity.setDatetime(
                LocalDateTime.now()
                //todo: вернуть на место: toLocalDateTime(transaction.getDatetime())
        );
        entity.setCurrency(accountFrom.get().getCurrency());
        entity.setSum(
                exceedService.calculate(
                        transaction.getCurrencyShortname().name(),
                        accountFrom.get().getCurrency().name(),
                        transaction.getSum(), entity
                )
        );

        transactionRepository.save(entity);

        return TransactionResponse.builder()
                .limitCurrencyShortname(
                        Enum.valueOf(
                                CurrencyType.class,
                                accountFrom.get().getCurrency().name()
                        )
                )
                .limitDatetime(toXMLGregorianCalendar(entity.getLimit().getCreatedAt()))
                .limitSum(entity.getLimit().getAmount())
                .datetime(
                        toXMLGregorianCalendar(LocalDateTime.now())
//                        todo: вернуть transaction.getDatetime()
                )
                .accountFrom(accountFrom.get().getAccountNumber())
                .accountTo(accountTo.get().getAccountNumber())
                .sum(entity.getSum())
                .expenseCategory(
                        Enum.valueOf(
                                ExpenseCategory.class,
                                entity.getExpenseCategoryDto().name()
                        )
                )
                .currencyShortname(
                        Enum.valueOf(
                                CurrencyType.class,
                                accountFrom.get().getCurrency().name()
                        )
                )
                .build();
    }

    // Преобразование LocalDateTime в XMLGregorianCalendar
    public static XMLGregorianCalendar toXMLGregorianCalendar(LocalDateTime localDateTime)
            throws DatatypeConfigurationException {
        return DatatypeFactory.newInstance().newXMLGregorianCalendar(
                String.valueOf(localDateTime));
    }


    public static LocalDateTime toLocalDateTime(XMLGregorianCalendar calendar) {
        return calendar.toGregorianCalendar().toZonedDateTime().withZoneSameInstant(ZoneId.systemDefault())
                .toLocalDateTime();
    }


    public List<TransactionEntity> findAllBy(PageRequest page, String selectedType, String accountNum) {
        System.out.println("page = " + page.getSort());
        System.out.println("page= " + page);
        if (accountNum.isEmpty() && selectedType.isEmpty()) {
            return transactionRepository.findAll(page).toList();
        } else if (!accountNum.isEmpty() && !selectedType.isEmpty()) {
            return transactionRepository.findByAccount_AccountNumberContainingIgnoreCaseAndExpenseCategoryDto(
                    accountNum,
                    Enum.valueOf(ExpenseCategoryDto.class, selectedType),
                    page
            );
        } else if (accountNum.isEmpty()) {
            return transactionRepository.findByExpenseCategoryDto(
                    Enum.valueOf(ExpenseCategoryDto.class, selectedType),
                    page
            );
        } else {
            return transactionRepository.findByAccount_AccountNumberContainingIgnoreCase(
                    accountNum,
                    page
            );
        }
    }
}
