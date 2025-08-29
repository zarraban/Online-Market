package com.example.Online_Market.service.comment;


import com.example.Online_Market.dto.CommentDto;
import com.example.Online_Market.entity.comment.Comment;
import com.example.Online_Market.entity.user.User;
import com.example.Online_Market.repository.comment.CommentRepository;
import com.example.Online_Market.repository.user.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;


@Service("commentService")
@Slf4j
public class CommentService {
    private final UserRepository userRepository;
    private final CommentRepository commentRepository;

    public CommentService(
        @Qualifier("userRepository") UserRepository userRepository,
        @Qualifier("commentRepository") CommentRepository commentRepository
    ){
        this.commentRepository = commentRepository;
        this.userRepository = userRepository;
    }


    public boolean addCommentToUser(User user, CommentDto commentDto){
        if(commentDto == null){
            log.error("CommentDto is null!");
            throw new NullPointerException("CommentDto is null!");
        }
        if(user == null){
            log.error("User is null while adding comments!");
            throw new NullPointerException("User is null!");
        }

        Comment newComment = new Comment(
          commentDto.getText(),
          commentDto.getIsAnonymous()
        );

        List<Comment> userComments = user.getComments();

        if (userComments != null){
            userComments.add(newComment);
        }

        user.setComments(userComments);

        return commentRepository.findAll().contains(newComment);
    }
}
