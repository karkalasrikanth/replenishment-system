package com.fintrack.fin.transaction;

import java.math.BigDecimal;

public record TransactionDto(Long userId,
                             BigDecimal amount,
                             String category,
                             String type,
                             String description
) {
}
