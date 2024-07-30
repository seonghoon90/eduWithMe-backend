package com.sparta.eduwithme.domain.chat;

import com.sparta.eduwithme.domain.chat.dto.ChatMessage;
import com.sparta.eduwithme.security.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Slf4j
@Controller
@RequiredArgsConstructor
public class ChatController {

    private final ChatService chatService;

    @GetMapping("/chat-page")
    public String chatPage() {
        return "forward:/chat.html";
    }

    @MessageMapping("/chat/{roomId}") // prefix => /app
    @SendTo("/topic/room/{roomId}")
    public ChatMessage send(
                            @DestinationVariable Long roomId,
                            ChatMessage message)
    {
        chatService.testSendMessage(message.getContent());
        log.info("message : {}, roomId : {}", message.getContent(), roomId);
        return message;
    }

}
