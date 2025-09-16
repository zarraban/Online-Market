package com.example.Online_Market.repository.comment;

import com.example.Online_Market.entity.comment.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository("commentRepository")
public interface CommentRepository extends JpaRepository<Comment,Long> {
}
