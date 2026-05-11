package com.fintrack.fin.out;

import java.math.BigDecimal;

public record TransactionData(Long userId,
                              BigDecimal amount,
                              String category,
                              String type,
                              String description
) {
}
