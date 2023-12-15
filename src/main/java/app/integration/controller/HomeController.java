package app.integration.controller;

import app.integration.models.entity.Account;
import app.integration.models.enumiration.ExpenseCategoryDto;
import app.integration.models.enumiration.FieldNames;
import app.integration.repository.AccountRepository;
import app.integration.repository.CurrencyRepository;
import app.integration.repository.TransactionRepository;
import app.integration.service.LimitService;
import app.integration.service.TransactionService;
import app.integration.util.PageUtil;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/ui")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class HomeController {

    TransactionRepository transactionRepository;
    CurrencyRepository currencyRepository;
    TransactionService transactionService;
    AccountRepository accountRepository;
    LimitService limitService;


    @GetMapping("/home")
    public String home(Model model,
            @RequestParam(value = "offset", defaultValue = "0", required = false) int offset,
            @RequestParam(value = "limit", defaultValue = "5", required = false) int limit,
            @RequestParam(value = "selectedType", defaultValue = "", required = false) String selectedType,
            @RequestParam(value = "direction", defaultValue = "asc", required = false) String direction,
            @RequestParam(value = "field", required = false) String field,
            @RequestParam(value = "account_num", defaultValue = "", required = false) String accountNum,
            @RequestParam(value = "acc", defaultValue = "", required = false) String acc,
            @RequestParam(value = "name", defaultValue = "", required = false) String name
    ) {
        Direction direction1 = direction.equalsIgnoreCase(
                Direction.ASC.name()
        ) ? Direction.ASC : Direction.DESC;

        Sort sort = Sort.unsorted();
        if (field != null && !field.isEmpty() && !field.equals("null")) {
            sort = Sort.by(direction1, field);
        }
        long count = selectedType.isEmpty() ? transactionRepository.count() : transactionRepository.countByExpenseCategoryDto(Enum.valueOf(ExpenseCategoryDto.class, selectedType));
        model.addAttribute("transactions",
                transactionService.findAllBy(
                        PageRequest.of(offset == 0 ? 0 : offset - 1, limit, sort),
                        selectedType, accountNum)
        );
        model.addAttribute("field", field);
        model.addAttribute("selectedType", selectedType);
        model.addAttribute("direction", direction);
        model.addAttribute("account_num", accountNum);

        model.addAttribute("currency", currencyRepository.findAll());
        model.addAttribute("pagination", PageUtil.pagination(limit, offset, count));
        model.addAttribute("pagination_count", count);
        model.addAttribute("types", ExpenseCategoryDto.values());
        model.addAttribute("fields", FieldNames.values());
        model.addAttribute("directions", Direction.values());

        model.addAttribute("acc", acc);
        model.addAttribute("name", name);

        return "index";
    }

    @PostMapping("/account")
    public String createAccount(@RequestParam("name") String name) {
        if (name.isEmpty()) {
            return "redirect:/ui/home?invalid=true";
        }
        Account account = accountRepository.save(new Account(name));
        limitService.setLimitForNewAccount(account);
        return "redirect:/ui/home?acc=" + account.getAccountNumber() + "&name=" + name;
    }
}
