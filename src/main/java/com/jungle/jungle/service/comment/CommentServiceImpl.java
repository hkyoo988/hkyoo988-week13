package com.jungle.jungle.service.comment;

import com.jungle.jungle.dto.CommentRequestDto;
import com.jungle.jungle.dto.CommentResponseDto;
import com.jungle.jungle.dto.CommentUpdateDto;
import com.jungle.jungle.dto.SuccessResponseDto;
import com.jungle.jungle.entity.board.Board;
import com.jungle.jungle.entity.comment.Comment;
import com.jungle.jungle.entity.user.User;
import com.jungle.jungle.entity.user.UserRoleEnum;
import com.jungle.jungle.exception.CustomException;
import com.jungle.jungle.exception.ErrorCode;
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

import static com.jungle.jungle.jwt.JwtUtil.AUTHORIZATION_HEADER;

@RequiredArgsConstructor
@Service
public class CommentServiceImpl implements CommentService{

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
                    () -> new CustomException(ErrorCode.USERNAME_NOT_FOUND)
            );

            Optional<Board> board = boardRepository.findById(id);
            Comment temp = commentRequestDto.toEntity(user, board.get());
            System.out.println("===== Debug: Comment Request DTO =====");
            System.out.println(commentRequestDto.getContent());
            System.out.println("======================================");

            System.out.println("===== Debug: Comment Entity =====");
            System.out.println(temp.toString());
            System.out.println("=================================");

            Comment comment = commentRepository.save(temp);
            return CommentResponseDto.of(comment);
        } else {
            throw new CustomException(ErrorCode.TOKEN_INVALID);
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
                    () -> new CustomException(ErrorCode.USERNAME_NOT_FOUND)
            );

            Comment comment = (Comment) commentRepository.findByBoard_IdAndId(postId, id).orElseThrow(
                    () -> new CustomException(ErrorCode.INVALID_PARAMETER)
            );

            if (!comment.getUser().getUsername().equals(claims.getSubject())) {
                String roleString = claims.get(AUTHORIZATION_HEADER, String.class);
                UserRoleEnum role = UserRoleEnum.valueOf(roleString);
                if (role != UserRoleEnum.ADMIN) {
                    throw new IllegalAccessException("게시글 작성자만 수정할 수 있습니다.");
                }
            }

            comment.update(commentUpdateDto);
            return CommentResponseDto.of(comment);
        } else {
            throw new CustomException(ErrorCode.TOKEN_INVALID);
        }
    }

    public SuccessResponseDto delete(Long postId, Long id, HttpServletRequest request) throws Exception {
        String token = jwtUtil.resolveToken(request);
        Claims claims;

        if (token != null && jwtUtil.validateToken(token)) {

            claims = jwtUtil.getUserInfoFromToken(token);

            User user = userRepository.findByUsername(claims.getSubject()).orElseThrow(
                    () -> new CustomException(ErrorCode.USERNAME_NOT_FOUND)
            );

            Comment comment = (Comment) commentRepository.findByBoard_IdAndId(postId, id).orElseThrow(
                    () -> new CustomException(ErrorCode.INVALID_PARAMETER)
            );

            if (!comment.getUser().getUsername().equals(claims.getSubject())) {
                String roleString = claims.get(AUTHORIZATION_HEADER, String.class);
                UserRoleEnum role = UserRoleEnum.valueOf(roleString);
                if (role != UserRoleEnum.ADMIN) {
                    throw new IllegalAccessException("게시글 작성자만 수정할 수 있습니다.");
                }
            }

            commentRepository.deleteById(id);
            return new SuccessResponseDto(true);
        } else {
            throw new CustomException(ErrorCode.TOKEN_INVALID);
        }
    }
}

