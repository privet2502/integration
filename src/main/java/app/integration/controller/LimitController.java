package app.integration.controller;

import app.integration.models.enumiration.ExpenseCategoryDto;
import app.integration.service.LimitService;
import jakarta.jws.WebParam.Mode;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/ui/limits")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class LimitController {

    private LimitService limitService;

    @GetMapping
    public String limit(Model model,
            @RequestParam(value = "account_num", defaultValue = "", required = false) String accountNum,
            @RequestParam(value = "limit", defaultValue = "0", required = false) String limit
    ) {
        model.addAttribute("types", ExpenseCategoryDto.values());
        model.addAttribute("account_num", accountNum);
        model.addAttribute("limit", limit);

        return "limit";
    }

    @PostMapping
    public String addLimit(
            @RequestParam(value = "account_num") String accountNum,
            @RequestParam(value = "limit", defaultValue = "0", required = false) Long limit,
            @RequestParam(value = "category") String expenseCategory,
            Model model
    ) {
        model.addAttribute("types", ExpenseCategoryDto.values());
        model.addAttribute("account_num", accountNum);
        try {
            if (limit == 0 && !accountNum.isEmpty()){
                model.addAttribute("limitEntity", limitService.getLimit(accountNum, expenseCategory));
                return "limit";
            }
            model.addAttribute("limitEntity", limitService.addLimit(accountNum, limit, expenseCategory));
        } catch (Exception e){
            return "redirect:/ui/limits?invalid=true";
        }

        return "limit";
    }

    @PostMapping("/delete")
    public String deleteAccount(@RequestParam(value = "account_num") String accountNum, Model model) throws Exception {
        model.addAttribute("types", ExpenseCategoryDto.values());
        try {
            limitService.deleteAccount(accountNum);
            model.addAttribute("deleted", "Успешно удалено");
            return "limit";
        } catch (Exception e){
            return "redirect:/ui/limits?delete_error=true";

        }
    }
}
