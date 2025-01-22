package com.task.consumerapp.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public record MessageResponseDto(
        @JsonProperty("freq_word")
        String frequentWord,
        @JsonProperty("avg_paragraph_size")
        double averageParagraphSize,
        @JsonProperty("avg_paragraph_processing_time")
        double averageParagraphProcessingTime,
        @JsonProperty("total_processing_time")
        double totalProcessingTime
) {
}
