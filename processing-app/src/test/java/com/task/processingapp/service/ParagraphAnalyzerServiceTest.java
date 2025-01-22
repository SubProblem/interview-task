package com.task.processingapp.service;

import com.task.processingapp.dto.ProcessResult;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@Slf4j
@ExtendWith(MockitoExtension.class)
class ParagraphAnalyzerServiceTest {

    private ParagraphAnalyzerService paragraphAnalyzerService;

    @BeforeEach
    void setup() {
        paragraphAnalyzerService = new ParagraphAnalyzerService();
        ReflectionTestUtils.setField(paragraphAnalyzerService, "self", paragraphAnalyzerService);
    }

    @Test
    void transformResponse_WithValidParagraphs() {
        String input = "<p>Paragraph Analyzer</p><p>Random Text</p>";

        String[] result = paragraphAnalyzerService.transformResponse(input);

        assertEquals(2, result.length);
        assertArrayEquals(new String[]{"Paragraph Analyzer", "Random Text" }, result);
    }

    @Test
    void transformResponse_WithEmptyParagraphs() {
        String paragraph = "<p></p><p>Valid paragraph</p><p>  </p>";

        String[] result = paragraphAnalyzerService.transformResponse(paragraph);

        assertEquals(1, result.length);
        assertEquals("Valid paragraph", result[0]);
    }

    @Test
    void processParagraph_ShouldUpdateWordFrequency() {
        String paragraph = "Hello hello World";

        ProcessResult result = paragraphAnalyzerService.processParagraph(paragraph);

        assertEquals("hello", paragraphAnalyzerService.findMostFrequentWord());
    }

    @Test
    void findMostFrequentWord_WithEmptyMap() {
        String result = paragraphAnalyzerService.findMostFrequentWord();

        assertEquals("", result);
    }

    @Test
    void calculateParagraphAverageSize_WithValidParagraphs() {
        String[] paragraphs = {"paragraph 1", "paragraph 2", "paragraph 3"};

        double result = paragraphAnalyzerService.calculateParagraphAverageSize(paragraphs);

        assertEquals(2.0, result);
    }

    @Test
    void calculateParagraphAverageSize_WithEmptyParagraphs() {
        String[] paragraphs = {"", "", ""};

        double result = paragraphAnalyzerService.calculateParagraphAverageSize(paragraphs);

        assertEquals(0.0, result);
    }

    @Test
    void calculateAverageParagraphProcessingTime_ShouldReturnCorrectAverage() {
        double totalTime = 100.0;
        int totalParagraphs = 5;

        double result = paragraphAnalyzerService.calculateAverageParagraphProcessingTime(totalTime, totalParagraphs);

        assertEquals(20, result);
    }

    @Test
    void getTotalProcessingTime_ShouldSumAllProcessingTimes() {
        String[] paragraphs = {"paragraph 1", "paragraph 2", "paragraph 3"};

        ParagraphAnalyzerService serviceSpy = spy(paragraphAnalyzerService);
        ReflectionTestUtils.setField(serviceSpy, "self", serviceSpy);

        doReturn(new ProcessResult(10.0)).when(serviceSpy).processParagraph("paragraph 1");
        doReturn(new ProcessResult(10.0)).when(serviceSpy).processParagraph("paragraph 2");
        doReturn(new ProcessResult(10.0)).when(serviceSpy).processParagraph("paragraph 3");

        double result = serviceSpy.getTotalProcessingTime(paragraphs);

        assertEquals(30.0, result);
    }

}