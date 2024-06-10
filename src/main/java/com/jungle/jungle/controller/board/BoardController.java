package com.jungle.jungle.controller.board;

import com.jungle.jungle.dto.BoardRequestDto;
import com.jungle.jungle.dto.BoardResponseDto;
import com.jungle.jungle.dto.EnvelopeResponseDto;
import com.jungle.jungle.dto.SuccessResponseDto;
import com.jungle.jungle.service.board.BoardService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/posts")
public class BoardController {

    private final BoardService boardService;

    @GetMapping
    public ResponseEntity<EnvelopeResponseDto<List<BoardResponseDto>>> getPosts() {
        List<BoardResponseDto> posts = boardService.getPosts();
        EnvelopeResponseDto<List<BoardResponseDto>> response = new EnvelopeResponseDto<>("success", "Posts retrieved successfully", posts);
        return ResponseEntity.ok(response);
    }

    @PostMapping
    public ResponseEntity<EnvelopeResponseDto<BoardResponseDto>> createPost(@RequestBody BoardRequestDto requestDto, HttpServletRequest request) {
        BoardResponseDto responseDto = boardService.createPost(requestDto, request);
        EnvelopeResponseDto<BoardResponseDto> response = new EnvelopeResponseDto<>("success", "Post created successfully", responseDto);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<EnvelopeResponseDto<BoardResponseDto>> getPost(@PathVariable Long id) {
        BoardResponseDto responseDto = boardService.getPost(id);
        EnvelopeResponseDto<BoardResponseDto> response = new EnvelopeResponseDto<>("success", "Post retrieved successfully", responseDto);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<EnvelopeResponseDto<BoardResponseDto>> updatePost(@PathVariable Long id, @RequestBody BoardRequestDto requestDto, HttpServletRequest request) throws Exception {
        BoardResponseDto responseDto = boardService.updatePost(id, requestDto, request);
        EnvelopeResponseDto<BoardResponseDto> response = new EnvelopeResponseDto<>("success", "Post updated successfully", responseDto);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<EnvelopeResponseDto<SuccessResponseDto>> deletePost(@PathVariable Long id, HttpServletRequest request) throws Exception {
        SuccessResponseDto responseDto = boardService.deletePost(id, request);
        EnvelopeResponseDto<SuccessResponseDto> response = new EnvelopeResponseDto<>("success", "Post deleted successfully", responseDto);
        return ResponseEntity.ok(response);
    }
}
