package com.jungle.jungle.entity.comment;

import com.jungle.jungle.dto.CommentUpdateDto;
import com.jungle.jungle.entity.board.Board;
import com.jungle.jungle.entity.common.Timestamped;
import com.jungle.jungle.entity.user.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Getter
@Builder
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Comment extends Timestamped {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String content;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "board_id")
    private Board board;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id")
    private User user;

    public void update(CommentUpdateDto commentUpdateDto) {
        this.content = commentUpdateDto.getContent();
    }
    @Override
    public String toString() {
        return "Comment{" +
                "id=" + id +
                ", user=" + (user != null ? user.getUsername() : "null") +
                ", board=" + (board != null ? board.getId() : "null") +
                ", comment='" + content + '\'' +
                '}';
    }
}
