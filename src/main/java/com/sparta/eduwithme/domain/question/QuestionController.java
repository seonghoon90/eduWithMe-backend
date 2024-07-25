package com.sparta.eduwithme.domain.question;

import com.sparta.eduwithme.common.response.DataCommonResponse;
import com.sparta.eduwithme.common.response.StatusCommonResponse;
import com.sparta.eduwithme.domain.question.dto.*;
import com.sparta.eduwithme.security.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class QuestionController {

    private static final int PAGE_SIZE = 5;

    private final QuestionService questionService;

    //Question 생성
    @PostMapping("/rooms/{roomId}/question")
    public ResponseEntity<DataCommonResponse<QuestionResponseDto>> createQuestion(@PathVariable Long roomId,
                                                                                  @RequestBody QuestionRequestDto requestDto) {
        QuestionResponseDto responseDTO = questionService.createQuestion(roomId, requestDto);
        DataCommonResponse<QuestionResponseDto> response = new DataCommonResponse<>(201, "문제 생성이 완료 되었습니다.", responseDTO);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    //Question 전체 조회
    @GetMapping("/rooms/{roomId}/question")
    public ResponseEntity<DataCommonResponse<List<QuestionResponseDto>>> getAllQuestion(@PathVariable Long roomId,
                                                                                        @RequestParam(value = "page", defaultValue = "0") int page) {
        List<QuestionResponseDto> responseDtoList = questionService.getAllQuestion(roomId, page, PAGE_SIZE);
        DataCommonResponse<List<QuestionResponseDto>> response = new DataCommonResponse<>(200, "문제 전체 조회를 성공하였습니다.", responseDtoList);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    //Question 제목 조회
    @GetMapping("/search/rooms/{roomId}/question/title")
    public ResponseEntity<DataCommonResponse<List<QuestionTitleDto>>> searchQuestionByTitle(@PathVariable Long roomId,
                                                                                            @RequestParam String keyword,
                                                                                            @RequestParam(defaultValue = "0") int page) {
        List<QuestionTitleDto> responseDtoList = questionService.searchQuestionByTitle(roomId, keyword, page, PAGE_SIZE);
        DataCommonResponse<List<QuestionTitleDto>> response = new DataCommonResponse<>(200, "문제 검색을 성공 하였습니다.", responseDtoList);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    //Question 수정
    @PutMapping("/rooms/{roomId}/question/{questionId}")
    public ResponseEntity<DataCommonResponse<QuestionResponseDto>> updateQuestion(@PathVariable Long roomId,
                                                                                  @PathVariable Long questionId,
                                                                                  @RequestBody QuestionUpdateRequestDto requestDto) {
        QuestionResponseDto responseDto = questionService.updateQuestion(roomId, questionId, requestDto);
        DataCommonResponse<QuestionResponseDto> response = new DataCommonResponse<>(200, "문제 수정이 완료되었습니다.", responseDto);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    //Question 삭제
    @DeleteMapping("/rooms/{roomId}/question/{questionId}")
    public ResponseEntity<StatusCommonResponse> deleteQuestion(@PathVariable Long roomId,
                                                               @PathVariable Long questionId) {
        questionService.deleteQuestion(roomId, questionId);
        StatusCommonResponse response = new StatusCommonResponse(204, "문제 삭제가 완료되었습니다.");
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    //Question 단건 조회
    @GetMapping("/rooms/{roomId}/question/{questionId}")
    public ResponseEntity<DataCommonResponse<QuestionDetailDto>> getQuestion(@PathVariable Long roomId,
                                                                             @PathVariable Long questionId) {
        QuestionDetailDto questionDetail = questionService.getQuestionDetail(roomId, questionId);
        DataCommonResponse<QuestionDetailDto> response = new DataCommonResponse<>(200, "문제 조회에 성공했습니다.", questionDetail);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    //Question 답변 제출
    @PostMapping("/rooms/{roomId}/question/{questionId}/submit")
    public ResponseEntity<DataCommonResponse<AnswerResultDto>> submitAnswer(@PathVariable Long roomId,
                                                                            @PathVariable Long questionId,
                                                                            @RequestBody AnswerSubmissionDto submissionDto,
                                                                            @AuthenticationPrincipal UserDetailsImpl userDetails) {
        AnswerResultDto result = questionService.submitAnswer(roomId, questionId, submissionDto, userDetails.getUser());
        DataCommonResponse<AnswerResultDto> response = new DataCommonResponse<>(200, "답변이 제출되었습니다.", result);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
    

}