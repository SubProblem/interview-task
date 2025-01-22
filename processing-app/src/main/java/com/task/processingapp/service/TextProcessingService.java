package com.task.processingapp.service;

import com.task.processingapp.dto.ApiResponseDto;
import com.task.processingapp.producer.MessageProducer;
import com.task.processingapp.util.Mapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class TextProcessingService {

    private final TextGenerationService textGenerationService;
    private final MessageProducer messageProducer;
    private final Mapper mapper;

    public ApiResponseDto sendAndReturnResponse(Integer paragraphNumber, String lengthType) {
        var response = textGenerationService.generateRandomText(paragraphNumber, lengthType);
        var kafkaMessage = mapper.toKafkaMessage(response);

        messageProducer.sendMessage(kafkaMessage);
        return response;
    }

}
