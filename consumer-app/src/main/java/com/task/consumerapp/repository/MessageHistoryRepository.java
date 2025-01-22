package com.task.consumerapp.repository;

import com.task.consumerapp.entity.Message;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MessageHistoryRepository extends JpaRepository<Message, Integer> {
}
