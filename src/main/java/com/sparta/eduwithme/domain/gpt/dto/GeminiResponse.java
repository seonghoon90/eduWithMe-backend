package com.sparta.eduwithme.domain.gpt.dto;

import lombok.Getter;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Builder;

import java.util.List;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class GeminiResponse {
    private List<Candidate> candidates;

    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Candidate {
        private Content content;
    }

    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Content {
        private List<Part> parts;
    }

    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Part {
        private String text;
    }
}