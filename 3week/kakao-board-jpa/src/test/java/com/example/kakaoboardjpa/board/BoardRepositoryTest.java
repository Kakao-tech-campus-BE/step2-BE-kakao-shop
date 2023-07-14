package com.example.kakaoboardjpa.board;

import com.example.kakaoboardjpa.DummyEntity;
import com.example.kakaoboardjpa.user.User;
import com.example.kakaoboardjpa.user.UserRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.Optional;


@Import(ObjectMapper.class)
@DataJpaTest
public class BoardRepositoryTest extends DummyEntity {

    @Autowired
    private BoardRepository boardRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private ObjectMapper om;

    @BeforeEach
    public void setUp() {
        em.createNativeQuery("ALTER TABLE user_tb ALTER COLUMN id RESTART WITH 1").executeUpdate();
        em.createNativeQuery("ALTER TABLE board_tb ALTER COLUMN id RESTART WITH 1").executeUpdate();
        User ssar = userRepository.save(newUser("ssar"));
        User cos = userRepository.save(newUser("cos"));
        boardRepository.save(newBoard("title1", ssar));
        boardRepository.save(newBoard("title2", ssar));
        boardRepository.save(newBoard("title3", cos));
        em.clear();
    }

    @Test
    public void findById_not_em_clear_test() {
        // given
        int id = 1;

        // when
        boardRepository.findById(1);

        // then
    }

    @Test
    public void findById_user_eager_test() {
        // given
        int id = 1;

        // when
        boardRepository.findById(1);

        // then
    }

    @Test
    public void findById_user_lazy_test() {
        // given
        int id = 1;

        // when
        boardRepository.findById(1);

        // then
    }

    @Test
    public void findById_user_lazy_loading_test() {
        // given
        int id = 1;

        // when
        Optional<Board> boardOP = boardRepository.findById(1);
        if (boardOP.isPresent()) {
            System.out.println("테스트 : board 객체를 꺼낸다.");
            Board boardPS = boardOP.get();
            System.out.println("테스트 : board 객체의 User의 id를 불러본다 : FK로 들고 있는 값이기 때문에 Lazy 로딩안됨");
            boardPS.getUser().getId();
            System.out.println("테스트 : board 객체의 User의 username를 불러본다 : Lazy 로딩 발동");
            boardPS.getUser().getUsername();
        }

        // then
    }

    @Test
    public void findById_user_lazy_loading_message_converter_fail_test() throws JsonProcessingException {
        // given
        int id = 1;

        // when
        Optional<Board> boardOP = boardRepository.findById(1);
        if (boardOP.isPresent()) {
            System.out.println("테스트 : board 객체를 꺼낸다.");
            Board boardPS = boardOP.get();
            boardPS.getUser().getId();
            boardPS.getUser().getUsername();
            boardPS.getUser().getPassword();
            boardPS.getUser().getEmail();
            System.out.println("테스트 : MessageConverter를 발동시킨다.");
            String responseBody = om.writeValueAsString(boardPS);
            System.out.println("테스트 : " + responseBody);
        }
        // then
    }


    @Test
    public void findById_user_lazy_loading_message_converter_dto_test() throws JsonProcessingException {
        // given
        int id = 1;

        // when
        Optional<Board> boardOP = boardRepository.findById(1);
        if (boardOP.isPresent()) {
            System.out.println("테스트 : board 객체를 꺼낸다.");
            Board boardPS = boardOP.get();
            System.out.println("테스트 : DTO를 만들면서 Lazy 로딩을 한다.");
            BoardDTO boardDTO = new BoardDTO(boardPS);
            System.out.println("테스트 : MessageConverter를 발동시킨다.");
            String responseBody = om.writeValueAsString(boardDTO);
            System.out.println("테스트 : " + responseBody);
        }
        // then
    }

    @Test
    public void findById_user_lazy_loading_message_converter_join_fetch_test() throws JsonProcessingException {
        // given
        int id = 1;

        // when
        //Optional<Board> boardOP = boardRepository.mFindByIdJoinUser(1);
        Optional<Board> boardOP = boardRepository.mFindByIdJoinFetchUser(1);
        if (boardOP.isPresent()) {
            Board boardPS = boardOP.get();
            String responseBody = om.writeValueAsString(boardPS);
            System.out.println("테스트 : " + responseBody);
        }
        // then
    }

    @Test
    public void findAll_eager_no_default_fetch_test() {
        // given

        // when
        boardRepository.findAll();

        // then
    }

    @Test
    public void findAll_lazy_no_default_fetch_test() {
        // given

        // when
        boardRepository.findAll();

        // then
    }

    // default_batch_fetch_size: 100 활성화
    @Test
    public void findAll_default_fetch_test() {
        // given

        // when
        List<Board> boardList = boardRepository.findAll();

        // ToString 어노테이션을 붙이면 MessageConverter가 발동하는 것과 같이 LazyLoading을 해볼 수 있다.
        System.out.println(boardList);
        // then
    }

    @Test
    public void update_test() {
        // given
        int id = 1;
        String title = "title1 update";
        String content = "content1 update";

        // when
        Optional<Board> boardOP = boardRepository.findById(id);
        if (boardOP.isPresent()) {
            Board boardPS = boardOP.get();
            boardPS.update(title, content);
        }
        em.flush();

        // then
    }

    @Test
    public void deleteById_fail_test() {
        // given
        int id = 5;

        // when
        boardRepository.deleteById(id);

        // then
    }

    @Test
    public void deleteById_safe_fail_test() {
        // given
        int id = 5;

        // when
        Optional<Board> boardOP = boardRepository.findById(id);
        if (boardOP.isPresent()) {
            boardRepository.deleteById(id);
        }

        // then
    }

    @Test
    public void deleteById_success_test() {
        // given
        int id = 1;

        // when
        Optional<Board> boardOP = boardRepository.findById(id);
        if (boardOP.isPresent()) {
            boardRepository.deleteById(id);
            em.flush();
        }
        // then
    }
}