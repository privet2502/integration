package app.integration.controller.api;

import app.integration.models.dto.AskDto;
import app.integration.models.entity.LimitExceedTransaction;
import app.integration.repository.LimitExceedTransactionRepository;
import app.integration.service.impl.LimitServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
import java.util.List;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class LimitRestController {

    LimitServiceImpl limitService;
    LimitExceedTransactionRepository limitExceedTransactionRepository;

    @PatchMapping("/limits/{account_num}")
    @Operation(summary = "Позволяет изменять лимит для конкретной категории расхода")
    public ResponseEntity<AskDto> setLimitForUser(
            @PathVariable String account_num,
            @RequestParam(value = "limit") Long limit,
            @RequestParam(value = "category") String expenseCategory
    ) {
        limitService.addLimit(account_num, limit, expenseCategory);
        return ResponseEntity.ok(AskDto.makeDefault(true));
    }


    /**
     * Возвращает список транзакций, превысивших лимит
     */
    @GetMapping("/limits/{account_num}")
    @Operation(summary = "Возвращает список транзакций, превысивших лимит")
    public ResponseEntity<List<LimitExceedTransaction>> getTransactionsThatOverLimits(
            @PathVariable String account_num,
            @RequestParam(name = "offset", required = false, defaultValue = "0") int offset,
            @RequestParam(name = "limit", required = false, defaultValue = "5") int limit,
            @RequestParam(name = "sort", required = false, defaultValue = "asc") String sort) {
        try {
            Direction direction;
            switch (sort) {
                case "desc":
                    direction = Direction.DESC;
                    break;
                default:
                    direction = Direction.ASC;
                    break;
            }
            return ResponseEntity.ok(limitExceedTransactionRepository.findByAccountNumber(
                            account_num,
                            PageRequest.of(offset, limit, Sort.by(direction, "sum"))
                    )
            );
        } catch (Exception e) {
            System.out.println(e);
            return null;
        }
    }
}
