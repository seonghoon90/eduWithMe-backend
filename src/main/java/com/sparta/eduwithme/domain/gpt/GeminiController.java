package com.sparta.eduwithme.domain.gpt;

import com.sparta.eduwithme.domain.gpt.dto.PromptRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/gemini")
public class GeminiController {

    private static final Logger logger = LoggerFactory.getLogger(GeminiController.class);
    private final GeminiService geminiService;

    @Autowired
    public GeminiController(GeminiService geminiService) {
        this.geminiService = geminiService;
    }

    @PostMapping("/generate")
    public ResponseEntity<String> generateContent(@RequestBody PromptRequest request) {
        try {
            String response = geminiService.getContents(request.getPrompt());
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            logger.error("Error generating content", e);
            return ResponseEntity.badRequest().body("Error: " + e.getMessage());
        }
    }
}