package com.sparta.eduwithme.domain.comment;

import com.sparta.eduwithme.common.response.DataCommonResponse;
import com.sparta.eduwithme.common.response.StatusCommonResponse;
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

    //Comment 생성
    @PostMapping("/comments")
    public ResponseEntity<DataCommonResponse<CommentResponseDto>> createComment(@PathVariable Long questionId,
                                                                                @Valid @RequestBody CommentRequestDto commentRequestDto,
                                                                                @AuthenticationPrincipal UserDetailsImpl userDetails) {
        CommentResponseDto responseDto = commentService.createComment(commentRequestDto, questionId,userDetails.getUser());
        DataCommonResponse<CommentResponseDto> response = new DataCommonResponse<>(201, "댓글 등록 되었습니다.", responseDto);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    //Comment 조회
    @GetMapping("/comments")
    public ResponseEntity<DataCommonResponse<List<CommentResponseDto>>> getAllComments(@PathVariable Long questionId,
                                                                                       @RequestParam(value = "page", defaultValue = "0") int page) {
        List<CommentResponseDto> responseDtoList = commentService.getAllComments(questionId, page, PAGE_SIZE);
        DataCommonResponse<List<CommentResponseDto>> response = new DataCommonResponse<>(200, "댓글 조회에 성공 하였습니다.", responseDtoList);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    //Comment 수정
    @PutMapping("/comments/{commentId}")
    public ResponseEntity<DataCommonResponse<CommentResponseDto>> updateComment(@PathVariable Long questionId,
                                                                                @PathVariable Long commentId,
                                                                                @Valid @RequestBody CommentRequestDto commentRequestDto,
                                                                                @AuthenticationPrincipal UserDetailsImpl userDetails) {
        CommentResponseDto responseDto = commentService.updateComment(commentRequestDto, questionId, commentId, userDetails.getUser());
        DataCommonResponse<CommentResponseDto> response = new DataCommonResponse<>(200, "댓글 수정 되었습니다.", responseDto);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    //Comment 삭제
    @DeleteMapping("/comments/{commentId}")
    public ResponseEntity<StatusCommonResponse> deleteComment(@PathVariable Long questionId,
                                                              @PathVariable Long commentId,
                                                              @AuthenticationPrincipal UserDetailsImpl userDetails) {
        commentService.deleteComment(questionId, commentId, userDetails.getUser());
        StatusCommonResponse response = new StatusCommonResponse(204, "댓글 삭제가 완료되었습니다.");
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

}
