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
    private final  CommentRepository commentRepository;

    public CommentService(
        @Qualifier("userRepository") UserRepository userRepository,
        @Qualifier("commentRepository") CommentRepository commentRepository
    ){
        this.commentRepository = commentRepository;
        this.userRepository = userRepository;
    }

    public User addCommentToUser(CommentDto commentDto) throws NullPointerException{
        if(commentDto == null){
            log.error("CommentDto is null!");
            throw new NullPointerException("CommentDto is null!");
        }
        User user = userRepository.findByEmail(commentDto.getEmail());

        if(user == null){
            log.error("User[email={}] is null!", commentDto.getEmail());
            throw new NullPointerException("User is null!");
        }
        List<Comment> userComments = user.getComments();

        Comment newComment = new Comment(
                commentDto.getText(),
                commentDto.getIsAnonymous()
        );

        userComments.add(newComment);

        user.setComments(userComments);

        return user;

    }
}
