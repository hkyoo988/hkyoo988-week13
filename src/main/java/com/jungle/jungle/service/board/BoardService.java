package com.jungle.jungle.service.board;

import com.jungle.jungle.dto.BoardRequestDto;
import com.jungle.jungle.dto.BoardResponseDto;
import com.jungle.jungle.dto.SuccessResponseDto;
import org.springframework.stereotype.Service;

import java.util.List;

public interface BoardService {
    public List<BoardResponseDto> getPosts();

    public BoardResponseDto createPost(BoardRequestDto requestDto);

    public BoardResponseDto getPost(Long id);

    public BoardResponseDto updatePost(Long id, BoardRequestDto requestDto) throws Exception;

    public SuccessResponseDto deletePost(Long id, BoardRequestDto requestDto) throws Exception;
}

