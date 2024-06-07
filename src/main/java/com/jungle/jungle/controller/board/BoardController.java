package com.jungle.jungle.controller.board;

import com.jungle.jungle.dto.BoardRequestDto;
import com.jungle.jungle.dto.BoardResponseDto;
import com.jungle.jungle.dto.SuccessResponseDto;
import com.jungle.jungle.service.board.BoardService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class BoardController {

    private final BoardService boardService;

    @GetMapping("/api/posts")
    public List<BoardResponseDto> getPosts() {
        return boardService.getPosts();
    }

//    @GetMapping("/api/posts")
//    public ResponseEntity<List<BoardResponseDto>> getPosts() {
//        return ResponseEntity.ok()
//                .headers(header)
//                .body(body);
//    }

    @PostMapping("/api/posts")
    public BoardResponseDto createPost(@RequestBody BoardRequestDto requestDto) {
        return boardService.createPost(requestDto);
    }

    @GetMapping("/api/posts/{id}")
    public BoardResponseDto getPost(@PathVariable("id") Long id) {
        return boardService.getPost(id);
    }

    @PutMapping("/api/posts/{id}")
    public BoardResponseDto updatePost(@PathVariable("id") Long id, @RequestBody BoardRequestDto requestDto) throws Exception {
        return boardService.updatePost(id, requestDto);
    }

    @DeleteMapping("/api/posts/{id}")
    public SuccessResponseDto deletePost(@PathVariable("id") Long id, @RequestBody BoardRequestDto requestDto) throws Exception {
        return boardService.deletePost(id, requestDto);
    }
}
