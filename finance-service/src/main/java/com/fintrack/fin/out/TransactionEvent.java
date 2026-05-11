package com.fintrack.fin.out;

public record TransactionEvent<T>(
        EventHeader header,
        T data
) {
}
