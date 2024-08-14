package com.sparta.eduwithme.domain.gpt.dto;

import lombok.Getter;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class GeminiRequest {
    private List<Content> contents;

    public GeminiRequest(String prompt) {
        this.contents = List.of(new Content(new Parts(prompt)));
    }

    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Content {
        private Parts parts;
    }
}