package app.integration.service.impl;

import jakarta.transaction.Transactional;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.Optional;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import app.integration.factory.LimitFactory;
import app.integration.models.entity.Currency;
import app.integration.models.entity.Limit;
import app.integration.models.entity.TransactionEntity;
import app.integration.repository.CurrencyRepository;
import app.integration.repository.LimitRepository;
import app.integration.repository.TransactionRepository;
import app.integration.service.ExceedService;

@Slf4j
@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ExceedServiceImpl implements ExceedService {

    CurrencyRepository currencyRepository;
    LimitRepository limitRepository;
    TransactionRepository transactionRepository;
    LimitFactory limitFactory;

    @Override
    @Transactional
    public Double calculate(String currencyFrom, String currencyTo, Double amount, TransactionEntity entity) {

        Double result = calculateExceed(currencyFrom, currencyTo, amount);

        Optional<Limit> optionalLimit = limitRepository
                .findFirstByAccount_AccountNumberAndActiveIsTrueAndExpenseCategoryDto(
                        entity.getAccount().getAccountNumber(),
                        entity.getExpenseCategoryDto()
                );

        Limit limit;

        if (optionalLimit.isEmpty()) {
            limit = limitFactory.createInstance(entity.getExpenseCategoryDto().name());
            limit.setActive(true);
            limit.setExpenseCategoryDto(entity.getExpenseCategoryDto());
            limit.setCreatedAt(LocalDateTime.now());
            limit.setAmount(1000L);
            limit.setAccount(entity.getAccount());
            limit = limitRepository.save(limit);
        } else {
            limit = optionalLimit.get();
        }

        Optional<TransactionEntity> lastTransaction = transactionRepository
                .findFirstByAccount_AccountNumberAndExpenseCategoryDtoOrderByDatetimeDesc(
                        entity.getAccount().getAccountNumber(),
                        entity.getExpenseCategoryDto()
                );

        if (lastTransaction.isEmpty()) {
            entity.setLimitExceeded(!(limit.getAmount() - result > 0));
            entity.setLimit(limit);
            entity.setRemainder(limit.getAmount() - result);
            return result;
        }

        double newReminder = lastTransaction.get().getRemainder() - result;

        //логика при изменении лимита
        if (!Objects.equals(lastTransaction.get().getLimit().getAmount(), limit.getAmount())) {
            float difference = lastTransaction.get().getLimit().getAmount() - limit.getAmount();
            if (difference > 0) {
                newReminder -= difference;
            } else if (difference < 0) {
                newReminder += difference * -1;
            }
        }

        entity.setLimitExceeded(newReminder < 0);

        entity.setLimit(limit);
        entity.setRemainder(newReminder);

        return result;
    }


    public Double calculateExceed(String currencyFrom, String currencyTo, Double amount) {

        Optional<Currency> currency = currencyRepository.findBySymbol(currencyTo + "/" + currencyFrom);

        if (currency.isEmpty()) {
            throw new RuntimeException("invalid Symbol");
        }

        double result = amount / currency.get().getAmount();

        try {
            DecimalFormat df = new DecimalFormat("#.##");
            result = df.parse(df.format(result)).doubleValue();
        } catch (ParseException e) {
            log.info("Error was thrown with num: {}", amount / currency.get().getAmount());
            throw new RuntimeException(e);
        }

        return result;
    }
}
