package com.jungle.jungle.repository.comment;

import com.jungle.jungle.entity.comment.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {
    Optional<Comment> findByBoard_IdAndId(Long board_id, Long id);
}
