package app.integration.config;

import app.integration.models.generated.TransactionRequest;
import app.integration.models.generated.TransactionResponse;
import app.integration.service.TransactionService;
import jakarta.xml.bind.JAXBElement;
import javax.xml.namespace.QName;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

@Slf4j
@Endpoint
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class TransactionEndpoint {

    public final static String NAMESPACE_URI = "http://iitu.kz/transaction";
    TransactionService transactionService;

    @PayloadRoot(namespace = NAMESPACE_URI,
            localPart = "TransactionRequest")
    @ResponsePayload
    public JAXBElement<TransactionResponse> getTransactionRequest(@RequestPayload JAXBElement<TransactionRequest> request) {
        log.info("Income request: {}", request);
        try {
            return createJaxbElement(transactionService.save(request.getValue()), TransactionResponse.class);
        }catch (Exception e){
            log.error("Error happened during woring of application:", e);
            throw new RuntimeException(e);
        }
    }

    private <T> JAXBElement<T> createJaxbElement(T object, Class<T> clazz) {
        return new JAXBElement<>(new QName(clazz.getSimpleName()), clazz, object);
    }





}
