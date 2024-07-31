package com.sparta.eduwithme.domain.chat;

import com.sparta.eduwithme.domain.chat.entity.Chat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ChatRepository extends JpaRepository<Chat, Long> {
    List<Chat> findTop20ByRoomIdOrderByCreatedAtAsc(Long roomId); // limit을 20으로 설정
}
