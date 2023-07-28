package com.timcooki.jnuwiki.docs;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.timcooki.jnuwiki.domain.docs.entity.Docs;
import com.timcooki.jnuwiki.domain.docs.entity.DocsLocation;
import com.timcooki.jnuwiki.domain.docs.repository.DocsRepository;
import com.timcooki.jnuwiki.domain.docsRequest.entity.DocsCategory;
import com.timcooki.jnuwiki.domain.member.entity.Member;
import com.timcooki.jnuwiki.domain.member.entity.MemberRole;
import com.timcooki.jnuwiki.domain.member.repository.MemberRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

import javax.persistence.EntityManager;
import java.util.ArrayList;
import java.util.List;

@Import(ObjectMapper.class)
@DataJpaTest
public class DocsRepositoryTest {

    @Autowired
    private DocsRepository docsRepository;

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private ObjectMapper om;

    @BeforeEach
    public void setUp(){
        //em.createNativeQuery("ALTER TABLE DOCS ALTER COLUMN docs_id RESTART WITH 1").executeUpdate();
        Member member = Member.builder()
                .email("minl741@naver.com")
                .nickName("momo")
                .password("asbc1234!")
                .role(MemberRole.USER)
                .build();

        memberRepository.save(member);

        Double a = 1.0;
        Double b = 2.0;
        Docs docs = docsRepository.save(Docs.builder()
                        .docsName("테스트문서")
                        .docsCategory(DocsCategory.CAFE)
                        .docsLocation(DocsLocation.builder()
                                .lat(a)
                                .lng(b)
                                .build())
                        .createdBy(null)
                .build());

        Docs docs2 = docsRepository.save(
                Docs.builder()
                        .docsName("테스트문서23123")
                        .docsCategory(DocsCategory.CAFE)
                        .docsLocation(new DocsLocation(10.3, 34.0))
                        .createdBy(member)
                        .build()
        );

        Docs docs3 = docsRepository.save(
                Docs.builder()
                        .docsName("efdsfd")
                        .docsCategory(DocsCategory.CAFE)
                        .docsLocation(new DocsLocation(10.3, 34.0))
                        .createdBy(member)
                        .build()
        );

        Docs docs4 = docsRepository.save(
                Docs.builder()
                        .docsName("efdsfd")
                        .docsCategory(DocsCategory.CAFE)
                        .docsLocation(new DocsLocation(10.3, 34.0))
                        .createdBy(member)
                        .build()
        );

        Docs docs5 = docsRepository.save(
                Docs.builder()
                        .docsName("efdsfd")
                        .docsCategory(DocsCategory.CAFE)
                        .docsLocation(new DocsLocation(10.3, 34.0))
                        .createdBy(member)
                        .build()
        );

        docsRepository.findAll().forEach(System.out::println);
        docs.updateContent("테스트내용");
        em.flush();
        em.clear();
    }

    @Test
    public void search_like_test()throws JsonProcessingException {
        //given
        String search = "테스트";

        // when
        List<Docs> docsList = docsRepository.searchLike(search);
        System.out.println("으아아아아ㅏ아아");
        docsList.forEach(d -> System.out.println(d.getDocsContent()));

        // then
        Assertions.assertThat(docsList.get(0).getDocsId()).isEqualTo(1);
        Assertions.assertThat(docsList.get(0).getDocsContent()).isEqualTo("테스트내용");
    }

    @Test
    @DisplayName("페이징 정보에 따라 문서 조회")
    public void docs_findAll_test() throws JsonProcessingException {
        // given
        int page = 0;
        int size = 4;

        // when
        PageRequest pageRequest = PageRequest.of(page, size);
        Page<Docs> docs = docsRepository.findAll(pageRequest);
        docs.forEach(d -> System.out.println(d.getCreatedAt()));

        // then

    }

    @Test
    @DisplayName("최신 수정 시간순 정렬이 적용되어 있는가")
    public void docs_findAllByTime_test() throws JsonProcessingException {
        // given
        int page = 0;
        int size = 6;
        Sort sort = Sort.by(Sort.Direction.DESC, "modifiedAt");

        Member member = Member.builder()
                .email("minl@naver.com")
                .nickName("아니나는다른사람")
                .password("asbc1234!")
                .role(MemberRole.USER)
                .build();

        memberRepository.save(member);

        Docs docs6 = docsRepository.save(
                Docs.builder()
                        .docsName("이거 늦게 저장한 문서라고")
                        .docsCategory(DocsCategory.CAFE)
                        .docsLocation(new DocsLocation(10.3, 34.0))
                        .createdBy(member)
                        .build()
        );

        // when
        PageRequest pageRequest = PageRequest.of(page, size, sort);
        Page<Docs> docs = docsRepository.findAll(pageRequest);
        docs.forEach(d -> System.out.println(d.getDocsName() + " " + d.getModifiedAt()));

        // then

    }
}
