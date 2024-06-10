package com.jungle.jungle.service.comment;

import com.jungle.jungle.dto.CommentRequestDto;
import com.jungle.jungle.dto.CommentResponseDto;
import com.jungle.jungle.dto.CommentUpdateDto;
import com.jungle.jungle.dto.SuccessResponseDto;
import com.jungle.jungle.entity.comment.Comment;
import jakarta.servlet.http.HttpServletRequest;

import java.util.List;

public interface CommentService {
    CommentResponseDto save(Long id, CommentRequestDto commentRequestDto, HttpServletRequest request);
    List<Comment> findAll(Long id);
    CommentResponseDto update(Long postId, Long id, CommentUpdateDto commentUpdateDto, HttpServletRequest request) throws Exception;
    SuccessResponseDto delete(Long postId, Long id, HttpServletRequest request) throws Exception;
}
