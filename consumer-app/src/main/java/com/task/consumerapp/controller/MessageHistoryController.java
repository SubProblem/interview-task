package com.task.consumerapp.controller;

import com.task.consumerapp.dto.MessageResponseDto;
import com.task.consumerapp.service.MessageHistoryService;
import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequiredArgsConstructor
@Validated
@RestController
@RequestMapping("/api/v1")
class MessageHistoryController {

    private final MessageHistoryService messageHistoryService;

    @GetMapping("/betvictor/history")
    ResponseEntity<List<MessageResponseDto>> getMessages(
            @RequestParam(defaultValue = "0")
            @Min(value = 0, message = "Parameter 'page' must be positive number")
            Integer page) {
        var response = messageHistoryService.getMessages(page);
        return ResponseEntity.status(HttpStatus.OK)
                .body(response);
    }
}
