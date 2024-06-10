package com.jungle.jungle.dto;

import com.jungle.jungle.entity.board.Board;
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
public class BoardRequestDto {
    @NotBlank
    private String title;
    @NotBlank
    private String content;
    @NotBlank
    private String author;

    public Board toEntity(User user) {
        return Board.builder()
                .title(this.title)
                .content(this.content)
                .author(this.author)
                .user(user)
                .build();
    }
}
