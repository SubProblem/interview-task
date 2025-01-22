package com.task.consumerapp.service;

import com.task.consumerapp.dto.MessageResponseDto;
import com.task.consumerapp.repository.MessageHistoryRepository;
import com.task.consumerapp.util.Mapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;


@Slf4j
@RequiredArgsConstructor
@Service
public class MessageHistoryService {

    private final MessageHistoryRepository messageHistoryRepository;
    private final Mapper mapper;

    public List<MessageResponseDto> getMessages(Integer page) {

        var pageable = PageRequest.of(page, 10, Sort.by("id").descending());

        return messageHistoryRepository.findAll(pageable).getContent()
                .stream()
                .map(mapper::toMessageResponseDto)
                .toList();
    }
}
