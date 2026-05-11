package com.fintrack.fin.transaction;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.YearMonth;

@RestController
@RequestMapping("/api/transactions")
@RequiredArgsConstructor
public class TransactionController {

    private final TransactionService transactionService;

    @GetMapping("/summary")
    public SummaryResponse summary(@RequestParam Long userId, @RequestParam int year, @RequestParam int month) {
        return transactionService.getSummary(userId, YearMonth.of(year, month));
    }

    @PostMapping
    public ResponseEntity<String> createTransaction(@RequestBody TransactionDto transactionDto) {
        transactionService.publishEvent(transactionDto);
        return ResponseEntity.accepted().build();
    }
}