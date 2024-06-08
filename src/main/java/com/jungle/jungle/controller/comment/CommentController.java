package com.jungle.jungle.controller.comment;

import com.jungle.jungle.dto.CommentRequestDto;
import com.jungle.jungle.dto.CommentResponseDto;
import com.jungle.jungle.dto.CommentUpdateDto;
import com.jungle.jungle.dto.SuccessResponseDto;
import com.jungle.jungle.entity.comment.Comment;
import com.jungle.jungle.service.comment.CommentService;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import jakarta.servlet.http.HttpServletRequest;
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
    public ResponseEntity<CommentResponseDto> save(@PathVariable("id") Long id, @RequestBody CommentRequestDto commentRequestDto, HttpServletRequest request) {
        CommentResponseDto commentResponseDto = commentService.save(id, commentRequestDto, request);

        return ResponseEntity.status(HttpStatus.CREATED).body(commentResponseDto);
    }

//    @GetMapping("/posts/{id}/comments")
//    public L

    @PutMapping({"/posts/{postId}/comments/{id}"})
    public ResponseEntity<CommentResponseDto> update(@PathVariable("postId") Long postId, @PathVariable("id") Long id, @RequestBody CommentUpdateDto commentUpdateDto, HttpServletRequest request) throws Exception {
        CommentResponseDto commentResponseDto = commentService.update(postId, id, commentUpdateDto, request);
        return ResponseEntity.status(HttpStatus.OK).body(commentResponseDto);
    }

    @DeleteMapping("/posts/{postId}/comments/{id}")
    public ResponseEntity<SuccessResponseDto> delete(@PathVariable("postId") Long postId, @PathVariable("id") Long id, HttpServletRequest request) throws Exception {
        SuccessResponseDto successResponseDto = commentService.delete(postId, id, request);
        return ResponseEntity.ok(successResponseDto);
    }
}
