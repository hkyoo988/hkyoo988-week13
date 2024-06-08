package com.jungle.jungle.entity.comment;

import com.jungle.jungle.dto.CommentResponseDto;
import com.jungle.jungle.dto.CommentUpdateDto;
import com.jungle.jungle.entity.board.Board;
import com.jungle.jungle.entity.common.Timestamped;
import com.jungle.jungle.entity.user.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Entity
public class Comment extends Timestamped {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String comment;

    @ManyToOne
    @JoinColumn(name = "board_id")
    private Board board;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    public void update(CommentUpdateDto commentUpdateDto) {
        this.comment = commentUpdateDto.getComment();
    }
}
