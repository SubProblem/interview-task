package com.task.processingapp.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public record ApiResponseDto(
        @JsonProperty("freq_word")
        String mostFrequentWord,
        @JsonProperty("avg_paragraph_size")
        double averageParagraphSize,
        @JsonProperty("avg_paragraph_processing_time")
        double averageParagraphProcessingTime,
        @JsonProperty("total_processing_time")
        double totalProcessingTime
) {
        public ApiResponseDto addTotalProcessingTime(double totalProcessingTime) {
                return new ApiResponseDto(
                        this.mostFrequentWord,
                        this.averageParagraphSize,
                        this.averageParagraphProcessingTime,
                        totalProcessingTime
                );
        }
}
