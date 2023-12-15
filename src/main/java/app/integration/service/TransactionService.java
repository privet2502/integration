package app.integration.service;

import app.integration.models.entity.TransactionEntity;
import app.integration.models.generated.TransactionRequest;
import app.integration.models.generated.TransactionResponse;
import java.util.List;
import javax.xml.datatype.DatatypeConfigurationException;
import org.springframework.data.domain.PageRequest;

public interface TransactionService {

    /**
     * Сохраняем пришедшую транзакцию
     */
    TransactionResponse save(TransactionRequest transaction) throws DatatypeConfigurationException;

    List<TransactionEntity> findAllBy(PageRequest page, String selectedType, String accountNum);
}
