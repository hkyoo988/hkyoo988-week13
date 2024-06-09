package com.jungle.jungle.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.jungle.jungle.entity.board.Board;
import com.jungle.jungle.entity.comment.Comment;
import com.jungle.jungle.entity.user.User;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CommentRequestDto {
    @NotBlank
    @JsonProperty("content")
    private String content;

    @NotBlank
    private String author;

    public Comment toEntity(User user, Board board) {
        return Comment.builder()
                .content(this.content)
                .user(user)
                .board(board)
                .build();
    }
}
