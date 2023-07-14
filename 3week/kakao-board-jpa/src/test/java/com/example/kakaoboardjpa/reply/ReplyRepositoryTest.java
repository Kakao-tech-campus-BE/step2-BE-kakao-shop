package com.example.kakaoboardjpa.reply;

import com.example.kakaoboardjpa.DummyEntity;
import com.example.kakaoboardjpa.board.Board;
import com.example.kakaoboardjpa.board.BoardRepository;
import com.example.kakaoboardjpa.user.User;
import com.example.kakaoboardjpa.user.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.PageRequest;

import javax.persistence.EntityManager;
import java.util.List;

@DataJpaTest
public class ReplyRepositoryTest extends DummyEntity {

    @Autowired
    private BoardRepository boardRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ReplyRepository replyRepository;

    @Autowired
    private EntityManager em;

    // 더미 데이터를 잘 기억해야 된다. 시나리오를 짜보자
    @BeforeEach
    public void setUp() {
        em.createNativeQuery("ALTER TABLE user_tb ALTER COLUMN id RESTART WITH 1").executeUpdate();
        em.createNativeQuery("ALTER TABLE board_tb ALTER COLUMN id RESTART WITH 1").executeUpdate();
        User ssar = userRepository.save(newUser("ssar"));
        User cos = userRepository.save(newUser("cos"));
        User love = userRepository.save(newUser("love"));
        Board board1 = boardRepository.save(newBoard("자바 재밌다", ssar));

        replyRepository.save(newReply("자바 공부 그만하고 휴가가자", cos, board1));
        replyRepository.save(newReply("댓글 씹는거야?", cos, board1));
        replyRepository.save(newReply("미안해 자기야 공부하다가 바빳어", ssar, board1));
        replyRepository.save(newReply("ㅡㅡ;", love, board1));
        replyRepository.save(newReply("ㅡㅡ;", love, board1));
        replyRepository.save(newReply("ㅡㅡ;", love, board1));
        replyRepository.save(newReply("ㅡㅡ;", love, board1));

        em.clear();
    }

    // ManyToOne을 모두 Lazy 전략 변경한다.
    @Test
    public void board_detail_test1() {
        // given
        int boardId = 1;

        // when
        // 1. 게시글과 작성자 정보를 찾는다. (board1, ssar) - 영속화 되어 있음
        Board boardPS = boardRepository.mFindByIdJoinFetchUser(boardId).orElseThrow(
                () -> new RuntimeException("게시글을 찾을 수 없어요")
        );

        // 2. 댓글을 불러 온다. (reply1)
        List<Reply> replyList = replyRepository.findByBoardId(boardId);

        // 3. reply1에 연관되어 있는 board1 정보는 찾지 않아도 된다. (board1은 위에서 이미 조회되었음)

        // 4. reply1에 연관되어 있는 cos 정보는 찾아야 한다. in query로 찾으면 된다. lazy loading 한다.
        replyList.forEach(reply -> {
            reply.getUser().getUsername();
        });

        // then
    }


    // ManyToOne을 모두 Lazy 전략 변경한다.
    @Test
    public void board_detail_test2() {
        // given
        int boardId = 1;

        // when

        // 1. 게시글과 작성자 정보를 찾는다. (board1, ssar) - 영속화 됨
        Board boardPS = boardRepository.mFindByIdJoinFetchUser(boardId).orElseThrow(
                () -> new RuntimeException("게시글을 찾을 수 없어요")
        );

        // 2. 댓글을 불러 온다. (reply1, user들) - 영속화 됨
        PageRequest pageRequest = PageRequest.of(0, 4);
        List<Reply> replyList = replyRepository.mFindByBoardId(boardId, pageRequest);

        // then
    }
}
