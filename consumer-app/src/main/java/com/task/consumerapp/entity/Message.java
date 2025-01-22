package com.task.consumerapp.entity;

import jakarta.persistence.*;
import lombok.*;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

@Entity
public class Message {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "most_freq_word")
    private String mostFrequentWord;

    @Column(name = "avg_paragraph_size")
    private Double averageParagraphSize;

    @Column(name = "avg_paragraph_processing_time")
    private Double averageParagraphProcessingTime;

    @Column(name = "total_processing_time")
    private Double totalProcessingTime;

}
