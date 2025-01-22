package com.task.consumerapp.config;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

import static org.task.constant.KafkaConstants.WORDS_PROCESSED_TOPIC;
import static org.task.constant.KafkaConstants.WORDS_PROCESSED_PARTITIONS;


@Configuration
public class TopicConfig {

    @Bean
    public NewTopic wordsProcessed() {
        return TopicBuilder
                .name(WORDS_PROCESSED_TOPIC)
                .partitions(WORDS_PROCESSED_PARTITIONS)
                .build();
    }
}
