package com.sparta.eduwithme.domain.comment;

import com.sparta.eduwithme.common.response.DataCommonResponse;
import com.sparta.eduwithme.common.response.StatusCommonResponse;
import com.sparta.eduwithme.domain.comment.dto.CommentRequestDto;
import com.sparta.eduwithme.domain.comment.dto.CommentResponseDto;
import com.sparta.eduwithme.security.UserDetailsImpl;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/question/{questionId}")
@RequiredArgsConstructor
public class CommentController {

    private static final int PAGE_SIZE =10;

    private final CommentService commentService;

    //Comment 생성
    @Operation(summary = "createComment", description = "댓글 생성 기능입니다.")
    @PostMapping("/comments")
    public ResponseEntity<DataCommonResponse<CommentResponseDto>> createComment(@PathVariable Long questionId,
                                                                                @Valid @RequestBody CommentRequestDto commentRequestDto,
                                                                                @AuthenticationPrincipal UserDetailsImpl userDetails) {
        CommentResponseDto responseDto = commentService.createComment(commentRequestDto, questionId,userDetails.getUser());
        DataCommonResponse<CommentResponseDto> response = new DataCommonResponse<>(201, "댓글 등록 되었습니다.", responseDto);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    //Comment 조회
    @Operation(summary = "getAllComments", description = "댓글 전체 조회 기능입니다.")
    @GetMapping("/comments")
    public ResponseEntity<DataCommonResponse<Page<CommentResponseDto>>> getAllComments(@PathVariable Long questionId,
                                                                                       @RequestParam(value = "page", defaultValue = "0") int page,
                                                                                       @RequestParam(value = "sort", defaultValue = "createdAt,asc") String sort) {

        String[] sortParams = sort.split(",");
        Sort.Direction direction = sortParams[1].equalsIgnoreCase("desc") ? Sort.Direction.DESC : Sort.Direction.ASC;

        Page<CommentResponseDto> commentPage = commentService.getAllComments(questionId, page, PAGE_SIZE, Sort.by(direction, sortParams[0]));
        DataCommonResponse<Page<CommentResponseDto>> response = new DataCommonResponse<>(200, "댓글 조회에 성공 하였습니다.", commentPage);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    //Comment 수정
    @Operation(summary = "updateComment", description = "댓글 수정 기능입니다.")
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
    @Operation(summary = "deleteComment", description = "댓글 삭제 기능입니다.")
    @DeleteMapping("/comments/{commentId}")
    public ResponseEntity<StatusCommonResponse> deleteComment(@PathVariable Long questionId,
                                                              @PathVariable Long commentId,
                                                              @AuthenticationPrincipal UserDetailsImpl userDetails) {
        commentService.deleteComment(questionId, commentId, userDetails.getUser());
        StatusCommonResponse response = new StatusCommonResponse(204, "댓글 삭제가 완료되었습니다.");
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

}
