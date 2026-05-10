package com.fintrack.fin.budget;


import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.YearMonth;
import java.util.List;

@RestController
@RequestMapping("/api/budgets")
@RequiredArgsConstructor
public class BudgetController {
    private final BudgetService budgetService;

    @GetMapping("/check")
    public List<BudgetAlert> check(@RequestParam Long userId,
                                   @RequestParam int year,
                                   @RequestParam int month
    ) {
        return budgetService.checkBudget(userId, YearMonth.of(year, month));
    }

    @GetMapping("/health")
    public List<BudgetHealthResponse> health(
            @RequestParam Long userId,
            @RequestParam int year,
            @RequestParam int month
    ) {
        return budgetService.getBudgetHealth(userId, YearMonth.of(year, month));
    }
}
