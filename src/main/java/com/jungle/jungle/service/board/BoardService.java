package com.jungle.jungle.service.board;

import com.jungle.jungle.dto.BoardRequestDto;
import com.jungle.jungle.dto.BoardResponseDto;
import com.jungle.jungle.dto.SuccessResponseDto;
import jakarta.servlet.http.HttpServletRequest;

import java.util.List;

public interface BoardService {
    List<BoardResponseDto> getPosts();

    BoardResponseDto createPost(BoardRequestDto requestDto, HttpServletRequest request);

    BoardResponseDto getPost(Long id);

    BoardResponseDto updatePost(Long id, BoardRequestDto requestDto, HttpServletRequest request) throws Exception;

    SuccessResponseDto deletePost(Long id, HttpServletRequest request) throws Exception;
}

