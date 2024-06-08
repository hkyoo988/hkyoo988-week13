package com.jungle.jungle.service.board;

import com.jungle.jungle.dto.BoardRequestDto;
import com.jungle.jungle.dto.BoardResponseDto;
import com.jungle.jungle.dto.SuccessResponseDto;
import jakarta.servlet.http.HttpServletRequest;

import java.util.List;

public interface BoardService {
    public List<BoardResponseDto> getPosts();

    public BoardResponseDto createPost(BoardRequestDto requestDto, HttpServletRequest request);

    public BoardResponseDto getPost(Long id);

    public BoardResponseDto updatePost(Long id, BoardRequestDto requestDto, HttpServletRequest request) throws Exception;

    public SuccessResponseDto deletePost(Long id, HttpServletRequest request) throws Exception;
}

