package org.task.dto;

public record KafkaMessage(
        String frequentWord,
        double averageParagraphSize,
        double averageParagraphProcessingTime,
        double totalProcessingTime
) {
}
