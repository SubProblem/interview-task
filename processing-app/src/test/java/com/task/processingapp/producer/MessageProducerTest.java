package com.task.processingapp.producer;

import com.task.processingapp.config.BaseTest;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.task.dto.KafkaMessage;

@Slf4j
class MessageProducerTest extends BaseTest {

    @Autowired
    private MessageProducer messageProducer;

    @Test
    public void sendMessage_ShouldSentSuccessfully() {
        var kafkaMessage = new KafkaMessage(
                "test",
                30.0,
                30.0,
                30.0
        );

        messageProducer.sendMessage(kafkaMessage);
    }
}