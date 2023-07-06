# step2-BE-kakao-shop
카카오 테크 캠퍼스 2단계 카카오 쇼핑하기 백엔드 클론 프로젝트 레포지토리입니다.

# 1주차

카카오 테크 캠퍼스 2단계 - BE - 1주차 클론 과제
</br>
</br>

## **과제명**
```
1. 요구사항분석/API요청 및 응답 시나리오 분석
2. 요구사항 추가 반영 및 테이블 설계도
```

## **과제 설명**
```
1. 요구사항 시나리오를 보고 부족해 보이는 기능을 하나 이상 체크하여 README에 내용을 작성하시오.
2. 제시된 화면설계를 보고 해당 화면설계와 배포된 기존 서버의 API주소를 매칭하여 README에 내용을 작성하시오. (카카오 화면설계 시나리오가 있음)
3. 배포된 서버에 모든 API를 POSTMAN으로 요청해본 뒤 응답되는 데이터를 확인하고 부족한 데이터가 무엇인지 체크하여 README에 내용을 작성하시오.
4. 테이블 설계를 하여 README에 ER-Diagram을 추가하여 제출하시오.
```

## **과제**
### 1. 제시된 화면설계를 보고 요구사항에 맞는 API 분석
![image](https://github.com/wona825/step2-BE-kakao-shop/assets/81519167/1eee9993-0c96-45f9-a123-a9f686d29e73)



</br>

### 2. 배포된 서버에 API를 POSTMAN으로 요청해보고, 부족한 데이터 체크
```
1. URL pattern: /carts/add
   현재 장바구니에 이미 담겨있는 옵션과 같은 것을 담으면, 코드 500과 함께 오류가 발생
   -> 장바구니에 이미 담겨있는 옵션과 같은 것을 담을 때 수량 업데이트 필요
```

![image](https://github.com/wona825/step2-BE-kakao-shop/assets/81519167/931d1e7d-ff8e-405d-b22c-e53e33e6d0c4)

</br>

```
2. URL pattern: /orders/{order_id}
   주문 후 주문 내역 조회에서 주문 시간 등 추가 정보 고려 필요
```

![image](https://github.com/wona825/step2-BE-kakao-shop/assets/81519167/3d162e4a-132e-4104-8c8f-80af0dddf43f)

</br>

```
3. URL pattern: /products/{product_id}
   제품 상세 조회 시 배송비, 예상 소요 시간, 배송 방법 등 배송과 관련된 상세 정보 고려 필요
```
![image](https://github.com/wona825/step2-BE-kakao-shop/assets/81519167/6e279d86-bd1d-4943-ad96-6117a96b0f45)

</br>

### 3. 부족한 기능에 대한 요구사항 예상
**[사용자]**
```
1. 소비자 / 판매자 / 관리자로 사용자 구분 
2. 사용자 타켓팅을 위해 성별, 나이 같은 추가 정보 받아오기 
3. 회원가입할 때 이메일 중복 체크 (이메일 중복 체크 api는 존재하나, 해당 기능을 수행시킬 수 있는 버튼이 없음)
4. 기본 사용자 정보 수정 및 배송지 같은 추가 정보 입력이 가능한 마이페이지 필요
```

**[상품]**
```
1. 상품 인기도/주문 수 보여주기 
2. 상품 조회 시 상품에 대한 추가 상세 정보 보여주기
3. 특정 상품 찜하기
4. 찜한 상품 모아보기
```

**[주문]**
```
1. 장바구니에서 특정 상품을 선택해 삭제하거나 주문하기
2. 주문 취소하기
3. 이전 주문내역 조회
```


</br>


### 4. Entity
**[CartEntity]**
```java
@Entity
@Getter
@Setter
public class CartEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private OptionEntity optionEntity;

    private Integer option_cnt;

    @ManyToOne
    private MemberEnrity member;
}
```

**[MemberEntity]**
```java
@Entity
@Getter
@Setter
public class MemberEnrity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String username;

    private String email;

    private String password;

    private LocalDate createdDate;
}
```

**[OptionEntity]**
```java
@Entity
@Getter
@Setter
public class OptionEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private ProductEntity productEntity;

    private String name;

    private Integer price;
}
```

**[OrderEntity]**
```java
@Entity
@Getter
@Setter
public class OrderEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private MemberEnrity member;

    private LocalDate createdDate;
}
```

**[OrderItemEntity]**
```java
@Entity
@Getter
@Setter
public class OrderItemEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private OptionEntity optionEntity;

    @ManyToOne
    private OrderEntity orderEntity;

    private Integer option_cnt;
}
```

**[ProductEntity]**
```java
@Entity
@Getter
@Setter
public class ProductEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String image;

    private Integer price;

    private LocalDate createdDate;
}
```

</br>

### 5. ER-Diagram
![image](https://github.com/wona825/step2-BE-kakao-shop/assets/81519167/d17eea19-2410-4325-8f32-507087846904)

</br>

</br>

# 2주차

카카오 테크 캠퍼스 2단계 - BE - 2주차 클론 과제
</br>
</br>

## **과제명**
```
1. 전체 API 주소 설계
2. Mock API Controller 구현
```

## **과제 설명**
```
1. API주소를 설계하여 README에 내용을 작성하시오.
2. 가짜 데이터를 설계하여 응답하는 스프링부트 컨트롤러를 작성하고 소스코드를 업로드하시오.
```

</br>

## **과제**
### 1. 제시된 화면설계를 보고 요구사항에 맞는 API 분석
![image](https://github.com/wona825/step2-BE-kakao-shop/assets/81519167/1eee9993-0c96-45f9-a123-a9f686d29e73)

</br>

# 3주차

카카오 테크 캠퍼스 2단계 - BE - 3주차 클론 과제
</br>
</br>

## **과제명**
```
1. 레포지토리 단위테스트
```

## **과제 설명**
```
1. 레포지토리 단위테스트를 구현하여 소스코드를 제출하시오.
2. 쿼리를 테스트하면서 가장 좋은 쿼리를 작성해보시오.
```

</br>

## **과제 상세 : 수강생들이 과제를 진행할 때, 유념해야할 것**
아래 항목은 반드시 포함하여 과제 수행해주세요!
>- 레포지토리 단위테스트가 구현되었는가?
>- 테스트 메서드끼리 유기적으로 연결되지 않았는가? (테스트는 격리성이 필요하다)
>- Persistene Context를 clear하여서 테스트가 구현되었는가? (더미데이터를 JPA를 이용해서 insert 할 예정인데, 레포지토리 테스트시에 영속화된 데이터 때문에 쿼리를 제대로 보지 못할 수 있기 때문에)
>- 테스트 코드의 쿼리 관련된 메서드가 너무 많은 select를 유발하지 않는지? (적절한 한방쿼리, 효율적인 in query, N+1 문제 등이 해결된 쿼리)
</br>

## **코드리뷰 관련: PR시, 아래 내용을 포함하여 코멘트 남겨주세요.**
**1. PR 제목과 내용을 아래와 같이 작성 해주세요.**

>- PR 제목 : 부산대BE_라이언_3주차 과제

</br>

**2. PR 내용 :**

>- 코드 작성하면서 어려웠던 점
>- 코드 리뷰 시, 멘토님이 중점적으로 리뷰해줬으면 하는 부분

# 4주차

카카오 테크 캠퍼스 2단계 - BE - 4주차 클론 과제
</br>
</br>

## **과제명**
```
1. 컨트롤러 단위 테스트
```

## **과제 설명**
```
1. 컨트롤러 단위테스트를 작성한뒤 소스코드를 업로드하시오.
2. stub을 구현하시오.
```

</br>

## **과제 상세 : 수강생들이 과제를 진행할 때, 유념해야할 것**
아래 항목은 반드시 포함하여 과제 수행해주세요!
>- 컨트롤러 단위테스트가 구현되었는가?
>- Mockito를 이용하여 stub을 구현하였는가?
>- 인증이 필요한 컨트롤러를 테스트할 수 있는가?
>- 200 ok만 체크한 것은 아닌가? (해당 컨트롤러에서 제일 필요한 데이터에 대한 테스트가 구현되었는가?)
</br>

## **코드리뷰 관련: PR시, 아래 내용을 포함하여 코멘트 남겨주세요.**
**1. PR 제목과 내용을 아래와 같이 작성 해주세요.**

>- PR 제목 : 부산대BE_라이언_4주차 과제 

</br>

**2. PR 내용 :**

>- 코드 작성하면서 어려웠던 점
>- 코드 리뷰 시, 멘토님이 중점적으로 리뷰해줬으면 하는 부분

# 5주차

카카오 테크 캠퍼스 2단계 - BE - 5주차 클론 과제
</br>
</br>

## **과제명**
```
1. 실패 단위 테스트
```

## **과제 설명**
```
1. 컨트롤러 단위테스트를 구현하는데, 실패 테스트 코드를 구현하시오.
2. 어떤 문제가 발생할 수 있을지 모든 시나리오를 생각해본 뒤, 실패에 대한 모든 테스트를 구현하시오.
```

</br>

## **과제 상세 : 수강생들이 과제를 진행할 때, 유념해야할 것**
아래 항목은 반드시 포함하여 과제 수행해주세요!
>- 실패 단위 테스트가 구현되었는가?
>- 모든 예외에 대한 실패 테스트가 구현되었는가?
</br>

## **코드리뷰 관련: PR시, 아래 내용을 포함하여 코멘트 남겨주세요.**
**1. PR 제목과 내용을 아래와 같이 작성 해주세요.**

>- PR 제목 : 부산대BE_라이언_5주차 과제

</br>

**2. PR 내용 :**

>- 코드 작성하면서 어려웠던 점
>- 코드 리뷰 시, 멘토님이 중점적으로 리뷰해줬으면 하는 부분

# 6주차

카카오 테크 캠퍼스 2단계 - BE - 6주차 클론 과제
</br>
</br>

## **과제명**
```
1. 카카오 클라우드 배포
```

## **과제 설명**
```
1. 통합테스트를 구현하시오.
2. API문서를 구현하시오. (swagger, restdoc, word로 직접 작성, 공책에 적어서 제출 등 모든 방법이 다 가능합니다)
3. 프론트앤드에 입장을 생각해본뒤 어떤 문서를 가장 원할지 생각해본뒤 API문서를 작성하시오.
4. 카카오 클라우드에 배포하시오.
5. 배포한 뒤 서비스 장애가 일어날 수 있으니, 해당 장애에 대처할 수 있게 로그를 작성하시오. (로그는 DB에 넣어도 되고, 외부 라이브러리를 사용해도 되고, 파일로 남겨도 된다 - 단 장애 발생시 확인을 할 수 있어야 한다)
```

</br>

## **과제 상세 : 수강생들이 과제를 진행할 때, 유념해야할 것**
아래 항목은 반드시 포함하여 과제 수행해주세요!
>- 통합테스트가 구현되었는가?
>- API문서가 구현되었는가?
>- 배포가 정상적으로 되었는가?
>- 서비스에 문제가 발생했을 때, 로그를 통해 문제를 확인할 수 있는가?
</br>

## **코드리뷰 관련: PR시, 아래 내용을 포함하여 코멘트 남겨주세요.**
**1. PR 제목과 내용을 아래와 같이 작성 해주세요.**

>- PR 제목 : 부산대BE_라이언_6주차 과제

</br>

**2. PR 내용 :**

>- 코드 작성하면서 어려웠던 점
>- 코드 리뷰 시, 멘토님이 중점적으로 리뷰해줬으면 하는 부분
