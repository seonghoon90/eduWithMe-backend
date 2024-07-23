package com.sparta.eduwithme.domain.question;

import com.sparta.eduwithme.common.response.DataCommonResponse;
import com.sparta.eduwithme.domain.question.dto.QuestionRequestDTO;
import com.sparta.eduwithme.domain.question.dto.QuestionResponseDTO;
import com.sparta.eduwithme.domain.question.dto.QuestionTitleDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
public class QuestionController {

    private static final int PAGE_SIZE = 5;

    private final QuestionService questionService;

    //Question 생성
    @PostMapping("/rooms/{roomId}/question")
    public ResponseEntity<DataCommonResponse<QuestionResponseDTO>> createQuestion(@PathVariable Long roomId,
                                                                                  @RequestBody QuestionRequestDTO requestDTO) {
        QuestionResponseDTO responseDTO = questionService.createQuestion(roomId, requestDTO);
        DataCommonResponse<QuestionResponseDTO> response = new DataCommonResponse<>(201, "문제 생성이 완료 되었습니다.", responseDTO);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    //Question 전체 조회
    @GetMapping("/rooms/{roomId}/question")
    public ResponseEntity<DataCommonResponse<List<QuestionResponseDTO>>> getAllQuestion(@PathVariable Long roomId,
                                                                                        @RequestParam(value = "page", defaultValue = "0") int page) {
        List<QuestionResponseDTO> responseDTOList = questionService.getAllQuestion(roomId, page, PAGE_SIZE);
        DataCommonResponse<List<QuestionResponseDTO>> response = new DataCommonResponse<>(200, "문제 전체 조회를 성공하였습니다.", responseDTOList);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    //Question 제목 조회
    @GetMapping("/search/rooms/{roomId}/question/title")
    public ResponseEntity<DataCommonResponse<List<QuestionTitleDTO>>> searchQuestionByTitle(@PathVariable Long roomId,
                                                                                            @RequestParam String keyword,
                                                                                            @RequestParam(defaultValue = "0") int page) {

        try {
            List<QuestionTitleDTO> responseDTOList = questionService.searchQuestionByTitle(roomId, keyword, page, PAGE_SIZE);
            String message = responseDTOList.isEmpty() ? "검색 결과가 없습니다." : "문제 제목 검색을 성공하였습니다.";
            return ResponseEntity.ok(new DataCommonResponse<>(200, message, responseDTOList));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(new DataCommonResponse<>(400, e.getMessage(), Collections.emptyList()));
        }
    }
}