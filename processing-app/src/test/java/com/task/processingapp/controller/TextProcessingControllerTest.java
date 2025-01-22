package com.task.processingapp.controller;

import com.task.processingapp.config.BaseTest;
import com.task.processingapp.dto.ApiResponseDto;
import com.task.processingapp.service.TextProcessingService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.web.util.UriComponentsBuilder;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

@Slf4j
class TextProcessingControllerTest extends BaseTest {

    @MockitoBean
    private TextProcessingService textProcessingService;

    @Test
    void generateRandomText_ShouldReturnDtos() {
        int paragraphNumber = 3;
        String paragraphType = "short";

        var dto = new ApiResponseDto(
                "Message 1",
                15.0,
                10.0,
                3.0
        );

        when(textProcessingService.sendAndReturnResponse(paragraphNumber, paragraphType))
                .thenReturn(dto);

        var url = UriComponentsBuilder.fromPath("/api/v1/betvictor/text")
                .queryParam("p", paragraphNumber)
                .queryParam("l", paragraphType)
                .toUriString();

        var response = restTemplate.getForEntity(
                url,
                ApiResponseDto.class,
                dto
        );

        log.info("Response: {}", response.getBody());
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertTrue(response.hasBody());
    }

    @Test
    void generateRandomText_IncorrectParagraphNumber() {
        int paragraphNumber = -1;
        String paragraphType = "short";

        var url = UriComponentsBuilder.fromPath("/api/v1/betvictor/text")
                .queryParam("p", paragraphNumber)
                .queryParam("l", paragraphType)
                .toUriString();

        var response = restTemplate.getForEntity(
                url,
                ApiResponseDto.class
        );

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

    @Test
    void generateRandomText_IncorrectParagraphType() {
        int paragraphNumber = 3;
        String paragraphType = "test";

        var url = UriComponentsBuilder.fromPath("/api/v1/betvictor/text")
                .queryParam("p", paragraphNumber)
                .queryParam("l", paragraphType)
                .toUriString();

        var response = restTemplate.getForEntity(
                url,
                ApiResponseDto.class
        );

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }
}