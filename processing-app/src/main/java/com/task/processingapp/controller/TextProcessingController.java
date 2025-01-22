package com.task.processingapp.controller;

import com.task.processingapp.dto.ApiResponseDto;
import com.task.processingapp.service.TextProcessingService;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Pattern;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@Validated
@RestController
@RequestMapping("/api/v1")
class TextProcessingController {

    private final TextProcessingService textProcessingService;

    @GetMapping("/betvictor/text")
    ResponseEntity<ApiResponseDto> generateRandomText(
            @RequestParam(name = "p")  // required by default
            @Min(value = 1, message = "Parameter 'p' must be greater than 0")
            Integer p,

            @RequestParam(name = "l")  // required by default
            @Pattern(regexp = "^(short|medium|long|verylong)$",
                    message = "Parameter 'l' must be one of: short, medium, long, verylong")
            String l
    ) {
        var response = textProcessingService.sendAndReturnResponse(p, l);
        return ResponseEntity.status(HttpStatus.OK)
                .body(response);
    }
}
