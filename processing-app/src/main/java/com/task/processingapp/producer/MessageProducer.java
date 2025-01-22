package com.task.processingapp.producer;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.task.dto.KafkaMessage;

import static org.task.constant.KafkaConstants.WORDS_PROCESSED_TOPIC;

@Slf4j
@RequiredArgsConstructor
@Service
public class MessageProducer {

    private final KafkaTemplate<String, Object> kafkaTemplate;

    public void sendMessage(KafkaMessage message) {
        log.info("Message {}", message);
        kafkaTemplate.send(WORDS_PROCESSED_TOPIC, message.frequentWord(), message);
    }
}
