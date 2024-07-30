package com.sparta.eduwithme.domain.chat;

import com.sparta.eduwithme.domain.chat.entity.Chat;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ChatRepository extends JpaRepository<Chat, Long> {
}
