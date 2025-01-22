package com.task.consumerapp.controller;

import com.task.consumerapp.config.BaseTest;
import com.task.consumerapp.dto.MessageResponseDto;
import com.task.consumerapp.service.MessageHistoryService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@Slf4j
class MessageHistoryControllerTest extends BaseTest {

    @MockitoBean
    private MessageHistoryService messageHistoryService;

    @Test
    void getMessages_ShouldReturnDtos() {
        int pageNumber = 0;

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

        var dtos = List.of(dto1, dto2);

        when(messageHistoryService.getMessages(pageNumber))
                .thenReturn(dtos);

        var url = UriComponentsBuilder.fromPath("/api/v1/betvictor/history")
                .queryParam("page", pageNumber)
                .toUriString();

        var response = restTemplate.getForEntity(
                url,
                MessageResponseDto[].class,
                dtos
        );

        log.info("Response: {}", response.getBody());
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertTrue(response.hasBody());
        assertEquals(2, response.getBody().length);
    }

    @Test
    void getMessages_IncorrectPageNumber() {
        int pageNumber = -1;

        var url = UriComponentsBuilder.fromPath("/api/v1/betvictor/history")
                .queryParam("page", pageNumber)
                .toUriString();

        var response = restTemplate.getForEntity(
                url,
                MessageResponseDto.class
        );

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }
}