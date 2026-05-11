package com.fintrack.fin.out;

import com.fintrack.fin.transaction.TransactionDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Component;

import java.util.concurrent.CompletableFuture;

@Slf4j
@Component
public class EventProducer {


    private final KafkaTemplate<String, TransactionEvent<TransactionData>> kafkaTemplate;

    public EventProducer(KafkaTemplate<String, TransactionEvent<TransactionData>> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void publishEvent(TransactionDto transactionDto) {
        TransactionEvent<TransactionData> transactionEvent =
                new TransactionEvent<>(
                        new EventHeader(
                                "evt-123",
                                EventType.CREATE.name(),
                                "transaction-service",
                                System.currentTimeMillis()
                        ),
                        new TransactionData(
                                transactionDto.userId(),
                                transactionDto.amount(),
                                transactionDto.category(),
                                transactionDto.type(),
                                transactionDto.description()
                        )
                );
        CompletableFuture<SendResult<String, TransactionEvent<TransactionData>>> future =
                kafkaTemplate.send("transaction-events", String.valueOf(transactionDto.userId()), transactionEvent);

        future.whenComplete((result, throwable) -> {
            if (throwable != null) {
                log.error("Failed {} ", throwable.getLocalizedMessage());
            } else {
                log.info("Sent to partition: {}, off-set: {}",
                        result.getRecordMetadata().partition()
                        , result.getRecordMetadata().offset());
            }
        });
    }
}
