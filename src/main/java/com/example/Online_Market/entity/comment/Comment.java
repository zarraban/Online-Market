package com.example.Online_Market.entity.comment;


import com.example.Online_Market.entity.user.User;
import jakarta.persistence.*;
import lombok.*;


@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "comments")
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "comment_id")
    private Long id;

    private String text;


    @Column(name = "isanonymous")
    private Boolean isAnonymous;

    @ManyToOne( fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;



    public Comment(
        String text, Boolean isAnonymous
    ){
        this.text = text;
        this.isAnonymous = isAnonymous;
    }

}
