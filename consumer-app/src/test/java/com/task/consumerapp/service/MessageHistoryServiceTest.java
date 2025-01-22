package com.task.consumerapp.service;

import com.task.consumerapp.dto.MessageResponseDto;
import com.task.consumerapp.entity.Message;
import com.task.consumerapp.repository.MessageHistoryRepository;
import com.task.consumerapp.util.Mapper;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@Slf4j
@ExtendWith(MockitoExtension.class)
class MessageHistoryServiceTest {

    @Mock
    private MessageHistoryRepository messageHistoryRepository;

    @Mock
    private Mapper mapper;

    @InjectMocks
    private MessageHistoryService messageHistoryService;

    @Test
    void getMessages_ShouldReturnMappedResponse() {
        int pageNumber = 0;
        int pageSize = 10;

        var message1 = Message.builder()
                .id(1)
                .averageParagraphProcessingTime(10.0)
                .averageParagraphSize(15.0)
                .totalProcessingTime(3.0)
                .mostFrequentWord("Message 1")
                .build();

        var message2 = Message.builder()
                .id(2)
                .averageParagraphProcessingTime(10.0)
                .averageParagraphSize(15.0)
                .totalProcessingTime(3.0)
                .mostFrequentWord("Message 2")
                .build();

        var messages = List.of(message1, message2);

        var dto1 = new MessageResponseDto(
                "Message 1",
                15.0,
                10.0,
                3.0
        );

        var dto2 = new MessageResponseDto(
                "Message 1",
                15.0,
                10.0,
                3.0
        );

        var expectedDtos = List.of(dto1, dto2);

        var pageRequest = PageRequest.of(pageNumber, pageSize, Sort.by(Sort.Direction.DESC, "id"));
        Page<Message> messagePage = new PageImpl<>(messages, PageRequest.of(pageNumber, pageSize), messages.size());

        when(messageHistoryRepository.findAll(eq(pageRequest)))
                .thenReturn(messagePage);

        when(mapper.toMessageResponseDto(message1)).thenReturn(dto1);
        when(mapper.toMessageResponseDto(message2)).thenReturn(dto2);

        var result = messageHistoryService.getMessages(pageNumber);

        assertNotNull(result);
        assertEquals(expectedDtos, result);
        verify(messageHistoryRepository).findAll(pageRequest);
        verify(mapper, times(2)).toMessageResponseDto(any(Message.class));
    }

    @Test
    void getMessages_ShouldReturnEmptyList() {
        int pageNumber = 0;
        Page<Message> emptyPage = new PageImpl<>(List.of());
        when(messageHistoryRepository.findAll(any(PageRequest.class)))
                .thenReturn(emptyPage);

        var result = messageHistoryService.getMessages(pageNumber);

        assertNotNull(result);
        assertTrue(result.isEmpty());
        verify(messageHistoryRepository).findAll(any(PageRequest.class));
        verify(mapper, never()).toMessageResponseDto(any(Message.class));
    }
}