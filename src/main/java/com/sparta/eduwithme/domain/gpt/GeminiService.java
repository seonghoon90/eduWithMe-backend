package com.sparta.eduwithme.domain.gpt;

import com.sparta.eduwithme.domain.gpt.dto.GeminiRequest;
import com.sparta.eduwithme.domain.gpt.dto.GeminiResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.time.Duration;

@Service
public class GeminiService {
    private static final Logger logger = LoggerFactory.getLogger(GeminiService.class);

    @Value("${gemini.api.url}")
    private String apiUrl;

    @Value("${gemini.api.key}")
    private String apiKey;

    private final RestTemplate restTemplate;

    public GeminiService(RestTemplateBuilder restTemplateBuilder) {
        this.restTemplate = restTemplateBuilder
                .setConnectTimeout(Duration.ofSeconds(10))
                .setReadTimeout(Duration.ofSeconds(30))
                .build();
    }

    public String getContents(String prompt) {
        try {
            GeminiRequest request = new GeminiRequest(prompt);
            String fullUrl = apiUrl + "?key=" + apiKey;

            ResponseEntity<GeminiResponse> responseEntity = restTemplate.postForEntity(fullUrl, request, GeminiResponse.class);

            if (responseEntity.getStatusCode() == HttpStatus.OK && responseEntity.getBody() != null) {
                GeminiResponse response = responseEntity.getBody();
                if (response.getCandidates() != null && !response.getCandidates().isEmpty()) {
                    return response.getCandidates().get(0).getContent().getParts().get(0).getText();
                }
            }

            throw new RuntimeException("No valid response from Gemini API");
        } catch (RestClientException e) {
            logger.error("Error calling Gemini API", e);
            throw new RuntimeException("Error calling Gemini API: " + e.getMessage(), e);
        }
    }
}