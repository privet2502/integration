package app.integration.task;

import app.integration.models.entity.Currency;
import app.integration.models.enumiration.CurrencyTypeDto;
import jakarta.annotation.PostConstruct;
import java.sql.Date;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Calendar;
import java.util.Optional;
import java.util.Set;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import app.integration.models.dto.CurrencyConversionResponse;
import app.integration.repository.CurrencyRepository;

@Service
@Slf4j
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class Scheduler {

    private static final String API_URL = "https://api.twelvedata.com/currency_conversion?";
    private static final String API_KEY = "&apikey=d40d991adde3460d9f6b20779ced3a69";
    public Set<String> updateList = Set.of(
            String.join("/", CurrencyTypeDto.USD.name(), CurrencyTypeDto.KZT.name()),
            String.join("/", CurrencyTypeDto.USD.name(), CurrencyTypeDto.RUB.name())
    );

    CurrencyRepository currencyRepository;
    RestTemplate restTemplate;


    @Scheduled(cron = "0 0 9 * * *")
    @PostConstruct
    public void cron() {
        log.info("Cron is started");
        for (String currencyType : updateList) {
            updateCurrency(currencyType);
        }
    }

    /**
     * Проверяет наличие в базе валют, дату их добавления
     * если день недели выходные дни(1,7), то будут использоваться данные с пятницы(6)
     */
    public void updateCurrency(String currencyName) {

        Optional<Currency> currency = currencyRepository.findBySymbol(currencyName);

        if (currency.isEmpty()) {
            log.info("Cycle of adding currency: {}", currencyName);
            saveCurrency(new Currency(), returnUrl(currencyName), currencyName);
            return;
        }

        if (currency.get().getUpdatedAt().toLocalDate().equals(LocalDate.now())) {
            return;
        }

        if (isTodayWeekend(currency.get())) {
            return;
        }

        saveCurrency(currency.get(), returnUrl(currency.get().getSymbol()), currencyName);
    }

    private static boolean isTodayWeekend(Currency currency) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(Date.valueOf(LocalDate.now()));
        int number = calendar.get(Calendar.DAY_OF_WEEK);

        if (number == 1 || number == 7) {
            calendar.setTime(Date.valueOf(currency.getUpdatedAt().toLocalDate()));
            int num = calendar.get(Calendar.DAY_OF_WEEK);
            return num == 6;
        }
        return false;
    }


    public void saveCurrency(Currency currency, String url, String currencyName) {

        var response = sendRequest(String.valueOf(url));

        if (currency.getUpdatedAt() == null) {
            currency.setSymbol(currencyName);
        }

        currency.setUpdatedAt(LocalDateTime.now());
        currency.setAmount(response.getRate());

        currencyRepository.save(currency);
    }

    public CurrencyConversionResponse sendRequest(String url) {
        log.info("Sending request for API with url; {}", url);

        ResponseEntity<CurrencyConversionResponse> response = restTemplate.exchange(
                url, HttpMethod.GET,
                HttpEntity.EMPTY, CurrencyConversionResponse.class
        );

        if (response.getStatusCode().isError()) {
            throw new RuntimeException(String.format("Not found currency on stock exchange: %s", url));
        }

        log.info("conversionResponse: {}", response.getBody());

        return response.getBody();
    }


    public String returnUrl(String symbol) {
        return API_URL
                + "symbol="
                + symbol
                + API_KEY;
    }

}
