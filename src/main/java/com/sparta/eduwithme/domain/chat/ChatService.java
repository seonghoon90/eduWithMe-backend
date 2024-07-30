package com.sparta.eduwithme.domain.chat;

import com.sparta.eduwithme.domain.chat.dto.ChatMessage;
import com.sparta.eduwithme.domain.chat.entity.Chat;
import com.sparta.eduwithme.domain.room.RoomService;
import com.sparta.eduwithme.domain.room.entity.Room;
import com.sparta.eduwithme.domain.user.UserRepository;
import com.sparta.eduwithme.domain.user.UserService;
import com.sparta.eduwithme.domain.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ChatService {

    private final ChatRepository chatRepository;
    private final RoomService roomService;
    private final UserService userService;

//    public void sendMessage(Long roomId, ChatMessage message, User user) {
//        Room room = roomService.findById(roomId);
//        Chat chat = Chat.builder().message(message).room(room).user(user).build();
//        chatRepository.save(chat);
//    }

    public void testSendMessage(String content) {
        Long roomId = 1L;
        User user = userService.findById(1L);
        Room room = roomService.findById(roomId);
        Chat chat = Chat.builder().content(content).room(room).user(user).build();
        chatRepository.save(chat);
    }
}
