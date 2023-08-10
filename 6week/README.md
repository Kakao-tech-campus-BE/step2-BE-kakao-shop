# step2-BE-kakao-shop
카카오 테크 캠퍼스 2단계 카카오 쇼핑하기 백엔드 클론 프로젝트 레포지토리입니다.

</br>

# 6주차
카카오 테크 캠퍼스 2단계 - BE - 6주차 클론 과제</br>

</br>

## **과제명**
```
1. 카카오 클라우드 배포
```

## **과제 설명**
```
1. 통합테스트를 구현하시오.
2. API문서를 구현하시오.
3. 프론트엔드의 입장을 생각해 본 뒤 어떤 문서를 가장 원할지 생각하면서 API문서를 작성하시오.
4. 카카오 클라우드에 배포하시오.
```

## **과제 상세 : 수강생들이 과제를 진행할 때, 유념해야할 것**
아래 항목은 반드시 포함하여 과제 수행해주세요!
>- 통합테스트가 구현되었는가?
>- API문서가 구현되었는가?
>- 배포가 정상적으로 되었는가?
>- 프로그램이 정상 작동되고 있는가?
>- API 문서에 실패 예시가 작성되었는가?

</br>

## **1. 통합테스트를 구현하시오.**

```
(기능 1) 회원 가입
- 회원가입 페이지에서 form을 채우고 회원가입 버튼을 누를 때 해당 API를 호출한다.
- Method: POST
- URL: http://localhost:8080/join
```
<details>
<summary>Test</summary>
	
  - Normal Case
      ```java
      @Test
      @DisplayName("(기능 1) 회원 가입")
      public void join_test() throws Exception {
          // given
          UserRequest.JoinDTO requestDTO = new UserRequest.JoinDTO();
          requestDTO.setEmail("testmango@nate.com");
          requestDTO.setPassword("test1234!");
          requestDTO.setUsername("testmango");
          String requestBody = om.writeValueAsString(requestDTO);
      
          // when
          ResultActions resultActions = mvc.perform(
                  post("/join")
                          .content(requestBody)
                          .contentType(MediaType.APPLICATION_JSON)
          );
          String responseBody = resultActions.andReturn().getResponse().getContentAsString();
          System.out.println("테스트 : " + responseBody);
      
          // then
          resultActions.andExpect(status().isOk());
          resultActions.andExpect(jsonPath("$.success").value("true"));
          resultActions.andExpect(jsonPath("$.response").value(IsNull.nullValue()));
          resultActions.andExpect(jsonPath("$.error").value(IsNull.nullValue()));
          resultActions.andDo(print()).andDo(document);
      }
      ```
      
  - Failure Case 1
      ```java
      @Test
      public void join_fail_test1() throws Exception {
          // given
          UserRequest.JoinDTO requestDTO = new UserRequest.JoinDTO();
          requestDTO.setEmail("testmango.nate.com");
          requestDTO.setPassword("test1234!");
          requestDTO.setUsername("testmango");
          String requestBody = om.writeValueAsString(requestDTO);
      
          // when
          ResultActions resultActions = mvc.perform(
                  post("/join")
                          .content(requestBody)
                          .contentType(MediaType.APPLICATION_JSON)
          );
          String responseBody = resultActions.andReturn().getResponse().getContentAsString();
          System.out.println("테스트 : " + responseBody);
      
          // then
          resultActions.andExpect(status().isBadRequest());
          resultActions.andExpect(jsonPath("$.success").value("false"));
          resultActions.andExpect(jsonPath("$.response").value(IsNull.nullValue()));
          resultActions.andExpect(jsonPath("$.error.message").value("이메일 형식으로 작성해주세요.:email"));
          resultActions.andExpect(jsonPath("$.error.status").value(400));
          resultActions.andDo(print()).andDo(document);
      }
      ```
      
  - Failure Case 2
      ```java
      @Test
      public void join_fail_test2() throws Exception {
          // given
          UserRequest.JoinDTO requestDTO = new UserRequest.JoinDTO();
          requestDTO.setEmail("testmango@nate.com");
          requestDTO.setPassword("test12!");
          requestDTO.setUsername("testmango");
          String requestBody = om.writeValueAsString(requestDTO);
      
          // when
          ResultActions resultActions = mvc.perform(
                  post("/join")
                          .content(requestBody)
                          .contentType(MediaType.APPLICATION_JSON)
          );
          String responseBody = resultActions.andReturn().getResponse().getContentAsString();
          System.out.println("테스트 : " + responseBody);
      
          // then
          resultActions.andExpect(status().isBadRequest());
          resultActions.andExpect(jsonPath("$.success").value("false"));
          resultActions.andExpect(jsonPath("$.response").value(IsNull.nullValue()));
          resultActions.andExpect(jsonPath("$.error.message").value("8에서 20자 이내여야 합니다.:password"));
          resultActions.andExpect(jsonPath("$.error.status").value(400));
          resultActions.andDo(print()).andDo(document);
      }
      ```
      
  - Failure Case 3
      ```java
      @Test
      public void join_fail_test3() throws Exception {
          // given
          UserRequest.JoinDTO requestDTO = new UserRequest.JoinDTO();
          requestDTO.setEmail("testmango@nate.com");
          requestDTO.setPassword("test1234");
          requestDTO.setUsername("testmango");
          String requestBody = om.writeValueAsString(requestDTO);
      
          // when
          ResultActions resultActions = mvc.perform(
                  post("/join")
                          .content(requestBody)
                          .contentType(MediaType.APPLICATION_JSON)
          );
          String responseBody = resultActions.andReturn().getResponse().getContentAsString();
          System.out.println("테스트 : " + responseBody);
      
          // then
          resultActions.andExpect(status().isBadRequest());
          resultActions.andExpect(jsonPath("$.success").value("false"));
          resultActions.andExpect(jsonPath("$.response").value(IsNull.nullValue()));
          resultActions.andExpect(jsonPath("$.error.message").value("영문, 숫자, 특수문자가 포함되어야하고 공백이 포함될 수 없습니다.:password"));
          resultActions.andExpect(jsonPath("$.error.status").value(400));
          resultActions.andDo(print()).andDo(document);
      }
      ```
      
  - Failure Case 4
      ```java
      @Test
      public void join_fail_test4() throws Exception {
          // given
          UserRequest.JoinDTO requestDTO = new UserRequest.JoinDTO();
          requestDTO.setEmail("testmango@nate.com");
          requestDTO.setPassword("test1234!");
          requestDTO.setUsername("test");
          String requestBody = om.writeValueAsString(requestDTO);
      
          // when
          ResultActions resultActions = mvc.perform(
                  post("/join")
                          .content(requestBody)
                          .contentType(MediaType.APPLICATION_JSON)
          );
          String responseBody = resultActions.andReturn().getResponse().getContentAsString();
          System.out.println("테스트 : " + responseBody);
      
          // then
          resultActions.andExpect(status().isBadRequest());
          resultActions.andExpect(jsonPath("$.success").value("false"));
          resultActions.andExpect(jsonPath("$.response").value(IsNull.nullValue()));
          resultActions.andExpect(jsonPath("$.error.message").value("8에서 45자 이내여야 합니다.:username"));
          resultActions.andExpect(jsonPath("$.error.status").value(400));
          resultActions.andDo(print()).andDo(document);
      }
      ```
        
</details>

```
(기능 2) 로그인
- 로그인 페이지에서 이메일 비밀번호를 채우고 로그인 버튼을 누를 때 해당 API를 호출한다.
- Method: POST
- URL: http://localhost:8080/login
```
<details>
<summary>Test</summary>
	
  - Normal Case
      ```java
      @Test
      @DisplayName("(기능 2) 로그인")
      public void login_test() throws Exception {
          // given
          UserRequest.LoginDTO requestDTO = new UserRequest.LoginDTO();
          requestDTO.setEmail("ssarmango@nate.com");
          requestDTO.setPassword("meta1234!");
          String requestBody = om.writeValueAsString(requestDTO);
          User user = User.builder().id(1).roles("ROLE_USER").build();
          String jwt = JWTProvider.create(user);
      
          // when
          ResultActions resultActions = mvc.perform(
                  post("/login")
                          .content(requestBody)
                          .contentType(MediaType.APPLICATION_JSON)
          );
          String responseHeader = resultActions.andReturn().getResponse().getHeader(JWTProvider.HEADER);
          String responseBody = resultActions.andReturn().getResponse().getContentAsString();
          System.out.println("테스트 : " + responseHeader);
          System.out.println("테스트 : " + responseBody);
      
          // then
          Assertions.assertTrue(jwt.startsWith(JWTProvider.TOKEN_PREFIX));
          resultActions.andExpect(status().isOk());
          resultActions.andExpect(jsonPath("$.success").value("true"));
          resultActions.andExpect(jsonPath("$.response").value(IsNull.nullValue()));
          resultActions.andExpect(jsonPath("$.error").value(IsNull.nullValue()));
          resultActions.andDo(print()).andDo(document);
      }
      ```
      
  - Failure Case 1
      ```java
      @Test
      public void login_fail_test1() throws Exception {
          // given
          UserRequest.LoginDTO requestDTO = new UserRequest.LoginDTO();
          requestDTO.setEmail("ssarmango.nate.com");
          requestDTO.setPassword("meta1234!");
          String requestBody = om.writeValueAsString(requestDTO);
          User user = User.builder().id(1).roles("ROLE_USER").build();
          String jwt = JWTProvider.create(user);
      
          // when
          ResultActions resultActions = mvc.perform(
                  post("/login")
                          .content(requestBody)
                          .contentType(MediaType.APPLICATION_JSON)
          );
          String responseHeader = resultActions.andReturn().getResponse().getHeader(JWTProvider.HEADER);
          String responseBody = resultActions.andReturn().getResponse().getContentAsString();
          System.out.println("테스트 : " + responseHeader);
          System.out.println("테스트 : " + responseBody);
      
          // then
          Assertions.assertTrue(jwt.startsWith(JWTProvider.TOKEN_PREFIX));
          resultActions.andExpect(status().isBadRequest());
          resultActions.andExpect(jsonPath("$.success").value("false"));
          resultActions.andExpect(jsonPath("$.response").value(IsNull.nullValue()));
          resultActions.andExpect(jsonPath("$.error.message").value("이메일 형식으로 작성해주세요.:email"));
          resultActions.andExpect(jsonPath("$.error.status").value(400));
          resultActions.andDo(print()).andDo(document);
      }
      ```
      
  - Failure Case 2
      ```java
      @Test
      public void login_fail_test2() throws Exception {
          // given
          UserRequest.LoginDTO requestDTO = new UserRequest.LoginDTO();
          requestDTO.setEmail("ssarmango@nate.com");
          requestDTO.setPassword("meta12!");
          String requestBody = om.writeValueAsString(requestDTO);
          User user = User.builder().id(1).roles("ROLE_USER").build();
          String jwt = JWTProvider.create(user);
      
          // when
          ResultActions resultActions = mvc.perform(
                  post("/login")
                          .content(requestBody)
                          .contentType(MediaType.APPLICATION_JSON)
          );
          String responseHeader = resultActions.andReturn().getResponse().getHeader(JWTProvider.HEADER);
          String responseBody = resultActions.andReturn().getResponse().getContentAsString();
          System.out.println("테스트 : " + responseHeader);
          System.out.println("테스트 : " + responseBody);
      
          // then
          Assertions.assertTrue(jwt.startsWith(JWTProvider.TOKEN_PREFIX));
          resultActions.andExpect(status().isBadRequest());
          resultActions.andExpect(jsonPath("$.success").value("false"));
          resultActions.andExpect(jsonPath("$.response").value(IsNull.nullValue()));
          resultActions.andExpect(jsonPath("$.error.message").value("8에서 20자 이내여야 합니다.:password"));
          resultActions.andExpect(jsonPath("$.error.status").value(400));
          resultActions.andDo(print()).andDo(document);
      }
      ```
      
  - Failure Case 3
      ```java
      @Test
      public void login_fail_test3() throws Exception {
          // given
          UserRequest.LoginDTO requestDTO = new UserRequest.LoginDTO();
          requestDTO.setEmail("ssarmango@nate.com");
          requestDTO.setPassword("meta1234");
          String requestBody = om.writeValueAsString(requestDTO);
          User user = User.builder().id(1).roles("ROLE_USER").build();
          String jwt = JWTProvider.create(user);
      
          // when
          ResultActions resultActions = mvc.perform(
                  post("/login")
                          .content(requestBody)
                          .contentType(MediaType.APPLICATION_JSON)
          );
          String responseHeader = resultActions.andReturn().getResponse().getHeader(JWTProvider.HEADER);
          String responseBody = resultActions.andReturn().getResponse().getContentAsString();
          System.out.println("테스트 : " + responseHeader);
          System.out.println("테스트 : " + responseBody);
      
          // then
          Assertions.assertTrue(jwt.startsWith(JWTProvider.TOKEN_PREFIX));
          resultActions.andExpect(status().isBadRequest());
          resultActions.andExpect(jsonPath("$.success").value("false"));
          resultActions.andExpect(jsonPath("$.response").value(IsNull.nullValue()));
          resultActions.andExpect(jsonPath("$.error.message").value("영문, 숫자, 특수문자가 포함되어야하고 공백이 포함될 수 없습니다.:password"));
          resultActions.andExpect(jsonPath("$.error.status").value(400));
          resultActions.andDo(print()).andDo(document);
      }
      ```
      
  - Failure Case 4
      ```java
      @Test
      public void login_fail_test4() throws Exception {
          // given
          UserRequest.LoginDTO requestDTO = new UserRequest.LoginDTO();
          requestDTO.setEmail("ssarmango12@nate.com");
          requestDTO.setPassword("meta1234!");
          String requestBody = om.writeValueAsString(requestDTO);
          User user = User.builder().id(1).roles("ROLE_USER").build();
          String jwt = JWTProvider.create(user);
      
          // when
          ResultActions resultActions = mvc.perform(
                  post("/login")
                          .content(requestBody)
                          .contentType(MediaType.APPLICATION_JSON)
          );
          String responseHeader = resultActions.andReturn().getResponse().getHeader(JWTProvider.HEADER);
          String responseBody = resultActions.andReturn().getResponse().getContentAsString();
          System.out.println("테스트 : " + responseHeader);
          System.out.println("테스트 : " + responseBody);
      
          // then
          Assertions.assertTrue(jwt.startsWith(JWTProvider.TOKEN_PREFIX));
          resultActions.andExpect(status().isBadRequest());
          resultActions.andExpect(jsonPath("$.success").value("false"));
          resultActions.andExpect(jsonPath("$.response").value(IsNull.nullValue()));
          resultActions.andExpect(jsonPath("$.error.message").value("이메일을 찾을 수 없습니다.:ssarmango12@nate.com"));
          resultActions.andExpect(jsonPath("$.error.status").value(400));
          resultActions.andDo(print()).andDo(document);
      }
      ```
      
  - Failure Case 5
      ```java
      @Test
      public void login_fail_test5() throws Exception {
          // given
          UserRequest.LoginDTO requestDTO = new UserRequest.LoginDTO();
          requestDTO.setEmail("ssarmango@nate.com");
          requestDTO.setPassword("meta123456!");
          String requestBody = om.writeValueAsString(requestDTO);
          User user = User.builder().id(1).roles("ROLE_USER").build();
          String jwt = JWTProvider.create(user);
      
          // when
          ResultActions resultActions = mvc.perform(
                  post("/login")
                          .content(requestBody)
                          .contentType(MediaType.APPLICATION_JSON)
          );
          String responseHeader = resultActions.andReturn().getResponse().getHeader(JWTProvider.HEADER);
          String responseBody = resultActions.andReturn().getResponse().getContentAsString();
          System.out.println("테스트 : " + responseHeader);
          System.out.println("테스트 : " + responseBody);
      
          // then
          Assertions.assertTrue(jwt.startsWith(JWTProvider.TOKEN_PREFIX));
          resultActions.andExpect(status().isBadRequest());
          resultActions.andExpect(jsonPath("$.success").value("false"));
          resultActions.andExpect(jsonPath("$.response").value(IsNull.nullValue()));
          resultActions.andExpect(jsonPath("$.error.message").value("패스워드가 잘못 입력되었습니다."));
          resultActions.andExpect(jsonPath("$.error.status").value(400));
          resultActions.andDo(print()).andDo(document);
      }
      ```
        
</details>

```
(기능 +) 이메일 중복 체크
- 해당 API를 호출하여 입력한 이메일의 중복 여부를 확인한다.
- Method: POST
- URL: http://localhost:8080/check
```
<details>
<summary>Test</summary>
	
  - Normal Case
      ```java
      @Test
      @DisplayName("(기능 +) 이메일 중복 체크")
      public void check_test() throws Exception {
          // given
          UserRequest.EmailCheckDTO requestDTO = new UserRequest.EmailCheckDTO();
          requestDTO.setEmail("testmango@nate.com");
          String requestBody = om.writeValueAsString(requestDTO);
      
          // when
          ResultActions resultActions = mvc.perform(
                  post("/check")
                          .content(requestBody)
                          .contentType(MediaType.APPLICATION_JSON)
          );
          String responseBody = resultActions.andReturn().getResponse().getContentAsString();
          System.out.println("테스트 : " + responseBody);
      
          // then
          resultActions.andExpect(status().isOk());
          resultActions.andExpect(jsonPath("$.success").value("true"));
          resultActions.andExpect(jsonPath("$.response").value(IsNull.nullValue()));
          resultActions.andExpect(jsonPath("$.error").value(IsNull.nullValue()));
          resultActions.andDo(print()).andDo(document);
      }
      ```
      
  - Failure Case 1
      ```java
      @Test
      public void check_fail_test1() throws Exception {
          // given
          UserRequest.EmailCheckDTO requestDTO = new UserRequest.EmailCheckDTO();
          requestDTO.setEmail("testmango.nate.com");
          String requestBody = om.writeValueAsString(requestDTO);
      
          // when
          ResultActions resultActions = mvc.perform(
                  post("/check")
                          .content(requestBody)
                          .contentType(MediaType.APPLICATION_JSON)
          );
          String responseBody = resultActions.andReturn().getResponse().getContentAsString();
          System.out.println("테스트 : " + responseBody);
      
          // then
          resultActions.andExpect(status().isBadRequest());
          resultActions.andExpect(jsonPath("$.success").value("false"));
          resultActions.andExpect(jsonPath("$.response").value(IsNull.nullValue()));
          resultActions.andExpect(jsonPath("$.error.message").value("이메일 형식으로 작성해주세요.:email"));
          resultActions.andExpect(jsonPath("$.error.status").value(400));
          resultActions.andDo(print()).andDo(document);
      }
      ```
      
  - Failure Case 2
      ```java
      @Test
      public void check_fail_test2() throws Exception {
          // given
          UserRequest.EmailCheckDTO requestDTO = new UserRequest.EmailCheckDTO();
          requestDTO.setEmail("ssarmango@nate.com");
          String requestBody = om.writeValueAsString(requestDTO);
      
          // when
          ResultActions resultActions = mvc.perform(
                  post("/check")
                          .content(requestBody)
                          .contentType(MediaType.APPLICATION_JSON)
          );
          String responseBody = resultActions.andReturn().getResponse().getContentAsString();
          System.out.println("테스트 : " + responseBody);
      
          // then
          resultActions.andExpect(status().isBadRequest());
          resultActions.andExpect(jsonPath("$.success").value("false"));
          resultActions.andExpect(jsonPath("$.response").value(IsNull.nullValue()));
          resultActions.andExpect(jsonPath("$.error.message").value("동일한 이메일이 존재합니다.:ssarmango@nate.com"));
          resultActions.andExpect(jsonPath("$.error.status").value(400));
          resultActions.andDo(print()).andDo(document);
      }
      ```
        
</details>

```
(기능 4) 전체 상품 목록 조회
- 해당 페이지에 들어가면 전체 상품 목록 조회 API를 호출하여 page값에 따라 상품의 데이터를 받아온다.
- Method: GET
- URL: http://localhost:8080/products
- Param : page={number}, 디폴트 값 0
```
<details>
<summary>Test</summary>
	
  - Normal Case
    
      ```java
      @Test
      @DisplayName("(기능 4) 전체 상품 목록 조회")
      public void findAll_test() throws Exception {
          // given
      
          // when
          ResultActions resultActions = mvc.perform(
                  get("/products")
          );
          String responseBody = resultActions.andReturn().getResponse().getContentAsString();
          System.out.println("테스트 : " + responseBody);
      
          // then
          resultActions.andExpect(status().isOk());
          resultActions.andExpect(jsonPath("$.success").value("true"));
          resultActions.andExpect(jsonPath("$.response[0].id").value(1));
          resultActions.andExpect(jsonPath("$.response[0].productName").value("기본에 슬라이딩 지퍼백 크리스마스/플라워에디션 에디션 외 주방용품 특가전"));
          resultActions.andExpect(jsonPath("$.response[0].description").value(""));
          resultActions.andExpect(jsonPath("$.response[0].image").value("/images/1.jpg"));
          resultActions.andExpect(jsonPath("$.response[0].price").value(1000));
          resultActions.andExpect(jsonPath("$.error").value(IsNull.nullValue()));
          resultActions.andDo(print()).andDo(document);
      }
      ```
        
</details>

```
(기능 5) 개별 상품 상세 조회
- 개별 상품 페이지에 들어가면 해당 API를 호출하여 해당 상품 옵션들의 데이터를 받아온다.
- Method: GET
- URL: http://localhost:8080/products/{product_id}
```
<details>
<summary>Test</summary>
	
  - Normal Case
      ```java
      @Test
      @DisplayName("(기능 5) 개별 상품 상세 조회")
      public void findById_test() throws Exception {
          // given
          int productId = 1;
      
          // when
          ResultActions resultActions = mvc.perform(
                  get("/products/" + productId)
          );
          String responseBody = resultActions.andReturn().getResponse().getContentAsString();
          System.out.println("테스트 : " + responseBody);
      
          // then
          resultActions.andExpect(status().isOk());
          resultActions.andExpect(jsonPath("$.success").value("true"));
          resultActions.andExpect(jsonPath("$.response.id").value(1));
          resultActions.andExpect(jsonPath("$.response.productName").value("기본에 슬라이딩 지퍼백 크리스마스/플라워에디션 에디션 외 주방용품 특가전"));
          resultActions.andExpect(jsonPath("$.response.description").value(""));
          resultActions.andExpect(jsonPath("$.response.image").value("/images/1.jpg"));
          resultActions.andExpect(jsonPath("$.response.price").value(1000));
          resultActions.andExpect(jsonPath("$.response.options[0].id").value(1));
          resultActions.andExpect(jsonPath("$.response.options[0].optionName").value("01. 슬라이딩 지퍼백 크리스마스에디션 4종"));
          resultActions.andExpect(jsonPath("$.response.options[0].price").value(10000));
          resultActions.andExpect(jsonPath("$.error").value(IsNull.nullValue()));
          resultActions.andDo(print()).andDo(document);
      }
      ```
      
  - Failure Case
      ```java
      @Test
      public void findById_fail_test() throws Exception {
          // given
          int productId = 10000;
      
          // when
          ResultActions resultActions = mvc.perform(
                  get("/products/" + productId)
          );
          String responseBody = resultActions.andReturn().getResponse().getContentAsString();
          System.out.println("테스트 : " + responseBody);
      
          // then
          resultActions.andExpect(status().isNotFound());
          resultActions.andExpect(jsonPath("$.success").value("false"));
          resultActions.andExpect(jsonPath("$.response").value(IsNull.nullValue()));
          resultActions.andExpect(jsonPath("$.error.message").value("해당 상품을 찾을 수 없습니다.:" + productId));
          resultActions.andExpect(jsonPath("$.error.status").value(404));
          resultActions.andDo(print()).andDo(document);
      }
      ```
        
</details>

```
(기능 8) 장바구니 담기
- 로그인이 필요하며 개별 상품 페이지에서 구입하려는 옵션을 선택한 후 장바구니 버튼을 누르면, 해당 API를 호출한다.
- Method: POST
- URL: http://localhost:8080/carts/add
```
<details>
<summary>Test</summary>
	
  - Normal Case
      ```java
      @WithUserDetails(value = "ssarmango@nate.com")
      @Test
      @DisplayName("(기능 8) 장바구니 담기")
      public void addCartList_test() throws Exception {
          // given
          List<CartRequest.AddDTO> requestDTOs = new ArrayList<>();
          CartRequest.AddDTO item1 = new CartRequest.AddDTO();
          item1.setOptionId(1);
          item1.setQuantity(5);
          requestDTOs.add(item1);
          CartRequest.AddDTO item2 = new CartRequest.AddDTO();
          item2.setOptionId(2);
          item2.setQuantity(5);
          requestDTOs.add(item2);
          String requestBody = om.writeValueAsString(requestDTOs);
      
          // when
          ResultActions resultActions = mvc.perform(
                  post("/carts/add")
                          .content(requestBody)
                          .contentType(MediaType.APPLICATION_JSON)
          );
          String responseBody = resultActions.andReturn().getResponse().getContentAsString();
          System.out.println("테스트 : " + responseBody);
      
          // then
          resultActions.andExpect(status().isOk());
          resultActions.andExpect(jsonPath("$.success").value("true"));
          resultActions.andExpect(jsonPath("$.response").value(IsNull.nullValue()));
          resultActions.andExpect(jsonPath("$.error").value(IsNull.nullValue()));
          resultActions.andDo(print()).andDo(document);
      }
      ```
      
  - Failure Case 1
      ```java
      @WithUserDetails(value = "ssarmango@nate.com")
      @Test
      public void addCartList_fail_test1() throws Exception {
          // given
          List<CartRequest.AddDTO> requestDTOs = new ArrayList<>();
          CartRequest.AddDTO item1 = new CartRequest.AddDTO();
          item1.setOptionId(1);
          item1.setQuantity(3);
          requestDTOs.add(item1);
          CartRequest.AddDTO item2 = new CartRequest.AddDTO();
          item2.setOptionId(1);
          item2.setQuantity(5);
          requestDTOs.add(item2);
          String requestBody = om.writeValueAsString(requestDTOs);
      
          // when
          ResultActions resultActions = mvc.perform(
                  post("/carts/add")
                          .content(requestBody)
                          .contentType(MediaType.APPLICATION_JSON)
          );
          String responseBody = resultActions.andReturn().getResponse().getContentAsString();
          System.out.println("테스트 : " + responseBody);
      
          // then
          resultActions.andExpect(status().isBadRequest());
          resultActions.andExpect(jsonPath("$.success").value("false"));
          resultActions.andExpect(jsonPath("$.response").value(IsNull.nullValue()));
          resultActions.andExpect(jsonPath("$.error.message").value("중복되는 옵션이 존재합니다.:1"));
          resultActions.andExpect(jsonPath("$.error.status").value(400));
          resultActions.andDo(print()).andDo(document);
      }
      ```
      
  - Failure Case 2
      ```java
      @WithUserDetails(value = "ssarmango@nate.com")
      @Test
      public void addCartList_fail_test2() throws Exception {
          // given
          List<CartRequest.AddDTO> requestDTOs = new ArrayList<>();
          CartRequest.AddDTO item = new CartRequest.AddDTO();
          item.setOptionId(10000);
          item.setQuantity(5);
          requestDTOs.add(item);
          String requestBody = om.writeValueAsString(requestDTOs);
      
          // when
          ResultActions resultActions = mvc.perform(
                  post("/carts/add")
                          .content(requestBody)
                          .contentType(MediaType.APPLICATION_JSON)
          );
          String responseBody = resultActions.andReturn().getResponse().getContentAsString();
          System.out.println("테스트 : " + responseBody);
      
          // then
          resultActions.andExpect(status().isNotFound());
          resultActions.andExpect(jsonPath("$.success").value("false"));
          resultActions.andExpect(jsonPath("$.response").value(IsNull.nullValue()));
          resultActions.andExpect(jsonPath("$.error.message").value("해당 옵션을 찾을 수 없습니다.:10000"));
          resultActions.andExpect(jsonPath("$.error.status").value(404));
          resultActions.andDo(print()).andDo(document);
      }
      ```
        
</details>

```
(기능 9) 장바구니 조회
- 로그인이 필요하며 장바구니 보기 페이지에서 해당 API를 호출하여 현재 유저의 장바구니에 담긴 데이터를 받아온다.
- Method: GET
- URL: http://localhost:8080/carts
```
<details>
<summary>Test</summary>
	
  - Normal Case
    
      ```java
      @WithUserDetails(value = "ssarmango@nate.com")
      @Test
      @DisplayName("(기능 9) 장바구니 조회")
      public void findAll_test() throws Exception {
          // given
      
          // when
          ResultActions resultActions = mvc.perform(
                  get("/carts")
          );
          String responseBody = resultActions.andReturn().getResponse().getContentAsString();
          System.out.println("테스트 : " + responseBody);
      
          // then
          resultActions.andExpect(status().isOk());
          resultActions.andExpect(jsonPath("$.success").value("true"));
          resultActions.andExpect(jsonPath("$.response.products[0].id").value(1));
          resultActions.andExpect(jsonPath("$.response.products[0].productName").value("기본에 슬라이딩 지퍼백 크리스마스/플라워에디션 에디션 외 주방용품 특가전"));
          resultActions.andExpect(jsonPath("$.response.products[0].carts[0].id").value(1));
          resultActions.andExpect(jsonPath("$.response.products[0].carts[0].option.id").value(1));
          resultActions.andExpect(jsonPath("$.response.products[0].carts[0].option.optionName").value("01. 슬라이딩 지퍼백 크리스마스에디션 4종"));
          resultActions.andExpect(jsonPath("$.response.products[0].carts[0].option.price").value(10000));
          resultActions.andExpect(jsonPath("$.response.products[0].carts[0].quantity").value(5));
          resultActions.andExpect(jsonPath("$.response.products[0].carts[0].price").value(50000));
          resultActions.andExpect(jsonPath("$.response.totalPrice").value(310900));
          resultActions.andExpect(jsonPath("$.error").value(IsNull.nullValue()));
          resultActions.andDo(print()).andDo(document);
      }
      ```
        
</details>

```
(기능 11) 주문
- 로그인이 필요하며 장바구니 보기 페이지에서 주문하기 버튼을 누르면, 해당 API를 호출한다.
- Method: POST
- URL: http://localhost:8080/carts/update
```
<details>
<summary>Test</summary>
	
  - Normal Case
      ```java
      @WithUserDetails(value = "ssarmango@nate.com")
      @Test
      @DisplayName("(기능 11) 주문")
      public void updateCartList_test() throws Exception {
          // given
          List<CartRequest.UpdateDTO> requestDTOs = new ArrayList<>();
          CartRequest.UpdateDTO item1 = new CartRequest.UpdateDTO();
          item1.setCartId(1);
          item1.setQuantity(10);
          requestDTOs.add(item1);
          CartRequest.UpdateDTO item2 = new CartRequest.UpdateDTO();
          item2.setCartId(2);
          item2.setQuantity(10);
          requestDTOs.add(item2);
          String requestBody = om.writeValueAsString(requestDTOs);
      
          // when
          ResultActions resultActions = mvc.perform(
                  post("/carts/update")
                          .content(requestBody)
                          .contentType(MediaType.APPLICATION_JSON)
          );
          String responseBody = resultActions.andReturn().getResponse().getContentAsString();
          System.out.println("테스트 : " + responseBody);
      
          // then
          resultActions.andExpect(status().isOk());
          resultActions.andExpect(jsonPath("$.success").value("true"));
          resultActions.andExpect(jsonPath("$.response.carts[0].cartId").value(1));
          resultActions.andExpect(jsonPath("$.response.carts[0].optionId").value(1));
          resultActions.andExpect(jsonPath("$.response.carts[0].optionName").value("01. 슬라이딩 지퍼백 크리스마스에디션 4종"));
          resultActions.andExpect(jsonPath("$.response.carts[0].quantity").value(10));
          resultActions.andExpect(jsonPath("$.response.carts[0].price").value(100000));
          resultActions.andExpect(jsonPath("$.response.totalPrice").value(459000));
          resultActions.andExpect(jsonPath("$.error").value(IsNull.nullValue()));
          resultActions.andDo(print()).andDo(document);
      }
      ```
      
  - Failure Case 1
      ```java
      @WithUserDetails(value = "ssarmango@nate.com")
      @Test
      public void updateCartList_fail_test1() throws Exception {
          // given
          List<CartRequest.UpdateDTO> requestDTOs = new ArrayList<>();
          String requestBody = om.writeValueAsString(requestDTOs);
      
          // when
          mvc.perform(
                  post("/orders/save")
                          .contentType(MediaType.APPLICATION_JSON)
          );
          ResultActions resultActions = mvc.perform(
                  post("/carts/update")
                          .content(requestBody)
                          .contentType(MediaType.APPLICATION_JSON)
          );
          String responseBody = resultActions.andReturn().getResponse().getContentAsString();
          System.out.println("테스트 : " + responseBody);
      
          // then
          resultActions.andExpect(status().isNotFound());
          resultActions.andExpect(jsonPath("$.success").value("false"));
          resultActions.andExpect(jsonPath("$.response").value(IsNull.nullValue()));
          resultActions.andExpect(jsonPath("$.error.message").value("장바구니가 비어있습니다."));
          resultActions.andExpect(jsonPath("$.error.status").value(404));
          resultActions.andDo(print()).andDo(document);
      }
      ```
      
  - Failure Case 2
      ```java
      @WithUserDetails(value = "ssarmango@nate.com")
      @Test
      public void updateCartList_fail_test2() throws Exception {
          // given
          List<CartRequest.UpdateDTO> requestDTOs = new ArrayList<>();
          CartRequest.UpdateDTO item1 = new CartRequest.UpdateDTO();
          item1.setCartId(1);
          item1.setQuantity(3);
          requestDTOs.add(item1);
          CartRequest.UpdateDTO item2 = new CartRequest.UpdateDTO();
          item2.setCartId(1);
          item2.setQuantity(5);
          requestDTOs.add(item2);
          String requestBody = om.writeValueAsString(requestDTOs);
      
          // when
          ResultActions resultActions = mvc.perform(
                  post("/carts/update")
                          .content(requestBody)
                          .contentType(MediaType.APPLICATION_JSON)
          );
          String responseBody = resultActions.andReturn().getResponse().getContentAsString();
          System.out.println("테스트 : " + responseBody);
      
          // then
          resultActions.andExpect(status().isBadRequest());
          resultActions.andExpect(jsonPath("$.success").value("false"));
          resultActions.andExpect(jsonPath("$.response").value(IsNull.nullValue()));
          resultActions.andExpect(jsonPath("$.error.message").value("동일한 장바구니 아이디를 주문할 수 없습니다.:1"));
          resultActions.andExpect(jsonPath("$.error.status").value(400));
          resultActions.andDo(print()).andDo(document);
      }
      ```
      
  - Failure Case 3
      ```java
      @WithUserDetails(value = "ssarmango@nate.com")
      @Test
      public void updateCartList_fail_test3() throws Exception {
          // given
          List<CartRequest.UpdateDTO> requestDTOs = new ArrayList<>();
          CartRequest.UpdateDTO item = new CartRequest.UpdateDTO();
          item.setCartId(10000);
          item.setQuantity(5);
          requestDTOs.add(item);
          String requestBody = om.writeValueAsString(requestDTOs);
      
          // when
          ResultActions resultActions = mvc.perform(
                  post("/carts/update")
                          .content(requestBody)
                          .contentType(MediaType.APPLICATION_JSON)
          );
          String responseBody = resultActions.andReturn().getResponse().getContentAsString();
          System.out.println("테스트 : " + responseBody);
      
          // then
          resultActions.andExpect(status().isBadRequest());
          resultActions.andExpect(jsonPath("$.success").value("false"));
          resultActions.andExpect(jsonPath("$.response").value(IsNull.nullValue()));
          resultActions.andExpect(jsonPath("$.error.message").value("장바구니에 없는 상품은 주문할 수 없습니다.:10000"));
          resultActions.andExpect(jsonPath("$.error.status").value(400));
          resultActions.andDo(print()).andDo(document);
      }
      ```
        
</details>

```
(기능 12) 결제
- 로그인이 필요하며 상품 주문 및 결제 페이지에서 결제하기 버튼을 누르면, 해당 API를 호출한다.
- Method: POST
- URL: http://localhost:8080/orders/save
```
<details>
<summary>Test</summary>
	
  - Normal Case
      ```java
      @WithUserDetails(value = "ssarmango@nate.com")
      @Test
      @DisplayName("(기능 12) 결제")
      public void saveCartList_test() throws Exception {
          // given
      
          // when
          ResultActions resultActions = mvc.perform(
                  post("/orders/save")
                          .contentType(MediaType.APPLICATION_JSON)
          );
          String responseBody = resultActions.andReturn().getResponse().getContentAsString();
          System.out.println("테스트 : " + responseBody);
      
          // then
          resultActions.andExpect(status().isOk());
          resultActions.andExpect(jsonPath("$.success").value("true"));
          resultActions.andExpect(jsonPath("$.response.id").value(2));
          resultActions.andExpect(jsonPath("$.response.products[0].productName").value("기본에 슬라이딩 지퍼백 크리스마스/플라워에디션 에디션 외 주방용품 특가전"));
          resultActions.andExpect(jsonPath("$.response.products[0].items[0].id").value(4));
          resultActions.andExpect(jsonPath("$.response.products[0].items[0].optionName").value("01. 슬라이딩 지퍼백 크리스마스에디션 4종"));
          resultActions.andExpect(jsonPath("$.response.products[0].items[0].quantity").value(5));
          resultActions.andExpect(jsonPath("$.response.products[0].items[0].price").value(50000));
          resultActions.andExpect(jsonPath("$.response.totalPrice").value(310900));
          resultActions.andExpect(jsonPath("$.error").value(IsNull.nullValue()));
          resultActions.andDo(print()).andDo(document);
      }
      ```
      
  - Failure Case
      ```java
      @WithUserDetails(value = "ssarmango@nate.com")
      @Test
      public void saveCartList_fail_test() throws Exception {
          // given
      
          // when
          mvc.perform(
                  post("/orders/save")
                          .contentType(MediaType.APPLICATION_JSON)
          );
          ResultActions resultActions = mvc.perform(
                  post("/orders/save")
                          .contentType(MediaType.APPLICATION_JSON)
          );
          String responseBody = resultActions.andReturn().getResponse().getContentAsString();
          System.out.println("테스트 : " + responseBody);
      
          // then
          resultActions.andExpect(status().isNotFound());
          resultActions.andExpect(jsonPath("$.success").value("false"));
          resultActions.andExpect(jsonPath("$.response").value(IsNull.nullValue()));
          resultActions.andExpect(jsonPath("$.error.message").value("장바구니가 비어있습니다."));
          resultActions.andExpect(jsonPath("$.error.status").value(404));
          resultActions.andDo(print()).andDo(document);
      }
      ```
        
</details>

```
(기능 13) 주문 결과 확인
- 유저가 주문에 대해 성공적으로 결제하고 나면 주문 결과 확인 페이지에서 해당 API를 호출한다.
- Method: GET
- URL: http://localhost:8080/orders/{order_id}
```
<details>
<summary>Test</summary>
	
  - Normal Case
      ```java
      @WithUserDetails(value = "ssarmango@nate.com")
      @Test
      @DisplayName("(기능 13) 주문 결과 확인")
      public void findById_test() throws Exception {
          // given
          int orderId = 1;
      
          // when
          ResultActions resultActions = mvc.perform(
                  get("/orders/" + orderId)
          );
          String responseBody = resultActions.andReturn().getResponse().getContentAsString();
          System.out.println("테스트 : " + responseBody);
      
          // then
          resultActions.andExpect(status().isOk());
          resultActions.andExpect(jsonPath("$.success").value("true"));
          resultActions.andExpect(jsonPath("$.response.id").value(1));
          resultActions.andExpect(jsonPath("$.response.products[0].productName").value("기본에 슬라이딩 지퍼백 크리스마스/플라워에디션 에디션 외 주방용품 특가전"));
          resultActions.andExpect(jsonPath("$.response.products[0].items[0].id").value(1));
          resultActions.andExpect(jsonPath("$.response.products[0].items[0].optionName").value("01. 슬라이딩 지퍼백 크리스마스에디션 4종"));
          resultActions.andExpect(jsonPath("$.response.products[0].items[0].quantity").value(5));
          resultActions.andExpect(jsonPath("$.response.products[0].items[0].price").value(50000));
          resultActions.andExpect(jsonPath("$.response.totalPrice").value(310900));
          resultActions.andExpect(jsonPath("$.error").value(IsNull.nullValue()));
          resultActions.andDo(print()).andDo(document);
      }
      ```
      
  - Failure Case
      ```java
      @WithUserDetails(value = "ssarmango@nate.com")
      @Test
      public void findById_fail_test() throws Exception {
          // given
          int orderId = 10000;
      
          // when
          ResultActions resultActions = mvc.perform(
                  get("/orders/" + orderId)
          );
          String responseBody = resultActions.andReturn().getResponse().getContentAsString();
          System.out.println("테스트 : " + responseBody);
      
          // then
          resultActions.andExpect(status().isNotFound());
          resultActions.andExpect(jsonPath("$.success").value("false"));
          resultActions.andExpect(jsonPath("$.response").value(IsNull.nullValue()));
          resultActions.andExpect(jsonPath("$.error.message").value("해당 주문을 찾을 수 없습니다.:" + orderId));
          resultActions.andExpect(jsonPath("$.error.status").value(404));
          resultActions.andDo(print()).andDo(document);
      }
      ```
		
</details>

</br>

## **2. API문서를 구현하시오.**

```jsx
= 카카오 쇼핑하기 RestAPI
zxc88kr <https://github.com/zxc88kr>

ifndef::snippets[]
:snippets: ./build/generated-snippets
endif::[]

:user: user-rest-controller-test
:product: product-rest-controller-test
:cart: cart-rest-controller-test
:order: order-rest-controller-test

:toc: left
:toclevels: 4
:source-highlighter: highlightjs
```
<details>
<summary>Code</summary>
    
  ```
  (기능 1) 회원 가입
  - 회원가입 페이지에서 form을 채우고 회원가입 버튼을 누를 때 해당 API를 호출한다.
  - Method: POST
  - URL: http://localhost:8080/join
  ```
  
  ```jsx
  ==== 요청 예시
  include::{snippets}/{user}/join_test/request-body.adoc[]
  
  ==== 응답 예시
  include::{snippets}/{user}/join_test/response-body.adoc[]
  
  ==== 요청 예시 (실패 1)
  include::{snippets}/{user}/join_fail_test1/request-body.adoc[]
  
  ==== 응답 예시 (실패 1)
  include::{snippets}/{user}/join_fail_test1/response-body.adoc[]
  
  ==== 요청 예시 (실패 2)
  include::{snippets}/{user}/join_fail_test2/request-body.adoc[]
  
  ==== 응답 예시 (실패 2)
  include::{snippets}/{user}/join_fail_test2/response-body.adoc[]
  
  ==== 요청 예시 (실패 3)
  include::{snippets}/{user}/join_fail_test3/request-body.adoc[]
  
  ==== 응답 예시 (실패 3)
  include::{snippets}/{user}/join_fail_test3/response-body.adoc[]
  
  ==== 요청 예시 (실패 4)
  include::{snippets}/{user}/join_fail_test4/request-body.adoc[]
  
  ==== 응답 예시 (실패 4)
  include::{snippets}/{user}/join_fail_test4/response-body.adoc[]
  ```
  
  ```
  (기능 2) 로그인
  - 로그인 페이지에서 이메일 비밀번호를 채우고 로그인 버튼을 누를 때 해당 API를 호출한다.
  - Method: POST
  - URL: http://localhost:8080/login
  ```
  
  ```jsx
  ==== 요청 예시
  include::{snippets}/{user}/login_test/request-body.adoc[]
  
  ==== 응답 예시
  include::{snippets}/{user}/login_test/response-body.adoc[]
  
  ==== 요청 예시 (실패 1)
  include::{snippets}/{user}/login_fail_test1/request-body.adoc[]
  
  ==== 응답 예시 (실패 1)
  include::{snippets}/{user}/login_fail_test1/response-body.adoc[]
  
  ==== 요청 예시 (실패 2)
  include::{snippets}/{user}/login_fail_test2/request-body.adoc[]
  
  ==== 응답 예시 (실패 2)
  include::{snippets}/{user}/login_fail_test2/response-body.adoc[]
  
  ==== 요청 예시 (실패 3)
  include::{snippets}/{user}/login_fail_test3/request-body.adoc[]
  
  ==== 응답 예시 (실패 3)
  include::{snippets}/{user}/login_fail_test3/response-body.adoc[]
  
  ==== 요청 예시 (실패 4)
  include::{snippets}/{user}/login_fail_test4/request-body.adoc[]
  
  ==== 응답 예시 (실패 4)
  include::{snippets}/{user}/login_fail_test4/response-body.adoc[]
  
  ==== 요청 예시 (실패 5)
  include::{snippets}/{user}/login_fail_test5/request-body.adoc[]
  
  ==== 응답 예시 (실패 5)
  include::{snippets}/{user}/login_fail_test5/response-body.adoc[]
  ```
  
  ```
  (기능 +) 이메일 중복 체크
  - 해당 API를 호출하여 입력한 이메일의 중복 여부를 확인한다.
  - Method: POST
  - URL: http://localhost:8080/check
  ```
  
  ```jsx
  ==== 요청 예시
  include::{snippets}/{user}/check_test/request-body.adoc[]
  
  ==== 응답 예시
  include::{snippets}/{user}/check_test/response-body.adoc[]
  
  ==== 요청 예시 (실패 1)
  include::{snippets}/{user}/check_fail_test1/request-body.adoc[]
  
  ==== 응답 예시 (실패 1)
  include::{snippets}/{user}/check_fail_test1/response-body.adoc[]
  
  ==== 요청 예시 (실패 2)
  include::{snippets}/{user}/check_fail_test2/request-body.adoc[]
  
  ==== 응답 예시 (실패 2)
  include::{snippets}/{user}/check_fail_test2/response-body.adoc[]
  ```
  
  ```
  (기능 4) 전체 상품 목록 조회
  - 해당 페이지에 들어가면 전체 상품 목록 조회 API를 호출하여 page값에 따라 상품의 데이터를 받아온다.
  - Method: GET
  - URL: http://localhost:8080/products
  - Param : page={number}, 디폴트 값 0
  ```
  
  ```jsx
  ==== 응답 예시
  include::{snippets}/{product}/find-all_test/response-body.adoc[]
  ```
  
  ```
  (기능 5) 개별 상품 상세 조회
  - 개별 상품 페이지에 들어가면 해당 API를 호출하여 해당 상품 옵션들의 데이터를 받아온다.
  - Method: GET
  - URL: http://localhost:8080/products/{product_id}
  ```
  
  ```jsx
  ==== 응답 예시
  include::{snippets}/{product}/find-by-id_test/response-body.adoc[]
  
  ==== 응답 예시 (실패)
  include::{snippets}/{product}/find-by-id_fail_test/response-body.adoc[]
  ```
  
  ```
  (기능 8) 장바구니 담기
  - 로그인이 필요하며 개별 상품 페이지에서 구입하려는 옵션을 선택한 후 장바구니 버튼을 누르면, 해당 API를 호출한다.
  - Method: POST
  - URL: http://localhost:8080/carts/add
  ```
  
  ```jsx
  ==== 요청 예시
  include::{snippets}/{cart}/add-cart-list_test/request-body.adoc[]
  
  ==== 응답 예시
  include::{snippets}/{cart}/add-cart-list_test/response-body.adoc[]
  
  ==== 요청 예시 (실패 1)
  include::{snippets}/{cart}/add-cart-list_fail_test1/request-body.adoc[]
  
  ==== 응답 예시 (실패 1)
  include::{snippets}/{cart}/add-cart-list_fail_test1/response-body.adoc[]
  
  ==== 요청 예시 (실패 2)
  include::{snippets}/{cart}/add-cart-list_fail_test2/request-body.adoc[]
  
  ==== 응답 예시 (실패 2)
  include::{snippets}/{cart}/add-cart-list_fail_test2/response-body.adoc[]
  ```
  
  ```
  (기능 9) 장바구니 조회
  - 로그인이 필요하며 장바구니 보기 페이지에서 해당 API를 호출하여 현재 유저의 장바구니에 담긴 데이터를 받아온다.
  - Method: GET
  - URL: http://localhost:8080/carts
  ```
  
  ```jsx
  ==== 응답 예시
  include::{snippets}/{cart}/find-all_test/response-body.adoc[]
  ```
  
  ```
  (기능 11) 주문
  - 로그인이 필요하며 장바구니 보기 페이지에서 주문하기 버튼을 누르면, 해당 API를 호출한다.
  - Method: POST
  - URL: http://localhost:8080/carts/update
  ```
  
  ```jsx
  ==== 요청 예시
  include::{snippets}/{cart}/update-cart-list_test/request-body.adoc[]
  
  ==== 응답 예시
  include::{snippets}/{cart}/update-cart-list_test/response-body.adoc[]
  
  ==== 요청 예시 (실패 1)
  include::{snippets}/{cart}/update-cart-list_fail_test1/request-body.adoc[]
  
  ==== 응답 예시 (실패 1)
  include::{snippets}/{cart}/update-cart-list_fail_test1/response-body.adoc[]
  
  ==== 요청 예시 (실패 2)
  include::{snippets}/{cart}/update-cart-list_fail_test2/request-body.adoc[]
  
  ==== 응답 예시 (실패 2)
  include::{snippets}/{cart}/update-cart-list_fail_test2/response-body.adoc[]
  
  ==== 요청 예시 (실패 3)
  include::{snippets}/{cart}/update-cart-list_fail_test3/request-body.adoc[]
  
  ==== 응답 예시 (실패 3)
  include::{snippets}/{cart}/update-cart-list_fail_test3/response-body.adoc[]
  ```
  
  ```
  (기능 12) 결제
  - 로그인이 필요하며 상품 주문 및 결제 페이지에서 결제하기 버튼을 누르면, 해당 API를 호출한다.
  - Method: POST
  - URL: http://localhost:8080/orders/save
  ```
  
  ```jsx
  ==== 응답 예시
  include::{snippets}/{order}/save-cart-list_test/response-body.adoc[]
  
  ==== 응답 예시 (실패)
  include::{snippets}/{order}/save-cart-list_fail_test/response-body.adoc[]
  ```
  
  ```
  (기능 13) 주문 결과 확인
  - 유저가 주문에 대해 성공적으로 결제하고 나면 주문 결과 확인 페이지에서 해당 API를 호출한다.
  - Method: GET
  - URL: http://localhost:8080/orders/{order_id}
  ```
  
  ```jsx
  ==== 응답 예시
  include::{snippets}/{order}/find-by-id_test/response-body.adoc[]
  
  ==== 응답 예시 (실패)
  include::{snippets}/{order}/find-by-id_fail_test/response-body.adoc[]
  ```

</details>

</br>

## **3. 프론트엔드의 입장을 생각해 본 뒤 어떤 문서를 가장 원할지 생각하면서 API문서를 작성하시오.**

[카카오 크램폴린 링크](https://user-app.krampoline.com/kd478ef512a20a/api/docs/api-docs.html)

</br>

## **4. 카카오 클라우드에 배포하시오.**

[카카오 크램폴린 링크](https://user-app.krampoline.com/kd478ef512a20a)
