package com.timcooki.jnuwiki.docs;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.timcooki.jnuwiki.domain.docs.DTO.response.ListReadResDTO;
import com.timcooki.jnuwiki.domain.docs.DTO.response.ReadResDTO;
import com.timcooki.jnuwiki.domain.docs.controller.DocsController;
import com.timcooki.jnuwiki.domain.docs.entity.Docs;
import com.timcooki.jnuwiki.domain.docs.entity.DocsLocation;
import com.timcooki.jnuwiki.domain.docs.service.DocsService;
import com.timcooki.jnuwiki.domain.docsRequest.entity.DocsCategory;
import com.timcooki.jnuwiki.domain.member.entity.Member;
import com.timcooki.jnuwiki.domain.member.entity.MemberRole;
import com.timcooki.jnuwiki.domain.member.repository.MemberRepository;
import com.timcooki.jnuwiki.domain.security.config.AuthenticationConfig;
import com.timcooki.jnuwiki.domain.security.service.MemberSecurityService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.mapping.JpaMetamodelMappingContext;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Import({
        ObjectMapper.class,
        AuthenticationConfig.class
})
@WebMvcTest(controllers = {DocsController.class})
@MockBean(JpaMetamodelMappingContext.class)
public class DocsControllerTestt {

    @MockBean
    private DocsService docsService;

    @MockBean
    private MemberSecurityService memberSecurityService;

    @Autowired
    private MockMvc mvc;

    @Autowired
    private ObjectMapper om;


    @Test
    @DisplayName("문서 목록 조회")
    @WithMockUser(username = "mminl@naver.cm", roles = "USER")
    public void findAll_test() throws Exception {
        // given
        int page = 1;
        int size = 3;

        Member member = Member.builder()
                .email("momo@naver.com")
                .nickName("mmmm")
                .password("1235!dasd")
                .role(MemberRole.USER)
                .build();

        PageRequest pageRequest = PageRequest.of(page, size);
        List<ListReadResDTO> list = new ArrayList<>();
        list.add(new ListReadResDTO(1L, "내용1", DocsCategory.CONV, new DocsLocation(13.1, 34.3), "1nuu", member, LocalDateTime.now()));
        list.add(new ListReadResDTO(2L, "내용2", DocsCategory.CAFE, new DocsLocation(13.1, 34.3), "2nuu", member, LocalDateTime.now()));
        list.add(new ListReadResDTO(3L, "내용3", DocsCategory.SCHOOL, new DocsLocation(13.1, 34.3), "3nuu", member, LocalDateTime.now()));

        list.forEach(System.out::println);

        // stub
        Mockito.when(docsService.getDocsList(pageRequest)).thenReturn(list);

        // when
        ResultActions result = mvc.perform(
                MockMvcRequestBuilders
                        .get("/docs")
                        .param("page", "1")
                        .param("size", "3")
                        .contentType(MediaType.APPLICATION_JSON)
        );

        String responseBody = result.andReturn().getResponse().getContentAsString(StandardCharsets.UTF_8);
        System.out.println("테스트 : " + responseBody);

        // then

    }


    @Test
    @DisplayName("문서 상세 조회")
    @WithMockUser(username = "mminl@naver.cm", roles = "USER")
    public void findById_test() throws Exception {
        // given
        Long docsId = 2L;

        Member member = Member.builder()
                .email("momo@naver.com")
                .nickName("mmmm")
                .password("1235!dasd")
                .role(MemberRole.USER)
                .build();

        // stub
        Mockito.when(docsService.getOneDocs(docsId)).thenReturn(
                new ReadResDTO(1L, "내용1", DocsCategory.CONV, new DocsLocation(13.1, 34.3), "1nuu", member, LocalDateTime.now())
        );

        // when
        ResultActions result = mvc.perform(
                MockMvcRequestBuilders
                        .get("/docs/{docsId}", docsId)
                        .contentType(MediaType.APPLICATION_JSON)
        );

        String responseBody = result.andReturn().getResponse().getContentAsString(StandardCharsets.UTF_8);
        System.out.println("테스트 : " + responseBody);

        // then

    }

}
