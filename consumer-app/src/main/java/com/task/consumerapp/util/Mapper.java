package com.task.consumerapp.util;

import com.task.consumerapp.dto.MessageResponseDto;
import com.task.consumerapp.entity.Message;
import org.springframework.stereotype.Service;
import org.task.dto.KafkaMessage;

@Service
public class Mapper {

    public Message toMessageEntity(KafkaMessage message) {
        return Message.builder()
                .averageParagraphSize(message.averageParagraphSize())
                .averageParagraphProcessingTime(message.averageParagraphProcessingTime())
                .totalProcessingTime(message.totalProcessingTime())
                .mostFrequentWord(message.frequentWord())
                .build();
    }

    public MessageResponseDto toMessageResponseDto(Message message) {
        return new MessageResponseDto(
                message.getMostFrequentWord(),
                message.getAverageParagraphSize(),
                message.getAverageParagraphProcessingTime(),
                message.getTotalProcessingTime()
        );
    }
}
