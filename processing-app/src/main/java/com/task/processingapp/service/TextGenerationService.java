package com.task.processingapp.service;

import com.task.processingapp.config.Timed;
import com.task.processingapp.dto.ApiResponseDto;
import com.task.processingapp.util.Mapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

@Slf4j
@RequiredArgsConstructor
@Service
public class TextGenerationService {

    private final RestClient restClient;
    private final ParagraphAnalyzerService paragraphAnalyzerService;
    private final Mapper mapper;


    @Timed
    public ApiResponseDto generateRandomText(Integer paragraphNumber, String lengthType) {

        String paragraphs = fetchApi(paragraphNumber, lengthType);

        log.info("Paragraphs: {}", paragraphs);

        String[] modifiedParagraphs = paragraphAnalyzerService.transformResponse(paragraphs);

        double averageParagraphSize = paragraphAnalyzerService.calculateParagraphAverageSize(modifiedParagraphs);

        double totalParagraphProcessingTime = paragraphAnalyzerService.getTotalProcessingTime(modifiedParagraphs);

        double averageParagraphProcessingTime = paragraphAnalyzerService.calculateAverageParagraphProcessingTime(totalParagraphProcessingTime, modifiedParagraphs.length);

        String mostFrequentWord = paragraphAnalyzerService.findMostFrequentWord();

        return mapper.toApiResponseDto(
                mostFrequentWord,
                averageParagraphSize,
                averageParagraphProcessingTime
        );
    }

    private String fetchApi(Integer paragraphNumber, String lengthType) {
        return restClient
                .get()
                .uri("/paragraphs/{p}/{l}", paragraphNumber, lengthType)
                .retrieve()
                .body(String.class);
    }
}
