package com.jungle.jungle.dto;

import com.jungle.jungle.entity.board.Board;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class BoardRequestDto {
    @NotBlank
    private String title;
    @NotBlank
    private String content;
    @NotBlank
    private String author;
    @NotBlank
    private String password;

    public Board toEntity() {
        return Board.builder()
                .title(this.title)
                .content(this.content)
                .author(this.author)
                .password(this.password)
                .build();
    }
}
