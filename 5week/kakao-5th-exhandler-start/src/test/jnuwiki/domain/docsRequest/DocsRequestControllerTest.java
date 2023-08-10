package com.timcooki.jnuwiki.domain.docsRequest;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.timcooki.jnuwiki.domain.docs.entity.DocsLocation;
import com.timcooki.jnuwiki.domain.docsRequest.controller.DocsRequestController;
import com.timcooki.jnuwiki.domain.docsRequest.dto.request.EditWriteReqDTO;
import com.timcooki.jnuwiki.domain.docsRequest.dto.response.NewWriteResDTO;
import com.timcooki.jnuwiki.domain.docsRequest.entity.DocsCategory;
import com.timcooki.jnuwiki.domain.docsRequest.entity.DocsRequestType;
import com.timcooki.jnuwiki.domain.docsRequest.service.DocsRequestService;
import com.timcooki.jnuwiki.domain.member.entity.Member;
import com.timcooki.jnuwiki.domain.member.entity.MemberRole;
import com.timcooki.jnuwiki.domain.member.service.MemberService;
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
import org.springframework.data.jpa.mapping.JpaMetamodelMappingContext;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.nio.charset.StandardCharsets;


@Import({
        ObjectMapper.class,
        AuthenticationConfig.class
})
@WebMvcTest(controllers = {DocsRequestController.class})
@MockBean(JpaMetamodelMappingContext.class)
public class DocsRequestControllerTest {

    @MockBean
    private DocsRequestService docsRequestService;

    @MockBean
    private MemberService memberService;

    @MockBean
    private MemberSecurityService memberSecurityService;

    @Autowired
    private MockMvc mvc;

    @Autowired
    private ObjectMapper om;

    @Test
    @DisplayName("문서 수정 요청 작성")
    @WithMockUser(username = "mminl@naver.cm", roles = "USER")
    @WithUserDetails()
    public void add_() throws Exception {
        // given
        EditWriteReqDTO editWriteReqDTO = EditWriteReqDTO.builder()
                .docsId(1L)
                .docsRequestType(DocsRequestType.MODIFIED)
                .docsRequestName("ds")
                .docsRequestLocation(new DocsLocation(12.0, 321.3))
                .docsRequestCategory(DocsCategory.CAFE)
                .build();

        Member member = Member.builder()
                .email("momo@naver.com")
                .nickName("mmmm")
                .password("1235!dasd")
                .role(MemberRole.USER)
                .build();

        String requestBody = om.writeValueAsString(editWriteReqDTO);
        System.out.println("테스트 : " + requestBody);

        // stub
//        Mockito.when(docsRequestService.createModifiedRequest(new MemberDetails(member), editWriteReqDTO)).thenReturn(null);

        // when
        ResultActions result = mvc.perform(
                MockMvcRequestBuilders
                        .post("/requests/update")
                        .content(requestBody)
                        .contentType(MediaType.APPLICATION_JSON)
        );

        String responseBody = result.andReturn().getResponse().getContentAsString(StandardCharsets.UTF_8);
        System.out.println("테스트 : " + responseBody);

        // then
    }


}
