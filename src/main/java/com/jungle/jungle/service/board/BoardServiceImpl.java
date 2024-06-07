package com.jungle.jungle.service.board;

import com.jungle.jungle.dto.BoardRequestDto;
import com.jungle.jungle.dto.BoardResponseDto;
import com.jungle.jungle.dto.SuccessResponseDto;
import com.jungle.jungle.entity.board.Board;
import com.jungle.jungle.repository.board.BoardRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BoardServiceImpl implements BoardService {

    private final BoardRepository boardRepository;

    @Transactional(readOnly = true)
    public List<BoardResponseDto> getPosts() {
        return boardRepository.findAllByOrderByModifiedAtDesc().stream().map(BoardResponseDto::of).toList();
    }

    @Transactional
    public BoardResponseDto createPost(BoardRequestDto requestDto) {
        Board board = requestDto.toEntity();
        boardRepository.save(board);
        return BoardResponseDto.of(board);
    }

    @Transactional
    public BoardResponseDto getPost(Long id) {
        return boardRepository.findById(id).map(BoardResponseDto::of).orElseThrow(
                () -> new IllegalArgumentException("아이디가 존재하지 않습니다.")
        );
    }

    @Transactional
    public BoardResponseDto updatePost(Long id, BoardRequestDto requestDto) throws Exception {
        Board board = boardRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("아이디가 존재하지 않습니다.")
        );
        if (!requestDto.getPassword().equals(board.getPassword()))
            throw new Exception("비밀번호가 일치하지 않습니다.");

        board.update(requestDto);
        return BoardResponseDto.of(board);
    }

    @Transactional
    public SuccessResponseDto deletePost(Long id, BoardRequestDto requestDto) throws Exception {
        Board board = boardRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("아이디가 존재하지 않습니다.")
        );

        if (!requestDto.getPassword().equals(board.getPassword()))
            throw new Exception("비밀번호가 일치하지 않습니다.");

        boardRepository.deleteById(id);
        return new SuccessResponseDto(true);
    }

}
