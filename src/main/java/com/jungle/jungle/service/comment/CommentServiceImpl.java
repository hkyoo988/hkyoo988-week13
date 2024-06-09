package com.jungle.jungle.service.comment;

import com.jungle.jungle.dto.CommentRequestDto;
import com.jungle.jungle.dto.CommentResponseDto;
import com.jungle.jungle.dto.CommentUpdateDto;
import com.jungle.jungle.dto.SuccessResponseDto;
import com.jungle.jungle.entity.board.Board;
import com.jungle.jungle.entity.comment.Comment;
import com.jungle.jungle.entity.user.User;
import com.jungle.jungle.repository.board.BoardRepository;
import com.jungle.jungle.repository.comment.CommentRepository;
import com.jungle.jungle.repository.user.UserRepository;
import io.jsonwebtoken.Claims;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import com.jungle.jungle.jwt.JwtUtil;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class CommentServiceImpl {

    private final CommentRepository commentRepository;
    private final UserRepository userRepository;
    private final BoardRepository boardRepository;
    private final JwtUtil jwtUtil;

    @Transactional // 생성할 때는 게시글 id가 필요함
    public CommentResponseDto save(Long id, CommentRequestDto commentRequestDto, HttpServletRequest request)
    {
        String token = jwtUtil.resolveToken(request);
        Claims claims;

        if (token != null && jwtUtil.validateToken(token)) {

            claims = jwtUtil.getUserInfoFromToken(token);

            User user = userRepository.findByUsername(claims.getSubject()).orElseThrow(
                    () -> new IllegalArgumentException("사용자가 존재하지 않습니다.")
            );

            Optional<Board> board = boardRepository.findById(id);
            Comment comment = commentRepository.save(commentRequestDto.toEntity(user, board.get()));
            return CommentResponseDto.of(comment);
        } else {
            throw new IllegalArgumentException("Token invalid");
        }
    }

    @Transactional
    public List<Comment> findAll(Long id) {
        Board board = boardRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("해당 댓글이 존재하지 않습니다.")
        );
        List<Comment> comments = board.getComments();
        return comments;
    }

    @Transactional // 수정 및 삭제는 댓글 id가 필요함. 게시글 id는 필요한가?
    public CommentResponseDto update(Long postId, Long id, CommentUpdateDto commentUpdateDto, HttpServletRequest request) throws Exception {
        String token = jwtUtil.resolveToken(request);
        Claims claims;

        if (token != null && jwtUtil.validateToken(token)) {

            claims = jwtUtil.getUserInfoFromToken(token);

            User user = userRepository.findByUsername(claims.getSubject()).orElseThrow(
                    () -> new IllegalArgumentException("사용자가 존재하지 않습니다.")
            );

            Comment comment = (Comment) commentRepository.findByBoard_IdAndId(postId, id).orElseThrow(
                    () -> new IllegalArgumentException("아이디가 존재하지 않습니다.")
            );

            if (!comment.getUser().getUsername().equals(claims.getSubject())) {
                throw new IllegalAccessException("댓글 작성자만 수정할 수 있습니다.");
            }

            comment.update(commentUpdateDto);
            return CommentResponseDto.of(comment);
        } else {
            throw new IllegalArgumentException("Token invalid");
        }
    }

    public SuccessResponseDto delete(Long postId, Long id, HttpServletRequest request) throws Exception {
        String token = jwtUtil.resolveToken(request);
        Claims claims;

        if (token != null && jwtUtil.validateToken(token)) {

            claims = jwtUtil.getUserInfoFromToken(token);

            User user = userRepository.findByUsername(claims.getSubject()).orElseThrow(
                    () -> new IllegalArgumentException("사용자가 존재하지 않습니다.")
            );

            Comment comment = (Comment) commentRepository.findByBoard_IdAndId(postId, id).orElseThrow(
                    () -> new IllegalArgumentException("아이디가 존재하지 않습니다.")
            );

            if (!comment.getUser().getUsername().equals(claims.getSubject())) {
                throw new IllegalAccessException("댓글 작성자만 삭제할 수 있습니다.");
            }

            commentRepository.deleteById(id);
            return new SuccessResponseDto(true);
        } else {
            throw new IllegalArgumentException("Token invalid");
        }
    }
}

