package com.sparta.eduwithme.domain.chat;

import com.sparta.eduwithme.domain.chat.dto.ChatMessage;
import com.sparta.eduwithme.domain.chat.dto.ChatMessageResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
public class ChatController {

    private final ChatService chatService;

    @GetMapping("/room/{roomId}")
    public List<ChatMessageResponse> getChats(@PathVariable Long roomId) {
        return chatService.getRecentChats(roomId);
    }

    @MessageMapping("/chat/{roomId}")
    @SendTo("/topic/room/{roomId}")
    public ChatMessage send(
            @DestinationVariable Long roomId,
            ChatMessage message)
    {
        chatService.sendMessage(roomId, message);
        log.info("message : {}, roomId : {}", message.getContent(), roomId);
        log.info("sender : {}", message.getSender());
        return message;
    }

}