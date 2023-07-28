package com.timcooki.jnuwiki.domain.member;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.timcooki.jnuwiki.domain.docs.DTO.response.InfoEditResDTO;
import com.timcooki.jnuwiki.domain.docs.DTO.response.ListReadResDTO;
import com.timcooki.jnuwiki.domain.docs.entity.DocsLocation;
import com.timcooki.jnuwiki.domain.docsRequest.entity.DocsCategory;
import com.timcooki.jnuwiki.domain.docsRequest.entity.DocsRequestType;
import com.timcooki.jnuwiki.domain.docsRequest.service.DocsRequestService;
import com.timcooki.jnuwiki.domain.member.DTO.response.admin.EditListReadResDTO;
import com.timcooki.jnuwiki.domain.member.DTO.response.admin.EditReadResDTO;
import com.timcooki.jnuwiki.domain.member.controller.AdminController;
import com.timcooki.jnuwiki.domain.member.entity.Member;
import com.timcooki.jnuwiki.domain.member.entity.MemberRole;
import com.timcooki.jnuwiki.domain.member.service.AdminService;
import com.timcooki.jnuwiki.domain.member.service.MemberService;
import com.timcooki.jnuwiki.domain.member.service.Validator;
import com.timcooki.jnuwiki.domain.security.config.AuthenticationConfig;
import com.timcooki.jnuwiki.domain.security.config.MemberDetails;
import com.timcooki.jnuwiki.domain.security.service.MemberSecurityService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.mapping.JpaMetamodelMappingContext;
import org.springframework.http.MediaType;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.time.LocalDateTime;
import java.util.Arrays;

@Import({
        ObjectMapper.class,
        AuthenticationConfig.class
})
@WebMvcTest(controllers = {AdminController.class})
@MockBean(JpaMetamodelMappingContext.class)
public class AdminControllerTest {
    @MockBean
    private DocsRequestService docsRequestService;

    @MockBean
    private AdminService adminService;

    @MockBean
    private MemberService memberService;

    @MockBean
    private MemberSecurityService memberSecurityService;


    @Autowired
    private MockMvc mvc;

    @Autowired
    private ObjectMapper om;

    @Test
    @DisplayName("문서 수정 요청 목록 조회")
    @WithMockUser(username = "mminl@naver.cm", roles = "ADMIN")
    public void findAll_test() throws Exception {
        // given
        int page = 0;
        int size = 3;

        Member member = Member.builder()
                .email("momo@naver.com")
                .nickName("mmmm")
                .password("1235!dasd")
                .role(MemberRole.USER)
                .build();

        // stub
        Mockito.when(docsRequestService.getModifiedRequestList(PageRequest.of(page, size))).thenReturn(
                new EditListReadResDTO(
                        Arrays.asList(
                                new EditReadResDTO(1L, 1L, DocsRequestType.MODIFIED, DocsCategory.CAFE, "1이름", new DocsLocation(34.3, 43.3)),
                                new EditReadResDTO(2L, 1L, DocsRequestType.MODIFIED, DocsCategory.SCHOOL, "2이름", new DocsLocation(34.3, 43.3)),
                                new EditReadResDTO(3L, 3L, DocsRequestType.MODIFIED, DocsCategory.SCHOOL, "3이름", new DocsLocation(34.3, 43.3))
                        ))
                );


        // when
        ResultActions result = mvc.perform(
                MockMvcRequestBuilders
                        .get("/admin/requests/update/{page}", page)
                        .contentType(MediaType.APPLICATION_JSON)
        );

        String responseBody = result.andReturn().getResponse().getContentAsString();
        System.out.println("테스트 : " + responseBody);

        // then
    }

    @Test
    @DisplayName("문서 수정 요청 상세 조회")
    @WithMockUser(username = "mminl@naver.cm", roles = "ADMIN")
    public void findById_test() throws Exception {
        // given
        Long docs_request_id = 1L;

        // stub
        Mockito.when(docsRequestService.getOneModifiedRequest(docs_request_id)).thenReturn(
                new EditReadResDTO(1L, 1L, DocsRequestType.MODIFIED, DocsCategory.CAFE, "자고 싶어..", new DocsLocation(23.4, 532.4))
        );

        // when
        ResultActions result = mvc.perform(
                MockMvcRequestBuilders
                        .get("/admin/update/{docs_request_id}", docs_request_id)
                        .contentType(MediaType.APPLICATION_JSON)
        );

        String responseBody = result.andReturn().getResponse().getContentAsString();
        System.out.println("테스트 : " + responseBody);

        // then
    }

    @Test
    @DisplayName("문서 수정 요청 승락")
    @WithMockUser(username = "mminl@naver.cm", roles = "ADMIN")
    public void approve_modified_request_test() throws Exception {
        // given
        Long docs_request_id = 1L;
        Member member = Member.builder()
                .email("momo@naver.com")
                .nickName("mmmm")
                .password("1235!dasd")
                .role(MemberRole.USER)
                .build();

        // stub
        Mockito.when(adminService.updateDocsFromRequest(new MemberDetails(member), docs_request_id)).thenReturn(
                new InfoEditResDTO(1L, "자고싶다니까?", DocsCategory.CAFE, new DocsLocation(342.4, 32.4), "few", LocalDateTime.now())
        );

        // when
        ResultActions result = mvc.perform(
                MockMvcRequestBuilders
                        .post("/admin/approve/update/{docs_request_id}", docs_request_id)
                        .contentType(MediaType.APPLICATION_JSON)
        );

        String responseBody = result.andReturn().getResponse().getContentAsString();
        System.out.println("테스트 : " + responseBody);

        // then
    }


}
