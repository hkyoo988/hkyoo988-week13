package com.jungle.jungle.service.board;

import com.jungle.jungle.dto.BoardRequestDto;
import com.jungle.jungle.dto.BoardResponseDto;
import com.jungle.jungle.dto.SuccessResponseDto;
import com.jungle.jungle.entity.board.Board;
import com.jungle.jungle.entity.user.User;
import com.jungle.jungle.entity.user.UserRoleEnum;
import com.jungle.jungle.exception.CustomException;
import com.jungle.jungle.exception.ErrorCode;
import com.jungle.jungle.jwt.JwtUtil;
import com.jungle.jungle.repository.board.BoardRepository;
import com.jungle.jungle.repository.user.UserRepository;
import jakarta.servlet.http.HttpServletRequest;
import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.jungle.jungle.jwt.JwtUtil.AUTHORIZATION_HEADER;

@Service
@RequiredArgsConstructor
public class BoardServiceImpl implements BoardService {

    private final BoardRepository boardRepository;
    private final JwtUtil jwtUtil;
    private final UserRepository userRepository;

    @Transactional(readOnly = true)
    public List<BoardResponseDto> getPosts() {
        return boardRepository.findAllByOrderByModifiedAtDesc().stream().map(BoardResponseDto::of).toList();
    }

    @Transactional
    public BoardResponseDto createPost(BoardRequestDto requestDto, HttpServletRequest request) {
        String token = jwtUtil.resolveToken(request);
        Claims claims;

        if (token != null && jwtUtil.validateToken(token)) {

            claims = jwtUtil.getUserInfoFromToken(token);

            User user = userRepository.findByUsername(claims.getSubject()).orElseThrow(
                    () -> new CustomException(ErrorCode.USERNAME_NOT_FOUND)
            );

            Board board = boardRepository.save(requestDto.toEntity(user));
            return BoardResponseDto.of(board);
        } else {
            throw new CustomException(ErrorCode.TOKEN_INVALID);
        }
    }

    @Transactional
    public BoardResponseDto getPost(Long id) {
        return boardRepository.findById(id).map(BoardResponseDto::of).orElseThrow(
                () -> new CustomException(ErrorCode.INVALID_PARAMETER)
        );
    }

    @Transactional
    public BoardResponseDto updatePost(Long id, BoardRequestDto requestDto, HttpServletRequest request) throws Exception {
        String token = jwtUtil.resolveToken(request);
        Claims claims;

        if (token != null && jwtUtil.validateToken(token)) {
            claims = jwtUtil.getUserInfoFromToken(token);

            Board board = boardRepository.findById(id).orElseThrow(
                    () -> new CustomException(ErrorCode.INVALID_PARAMETER)
            );

            if (!board.getUser().getUsername().equals(claims.getSubject())) {
                String roleString = claims.get(AUTHORIZATION_HEADER, String.class);
                UserRoleEnum role = UserRoleEnum.valueOf(roleString);
                if (role != UserRoleEnum.ADMIN) {
                    throw new IllegalAccessException("게시글 작성자만 수정할 수 있습니다.");
                }
            }

            board.update(requestDto);
            return BoardResponseDto.of(board);
        } else {
            throw new CustomException(ErrorCode.TOKEN_INVALID);
        }
    }

    @Transactional
    public SuccessResponseDto deletePost(Long id, HttpServletRequest request) throws Exception {
        String token = jwtUtil.resolveToken(request);
        Claims claims;

        if (token != null && jwtUtil.validateToken(token)) {
            claims = jwtUtil.getUserInfoFromToken(token);

            Board board = boardRepository.findById(id).orElseThrow(
                    () -> new CustomException(ErrorCode.INVALID_PARAMETER)
            );

            if(!board.getUser().getUsername().equals(claims.getSubject())) {
                String roleString = claims.get(AUTHORIZATION_HEADER, String.class);
                UserRoleEnum role = UserRoleEnum.valueOf(roleString);
                if (role != UserRoleEnum.ADMIN) {
                    throw new IllegalAccessException("게시글 작성자만 수정할 수 있습니다.");
                }
            }

            boardRepository.deleteById(id);
            return new SuccessResponseDto(true);
        } else {
            throw new CustomException(ErrorCode.TOKEN_INVALID);
        }
    }
}
