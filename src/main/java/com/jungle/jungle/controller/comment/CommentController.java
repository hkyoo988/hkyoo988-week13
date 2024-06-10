package com.jungle.jungle.controller.comment;

import com.jungle.jungle.dto.*;
import com.jungle.jungle.service.comment.CommentService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class CommentController {

    private final CommentService commentService;

    @PostMapping("/posts/{id}/comments")
    public ResponseEntity<EnvelopeResponseDto<CommentResponseDto>> save(@PathVariable Long id, @Valid @RequestBody CommentRequestDto requestDto, HttpServletRequest request) {
        CommentResponseDto commentResponseDto = commentService.save(id, requestDto, request);
        EnvelopeResponseDto<CommentResponseDto> response = new EnvelopeResponseDto<>("success", "Post created successfully", commentResponseDto);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PutMapping({"/posts/{postId}/comments/{id}"})
    public ResponseEntity<EnvelopeResponseDto<CommentResponseDto>> update(@PathVariable("postId") Long postId, @PathVariable("id") Long id, @RequestBody CommentUpdateDto commentUpdateDto, HttpServletRequest request) throws Exception {
        CommentResponseDto commentResponseDto = commentService.update(postId, id, commentUpdateDto, request);
        EnvelopeResponseDto<CommentResponseDto> response = new EnvelopeResponseDto<>("success", "Post created successfully", commentResponseDto);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @DeleteMapping("/posts/{postId}/comments/{id}")
    public ResponseEntity<EnvelopeResponseDto<SuccessResponseDto>> delete(@PathVariable("postId") Long postId, @PathVariable("id") Long id, HttpServletRequest request) throws Exception {
        SuccessResponseDto successResponseDto = commentService.delete(postId, id, request);
        EnvelopeResponseDto<SuccessResponseDto> response = new EnvelopeResponseDto<>("success", "Post deleted successfully", successResponseDto);
        return ResponseEntity.ok(response);
    }

}
