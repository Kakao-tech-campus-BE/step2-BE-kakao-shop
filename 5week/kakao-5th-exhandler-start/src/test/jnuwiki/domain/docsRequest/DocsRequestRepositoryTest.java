package com.timcooki.jnuwiki.domain.docsRequest;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.timcooki.jnuwiki.domain.docs.entity.Docs;
import com.timcooki.jnuwiki.domain.docs.entity.DocsLocation;
import com.timcooki.jnuwiki.domain.docs.repository.DocsRepository;
import com.timcooki.jnuwiki.domain.docsRequest.entity.DocsCategory;
import com.timcooki.jnuwiki.domain.docsRequest.entity.DocsRequest;
import com.timcooki.jnuwiki.domain.docsRequest.entity.DocsRequestType;
import com.timcooki.jnuwiki.domain.docsRequest.repository.DocsRequestRepository;
import com.timcooki.jnuwiki.domain.member.entity.Member;
import com.timcooki.jnuwiki.domain.member.entity.MemberRole;
import com.timcooki.jnuwiki.domain.member.repository.MemberRepository;
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

@Import(ObjectMapper.class)
@DataJpaTest
public class DocsRequestRepositoryTest {
    @Autowired
    private DocsRequestRepository docsRequestRepository;

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private DocsRepository docsRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private ObjectMapper om;

    @BeforeEach
    public void setUp() {
        //em.createNativeQuery("ALTER TABLE DOCS ALTER COLUMN docs_id RESTART WITH 1").executeUpdate();
        Member member = Member.builder()
                .email("minl741@naver.com")
                .nickName("momo")
                .password("asbc1234!")
                .role(MemberRole.USER)
                .build();

        Docs docs = Docs.builder()
                .docsName("이머")
                .docsLocation(new DocsLocation(1.3, 321.3))
                .docsCategory(DocsCategory.CONV)
                .createdBy(member)
                .build();

        memberRepository.save(member);
        docsRepository.save(docs);

        DocsRequest request1 = docsRequestRepository.save(
                DocsRequest.builder()
                        .docsRequestName("1111111")
                        .docsRequestType(DocsRequestType.MODIFIED)
                        .docsRequestCategory(DocsCategory.CAFE)
                        .docsRequestLocation(new DocsLocation(10.3, 34.0))
                        .docsRequestedBy(member)
                        .docs(docs)
                        .build()
        );

        DocsRequest request2 = docsRequestRepository.save(
                DocsRequest.builder()
                        .docsRequestName("222222")
                        .docsRequestType(DocsRequestType.MODIFIED)
                        .docsRequestCategory(DocsCategory.SCHOOL)
                        .docsRequestLocation(new DocsLocation(10.3, 34.0))
                        .docsRequestedBy(member)
                        .docs(docs)
                        .build()
        );

        DocsRequest request3 = docsRequestRepository.save(
                DocsRequest.builder()
                        .docsRequestName("33333")
                        .docsRequestType(DocsRequestType.MODIFIED)
                        .docsRequestCategory(DocsCategory.CAFE)
                        .docsRequestLocation(new DocsLocation(10.3, 34.0))
                        .docsRequestedBy(member)
                        .docs(docs)
                        .build()
        );

        DocsRequest request4 = docsRequestRepository.save(
                DocsRequest.builder()
                        .docsRequestName("44444")
                        .docsRequestType(DocsRequestType.CREATED)
                        .docsRequestCategory(DocsCategory.CONV)
                        .docsRequestLocation(new DocsLocation(10.3, 34.0))
                        .docsRequestedBy(member)
                        .docs(docs)
                        .build()
        );

        DocsRequest request5 = docsRequestRepository.save(
                DocsRequest.builder()
                        .docsRequestName("55555")
                        .docsRequestType(DocsRequestType.CREATED)
                        .docsRequestCategory(DocsCategory.CAFE)
                        .docsRequestLocation(new DocsLocation(10.3, 34.0))
                        .docsRequestedBy(member)
                        .docs(docs)
                        .build()
        );

        DocsRequest request6 = docsRequestRepository.save(
                DocsRequest.builder()
                        .docsRequestName("666666")
                        .docsRequestType(DocsRequestType.CREATED)
                        .docsRequestCategory(DocsCategory.SCHOOL)
                        .docsRequestLocation(new DocsLocation(11.3, 33.0))
                        .docsRequestedBy(member)
                        .docs(docs)
                        .build()
        );

        DocsRequest request7 = docsRequestRepository.save(
                DocsRequest.builder()
                        .docsRequestName("77777")
                        .docsRequestType(DocsRequestType.CREATED)
                        .docsRequestCategory(DocsCategory.CAFE)
                        .docsRequestLocation(new DocsLocation(10.3, 34.0))
                        .docsRequestedBy(member)
                        .docs(docs)
                        .build()
        );

        docsRequestRepository.findAll().forEach(System.out::println);
        docs.updateContent("테스트내용");
        em.flush();
        em.clear();
    }

    @Test
    @DisplayName("요청 타입과 페이징 정보에 맞게 조회되는가")
    public void findAll_RequestType_test() {
        // given
        DocsRequestType docsRequestType = DocsRequestType.CREATED;

        Sort sort = Sort.by(Sort.Direction.DESC, "createdAt");
        PageRequest pageRequest = PageRequest.of(0, 3, sort);

        // when
        Page<DocsRequest> docsRequests = docsRequestRepository.findAllByDocsRequestType(docsRequestType, pageRequest);
        docsRequests.forEach(d -> System.out.println(d.getDocsRequestType() + " " + d.getCreatedAt()));

        // then
    }
}
