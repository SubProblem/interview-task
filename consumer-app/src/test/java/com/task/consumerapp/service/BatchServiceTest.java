package com.task.consumerapp.service;

import com.task.consumerapp.entity.Message;
import com.task.consumerapp.repository.MessageHistoryRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class BatchServiceTest {

    @Mock
    private MessageHistoryRepository messageHistoryRepository;

    @InjectMocks
    private BatchService batchService;

    @Test
    void saveMessages_ShouldSaveAndClearBuffer() {
        var message1 = Message.builder()
                .averageParagraphProcessingTime(10.0)
                .averageParagraphSize(15.0)
                .totalProcessingTime(3.0)
                .mostFrequentWord("Message 1")
                .build();

        var message2 = Message.builder()
                .averageParagraphProcessingTime(10.0)
                .averageParagraphSize(15.0)
                .totalProcessingTime(3.0)
                .mostFrequentWord("Message 2")
                .build();

        batchService.getBuffer().add(message1);
        batchService.getBuffer().add(message2);

        ArgumentCaptor<List<Message>> messagesCaptor = ArgumentCaptor.forClass(List.class);

        batchService.saveMessages();

        verify(messageHistoryRepository).saveAll(messagesCaptor.capture());

        var savedMessages = messagesCaptor.getValue();
        assertEquals(2, savedMessages.size());
        assertTrue(savedMessages.contains(message1));
        assertTrue(savedMessages.contains(message2));

        assertTrue(batchService.getBuffer().isEmpty());
    }

    @Test
    void saveMessages_ShouldNotInvokeRepository() {
        batchService.saveMessages();

        verify(messageHistoryRepository, never()).saveAll(any());
        assertTrue(batchService.getBuffer().isEmpty());
    }
}