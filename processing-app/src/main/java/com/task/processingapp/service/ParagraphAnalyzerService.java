package com.task.processingapp.service;

import com.task.processingapp.config.Timed;
import com.task.processingapp.dto.ProcessResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

@Service
public class ParagraphAnalyzerService {

    private final Map<String, Integer> wordFrequency = new HashMap<>();

    @Autowired
    private ParagraphAnalyzerService self;


    public String[] transformResponse(String paragraphs) {
        return Arrays.stream(paragraphs.split("</p>"))
                .map(p -> p.replaceAll("<p>", "").trim())
                .filter(p -> !p.isEmpty())
                .toArray(String[]::new);
    }

    @Timed
    public ProcessResult processParagraph(String paragraph) {
        Arrays.stream(paragraph.toLowerCase().split("\\W+"))
                .filter(word -> !word.isEmpty())
                .forEach(word -> wordFrequency.merge(word, 1, Integer::sum));

        return new ProcessResult(0);
    }

    public String findMostFrequentWord() {
        return wordFrequency.entrySet()
                .stream()
                .max(Map.Entry.comparingByValue())
                .map(Map.Entry::getKey)
                .orElse("");

    }

    public double calculateParagraphAverageSize(String[] paragraphs) {
        return Arrays.stream(paragraphs)
                .filter(s -> !s.isEmpty())
                .mapToInt(p -> p.split("\\s+").length)
                .average()
                .orElse(0.0);
    }

    public double calculateAverageParagraphProcessingTime(double totalTime, int totalParagraphs) {
        return totalTime / totalParagraphs;
    }

    public double getTotalProcessingTime(String[] modifiedParagraphs) {
        return Arrays.stream(modifiedParagraphs)
                .map(self::processParagraph)
                .mapToDouble(ProcessResult::processingTime)
                .sum();
    }

}
