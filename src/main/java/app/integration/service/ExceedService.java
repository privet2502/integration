package app.integration.service;


import app.integration.models.entity.TransactionEntity;

public interface ExceedService {

    /**
     * Метод вычисляет расход, учитывая прошлые расходы и лимиты
     */
    Double calculate(String currencyFrom, String currencyTo, Double amount, TransactionEntity entity);


}
