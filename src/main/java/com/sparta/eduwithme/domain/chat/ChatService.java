package com.sparta.eduwithme.domain.chat;

import com.sparta.eduwithme.common.exception.CustomException;
import com.sparta.eduwithme.common.exception.ErrorCode;
import com.sparta.eduwithme.domain.chat.dto.ChatMessage;
import com.sparta.eduwithme.domain.chat.dto.ChatMessageResponse;
import com.sparta.eduwithme.domain.chat.entity.Chat;
import com.sparta.eduwithme.domain.room.RoomService;
import com.sparta.eduwithme.domain.room.entity.Room;
import com.sparta.eduwithme.domain.user.UserRepository;
import com.sparta.eduwithme.domain.user.entity.User;
import com.vane.badwordfiltering.BadWordFiltering;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ChatService {

    private final ChatRepository chatRepository;
    private final RoomService roomService;
    private final UserRepository userRepository;
    private final BadWordFiltering badWordFiltering = new BadWordFiltering();

    public void sendMessage(Long roomId, ChatMessage message) {
        Room room = roomService.findById(roomId);
        User user = userRepository.findByNickName(message.getSender()).orElseThrow(
                () -> new CustomException(ErrorCode.USER_NOT_FOUND)
        );

        Chat chat = Chat.builder().content(message.getContent()).room(room).user(user).build();

        if (badWordFiltering.check(message.getContent())) {
            throw new CustomException(ErrorCode.PROFANITY_DETECTED);
        }

        chatRepository.save(chat);
    }

    public List<ChatMessageResponse> getRecentChats(Long roomId) {
        List<Chat> chats = chatRepository.findTop20ByRoomIdOrderByCreatedAtAsc(roomId);

        return chats.stream()
                .map(chat -> new ChatMessageResponse(chat.getContent(), chat.getUser().getNickName(), chat.getCreatedAt()))
                .collect(Collectors.toList());
    }
}