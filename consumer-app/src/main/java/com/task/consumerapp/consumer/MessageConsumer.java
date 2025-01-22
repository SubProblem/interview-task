package com.task.consumerapp.consumer;

import com.task.consumerapp.repository.MessageHistoryRepository;
import com.task.consumerapp.service.BatchService;
import com.task.consumerapp.util.Mapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import org.task.dto.KafkaMessage;

import static com.task.consumerapp.service.BatchService.BATCH_SIZE;
import static org.task.constant.KafkaConstants.GROUP_ID;
import static org.task.constant.KafkaConstants.WORDS_PROCESSED_TOPIC;

@Slf4j
@RequiredArgsConstructor
@Service
public class MessageConsumer {

    private final MessageHistoryRepository messageHistoryRepository;
    private final BatchService batchService;
    private final Mapper mapper;


    @KafkaListener(topics = WORDS_PROCESSED_TOPIC, groupId = GROUP_ID)
    public synchronized void listenMessages(KafkaMessage message) {
        log.info("Message Received: {}", message);

        var messageEntity = mapper.toMessageEntity(message);
        batchService.getBuffer().add(messageEntity);

        if (batchService.getBuffer().size() >= BATCH_SIZE) {
            batchService.saveMessages();
        }
    }

}
