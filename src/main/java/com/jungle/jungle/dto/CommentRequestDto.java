package com.jungle.jungle.dto;

import com.jungle.jungle.entity.board.Board;
import com.jungle.jungle.entity.comment.Comment;
import com.jungle.jungle.entity.user.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CommentRequestDto {
    private String comment;

    public Comment toEntity(User user, Board board) {
        return Comment.builder()
                .comment(this.comment)
                .user(user)
                .board(board)
                .build();
    }
}
