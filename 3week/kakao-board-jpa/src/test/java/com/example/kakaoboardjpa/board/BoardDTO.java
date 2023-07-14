package com.example.kakaoboardjpa.board;

import com.example.kakaoboardjpa.user.User;

// 화면에 전달할 오브젝트
public class BoardDTO {
    private int id;
    private String title;
    private String content;
    private UserDTO user;

    public BoardDTO(Board board) {
        this.id = board.getId();
        this.title = board.getTitle();
        this.content = board.getContent();
        this.user = new UserDTO(board.getUser());
    }

    public class UserDTO {
        private Integer id;
        private String username;
        private String email;

        // DTO의 생성자로 Lazy Loading을 한다.
        public UserDTO(User user) {
            this.id = user.getId();
            this.username = user.getUsername();
            this.email = user.getEmail();
        }

        // getter - setter (테스트에서는 lombok 사용못함)
        public Integer getId() {
            return id;
        }

        public void setId(Integer id) {
            this.id = id;
        }

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public UserDTO getUser() {
        return user;
    }

    public void setUser(UserDTO user) {
        this.user = user;
    }
}