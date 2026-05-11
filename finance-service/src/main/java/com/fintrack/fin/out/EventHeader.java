package com.fintrack.fin.out;

public record EventHeader(
        String eventId,
        String eventType,
        String source,
        long timestamp
) {
}
