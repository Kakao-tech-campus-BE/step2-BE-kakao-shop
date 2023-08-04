안녕하세요 멘토님, 6주 동안 많은 가르침 주셔서 감사합니다
멘토님 덕분에 많이 성장 할 수 있었습니다

## 어려웠던 점 / 중심적으로 리뷰해주셨으면 하는 부분

주문을 저장하는 부분에서, 비어있는 카트 예외처리 테스트를 하고 싶었습니다.
결국은 SQL에 새로운 유저를 추가해서 테스트를 구현하였습니다.

아래는 제가 시도한 방법입니다. 어떤 방법이 효율적이었을까 질문드립니다!

1. Mockito로 Cart를 주입 받는다
   - 다른 Cart 테스트도 영향을 받는 문제 발생
2. 테스트 전후로 새로운 유저를 생성했다가 지운다
   - 이 경우도 다른 Cart 테스트에서도 새로운 유저를 생성했다 지우는 비효율 발생
3. test Service를 만든다
   - test Service를 만들고, test repository를 만드는게 효율적인가 의문이 들었습니다

```java
// SQL을 추가하지 않고 하는 방법이 없을까? , Mockito나 testService 등을 만들었는데 자원 낭비라 여겨저서 user 생성 SQL을 추가하였다
@WithUserDetails(value = "ssarmango2@nate.com")
@Test
public void save_test_with_empty_cart() throws Exception {

    ResultActions resultActions = mvc.perform(post("/orders/save"));

    String responseBody = resultActions.andReturn().getResponse().getContentAsString();
    System.out.println("save_test_with_empty_cart : " + responseBody);

    resultActions.andExpect(status().isBadRequest()).andExpect(result -> assertTrue(result.getResolvedException() instanceof Exception400)).andExpect(result -> assertEquals("장바구니가 비어있습니다.", result.getResolvedException().getMessage()));
    resultActions.andDo(MockMvcResultHandlers.print()).andDo(document);
}
```

## 필수 제출 사항

[카카오 클라우드 인스턴스 주소](https://user-app.krampoline.com/kd37b805137cda)

[API 문서](https://user-app.krampoline.com/kd37b805137cda/api/docs/api-docs.html)
