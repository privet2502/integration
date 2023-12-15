package app.integration.models.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class CurrencyConversionResponse implements Serializable {

    @JsonProperty("symbol")
    private String symbol;

    @JsonProperty("rate")
    private double rate;

    @JsonProperty("amount")
    private double amount;

    @JsonProperty("timestamp")
    private long timestamp;

}