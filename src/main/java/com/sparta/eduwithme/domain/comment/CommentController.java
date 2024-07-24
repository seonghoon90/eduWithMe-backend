package com.sparta.eduwithme.domain.comment;

import com.sparta.eduwithme.common.response.DataCommonResponse;
import com.sparta.eduwithme.domain.comment.dto.CommentRequestDto;
import com.sparta.eduwithme.domain.comment.dto.CommentResponseDto;
import com.sparta.eduwithme.security.UserDetailsImpl;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/question/{questionId}")
@RequiredArgsConstructor
public class CommentController {

    private static final int PAGE_SIZE = 5;

    private final CommentService commentService;

    @PostMapping("/comments")
    public ResponseEntity<DataCommonResponse<CommentResponseDto>> createComment(@PathVariable Long questionId,
                                                                                @Valid @RequestBody CommentRequestDto commentRequestDto,
                                                                                @AuthenticationPrincipal UserDetailsImpl userDetails) {
        CommentResponseDto responseDto = commentService.createComment(commentRequestDto, questionId,userDetails.getUser());
        DataCommonResponse<CommentResponseDto> response = new DataCommonResponse<>(201, "댓글 등록 되었습니다.", responseDto);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping("/comments")
    public ResponseEntity<DataCommonResponse<List<CommentResponseDto>>> getAllComments(@PathVariable Long questionId,
                                                                                       @RequestParam(value = "page", defaultValue = "0") int page) {
        List<CommentResponseDto> responseDtoList = commentService.getAllComments(questionId, page, PAGE_SIZE);
        DataCommonResponse<List<CommentResponseDto>> response = new DataCommonResponse<>(200, "댓글 조회에 성공 하였습니다.", responseDtoList);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
