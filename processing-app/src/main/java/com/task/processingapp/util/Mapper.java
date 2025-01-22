package com.task.processingapp.util;

import com.task.processingapp.dto.ApiResponseDto;
import org.springframework.stereotype.Service;
import org.task.dto.KafkaMessage;

@Service
public class Mapper {

    public KafkaMessage toKafkaMessage(ApiResponseDto responseDto) {
        return new KafkaMessage(
                responseDto.mostFrequentWord(),
                responseDto.averageParagraphSize(),
                responseDto.averageParagraphProcessingTime(),
                responseDto.totalProcessingTime()
        );
    }

    public ApiResponseDto toApiResponseDto(
            String mostFrequentWord,
            double averageParagraphSize,
            double averageParagraphProcessingTime
    ) {
        return new ApiResponseDto(
                mostFrequentWord,
                averageParagraphSize,
                averageParagraphProcessingTime,
                0.0
        );
    }
}
