package com.task.consumerapp.consumer;

import com.task.consumerapp.config.BaseTest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.task.dto.KafkaMessage;


import static org.task.constant.KafkaConstants.WORDS_PROCESSED_TOPIC;

class MessageConsumerTest extends BaseTest {

    @Autowired
    private KafkaTemplate<String, Object> kafkaTemplate;

    @Test
    void consumeMessage_ShouldConsumeSuccessfully() throws InterruptedException {
        var kafkaMessage = new KafkaMessage(
                "test",
                30.0,
                30.0,
                30.0
        );
        kafkaTemplate.send(WORDS_PROCESSED_TOPIC, kafkaMessage.frequentWord(), kafkaMessage);
        Thread.sleep(1000); // Wait for the consumer to process the message
    }
}