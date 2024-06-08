package com.jungle.jungle.service.board;

import com.jungle.jungle.dto.BoardRequestDto;
import com.jungle.jungle.dto.BoardResponseDto;
import com.jungle.jungle.dto.SuccessResponseDto;
import com.jungle.jungle.entity.board.Board;
import com.jungle.jungle.entity.user.User;
import com.jungle.jungle.jwt.JwtUtil;
import com.jungle.jungle.repository.board.BoardRepository;
import com.jungle.jungle.repository.user.UserRepository;
import jakarta.servlet.http.HttpServletRequest;
import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

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
                    () -> new IllegalArgumentException("사용자가 존재하지 않습니다.")
            );

            Board board = boardRepository.save(requestDto.toEntity(user));
            return BoardResponseDto.of(board);
        } else {
            throw new IllegalArgumentException("Token invalid");
        }
    }

    @Transactional
    public BoardResponseDto getPost(Long id) {
        return boardRepository.findById(id).map(BoardResponseDto::of).orElseThrow(
                () -> new IllegalArgumentException("아이디가 존재하지 않습니다.")
        );
    }

    @Transactional
    public BoardResponseDto updatePost(Long id, BoardRequestDto requestDto, HttpServletRequest request) throws Exception {
        String token = jwtUtil.resolveToken(request);
        Claims claims;

        if (token != null && jwtUtil.validateToken(token)) {
            claims = jwtUtil.getUserInfoFromToken(token);

            Board board = boardRepository.findById(id).orElseThrow(
                    () -> new IllegalArgumentException("아이디가 존재하지 않습니다.")
            );

            if (!board.getUser().getUsername().equals(claims.getSubject())) {
                throw new IllegalAccessException("게시글 작성자만 수정할 수 있습니다.");
            }

            board.update(requestDto);
            return BoardResponseDto.of(board);
        } else {
            throw new IllegalArgumentException("Token invalid");
        }
    }

    @Transactional
    public SuccessResponseDto deletePost(Long id, HttpServletRequest request) throws Exception {
        String token = jwtUtil.resolveToken(request);
        Claims claims;

        if (token != null && jwtUtil.validateToken(token)) {
            claims = jwtUtil.getUserInfoFromToken(token);

            Board board = boardRepository.findById(id).orElseThrow(
                    () -> new IllegalArgumentException("아이디가 존재하지 않습니다.")
            );

            if(!board.getUser().getUsername().equals(claims.getSubject())) {
                throw new IllegalAccessException("게시글 작성자만 삭제할 수 있습니다.");
            }

            boardRepository.deleteById(id);
            return new SuccessResponseDto(true);
        } else {
            throw new IllegalArgumentException("Token invalid");
        }
    }
}
