package com.task.consumerapp.service;

import com.task.consumerapp.entity.Message;
import com.task.consumerapp.repository.MessageHistoryRepository;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;


@Slf4j
@RequiredArgsConstructor
@Service
public class BatchService {

    private final MessageHistoryRepository messageHistoryRepository;

    // In-memory buffering is used for this solution.
    // However, in production, a proper storage system should be implemented.
    @Getter
    private final List<Message> buffer = new ArrayList<>();
    public static final int BATCH_SIZE = 100;

    @Scheduled(fixedRate = 60000) // Fallback to save every 60 seconds
    public synchronized void saveMessages() {
        if (!buffer.isEmpty()) {
            log.info("Saving {} messages to the database", buffer.size());
            messageHistoryRepository.saveAll(new ArrayList<>(buffer));
            buffer.clear();
        }
    }
}
