package com.jungle.jungle.controller.board;

import com.jungle.jungle.dto.BoardRequestDto;
import com.jungle.jungle.dto.BoardResponseDto;
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

    @GetMapping("/api/posts")
    public ResponseEntity<List<BoardResponseDto>> getPosts() {
        List<BoardResponseDto> posts = boardService.getPosts();
        return ResponseEntity.ok(posts);
    }

    @PostMapping
    public ResponseEntity<BoardResponseDto> createPost(@RequestBody BoardRequestDto requestDto, HttpServletRequest request) {
        BoardResponseDto responseDto = boardService.createPost(requestDto, request);
        return ResponseEntity.status(HttpStatus.CREATED).body(responseDto);
    }

    @GetMapping("/{id}")
    public ResponseEntity<BoardResponseDto> getPost(@PathVariable Long id) {
        BoardResponseDto responseDto = boardService.getPost(id);
        return ResponseEntity.ok(responseDto);
    }

    @PutMapping("/{id}")
    public ResponseEntity<BoardResponseDto> updatePost(@PathVariable Long id, @RequestBody BoardRequestDto requestDto, HttpServletRequest request) throws Exception {
        BoardResponseDto responseDto = boardService.updatePost(id, requestDto, request);
        return ResponseEntity.ok(responseDto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<SuccessResponseDto> deletePost(@PathVariable Long id, HttpServletRequest request) throws Exception {
        SuccessResponseDto responseDto = boardService.deletePost(id, request);
        return ResponseEntity.ok(responseDto);
    }
}
