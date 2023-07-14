package com.example.kakaoboardjpa;

import com.example.kakaoboardjpa.board.Board;
import com.example.kakaoboardjpa.reply.Reply;
import com.example.kakaoboardjpa.user.User;

public class DummyEntity {

    protected User newUser(String username){
        return User.builder()
                .username(username)
                .password("1234")
                .email(username+"@nate.com")
                .build();
    }


    protected Board newBoard(String title, User userPS){
        return Board.builder()
                .title(title)
                .content(title)
                .user(userPS)
                .build();
    }

    protected Reply newReply(String comment, User userPS, Board board){
        return Reply.builder()
                .user(userPS)
                .board(board)
                .comment(comment)
                .build();
    }
}
