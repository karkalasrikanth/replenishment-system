package com.fintrack.fin.transaction;

import com.fintrack.fin.out.EventProducer;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.YearMonth;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TransactionService {

    private final TransactionRepository transactionRepository;
    private final EventProducer eventProducer;

    public SummaryResponse getSummary(Long userId, YearMonth month) {

        LocalDateTime start = month.atDay(1).atStartOfDay();
        LocalDateTime end = month.atEndOfMonth().atTime(23, 59, 59);

        List<Object[]> results = transactionRepository.getSummary(userId, start, end);

        BigDecimal income = BigDecimal.ZERO;
        BigDecimal expense = BigDecimal.ZERO;

        for (Object[] row : results) {
            String type = (String) row[0];
            BigDecimal amount = (BigDecimal) row[1];

            if ("INCOME".equalsIgnoreCase(type)) {
                income = amount;
            } else {
                expense = amount;
            }
        }

        return new SummaryResponse(income, expense);
    }

    public void publishEvent(TransactionDto transactionDto) {
        eventProducer.publishEvent(transactionDto);
    }
}
