package app.integration.models.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AskDto {
    boolean success;

    public static AskDto makeDefault(boolean success){
        return new AskDtoBuilder()
                .success(success)
                .build();
    }
}
