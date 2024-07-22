package com.sparta.eduwithme.domain.question;

import com.sparta.eduwithme.common.response.DataCommonResponse;
import com.sparta.eduwithme.domain.question.dto.QuestionRequestDTO;
import com.sparta.eduwithme.domain.question.dto.QuestionResponseDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class QuestionController {

    private final QuestionService questionService;

    //Question 생성
    @PostMapping("/rooms/{roomId}/question")
    public ResponseEntity<DataCommonResponse<QuestionResponseDTO>> createQuestion(@PathVariable Long roomId, @RequestBody QuestionRequestDTO requestDTO) {
        QuestionResponseDTO responseDTO = questionService.createQuestion(roomId, requestDTO);
        DataCommonResponse<QuestionResponseDTO> response = new DataCommonResponse<>(201, "문제 생성이 완료 되었습니다.", responseDTO);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }


}
