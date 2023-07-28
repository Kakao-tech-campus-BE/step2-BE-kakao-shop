# 5주차 과제 - 코드 리팩토링
```
카카오 쇼핑 프로젝트 전체 코드를 리팩토링한다
 - AOP로 유효성검사 적용하기
 - GlobalExceptionHanlder 구현하기
 - 장바구니 담기 -> 예외 처리하기
 - 장바구니 수정(주문하기) -> 예외처리하기
 - 결재하기 기능 구현 (장바구니가 꼭 초기화 되어야함)
 - 주문결과 확인 기능 구현
```

## 과제하면서 궁금했던 점
1. Dependency Injection을 하는 방법이 여러 개를 생각해볼 수 있는데 어떤 게 가장 선호되는지 알고싶습니다. 

   > a. `Constructor Injection`  
   > b. `@RequiredArgsConstructor`  
   > c. `@Autowired`
 
   + 테스트에서는 @Autowired를 쓰는게 Constructor를 쓰는 것보다 좋은가요?
---
2. 주문하기, 주문조회시 다른 ResponseDTO를 사용했는데 주문 조회를 한다는 점에서 같기 때문에 동일한 ResponseDTO를 사용하는게 좋지 않을까? 하는 생각이 들었는데 그럼에도 변경을 고려해서 분리하는게 맞을까요?
---
3. CartController에서 List 내부에 있는 요소에 대한(SaveDTO 등) `Vadliation(NotNull, Min 등)`이 제대로 작동하지 않아 Service 로직에서 다시 유효성 검사를 해줬습니다.
   블로그를 보니 CustomValidation을 만들면 된다고 하는데 이 방법이 Service 로직에서 처리하는 것보다는 나을까요?
   다른 List의 요소에 대해서 Validation 할 수 있는 좋은 방법이 있을까요?
---
4. cartService에서 addCartList 메서드를 for문을 이용해서 update 또는 add를 해주었더니 **주문 수만큼의 query**가 발생해서 효율이 떨어지는 것 같습니다. 이를 최적화시킬 수 있는 방안을 알고 싶습니다
---
5. Cart 추가, 업데이트에서 예외 처리하는 부분이 공통돼서 **중복 코드**가 많이 발생하였습니다. 이는 따로 분리하는게 좋을까요?

## 중점적으로 봐주셨으면 하는 부분
1. cartJPARepository, ItemJPARepository, OrderJPARepository의 쿼리문
2. ItemResponse의 DTO 부분
3. CartService의 예외 처리 부분

## Todo-List

---

- [x] 장바구니 담기 예외처리
- [x] 장바구니 수정(주문하기) 예외처리
- [x] 결제하기 기능
  - [x] 장바구니 초기화
- [x] 주문결과 확인 기능

### 장바구니 수정 예외

> - optionId가 중복된 경우 - 400
> - optionId가 잘못된 경우(존재하지 않음) - 404
> - quantity = 0인 경우- 400


### 장바구니 수정 예외

> - 장바구니에 아무것도 없는 경우
> - cartId가 중복된 경우 - 400
> - cartId가 잘못된 경우 (존재하지 않음) - 404
> - quantity = 0인 경우 - 400