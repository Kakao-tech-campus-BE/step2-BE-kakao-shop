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

</br>

## **과제 상세 : 수강생들이 과제를 진행할 때, 유념해야할 것**
아래 항목은 반드시 포함하여 과제 수행해주세요!
>- 부족한 기능에 대한 요구사항을 미리 예상할 수 있는가? (예를 들면 상품등록 api가 기존 요구사항에는 없는데 추후 필요하지는 않을지, 이런 부분들을 생각하였는지) 
>- 요구사항에 맞는 API를 분석하고 사용자 시나리오를 설계하였는가? (예를 들어 배포된 서버와 화면 설계를 제시해줄 예정인데, 특정 버튼을 클릭했을 때 어떤 API가 호출되어야 할지를 아는지)
>- 응답되는 데이터가 프론트앤드 화면에 모두 반영될 수 있는지를 체크하였는가?(예를 들어 배송관련 비용이 있는데, 이런것들이 API에는 없는데 이런 부분을 캐치할 수 있는지)
>- 테이블 설계가 모든 API를 만족할 수 있게 나왔는가? (테이블이 효율적으로 나왔는가 보다는 해당 테이블로 요구사항을 만족할 수 있는지에 대한 여부만)
>- 테이블명이 이해하기 쉽게 만들어졌는가? (상품테이블이 product이면 이해하기 쉽지만, material이라고 하면 이해하기 어렵기 때문)

</br>

## **코드리뷰 관련: PR시, 아래 내용을 포함하여 코멘트 남겨주세요.**
**1. PR 제목과 내용을 아래와 같이 작성 해주세요.**

>- PR 제목 : 부산대BE_라이언_1주차 과제

</br>

**2. PR 내용 :**

>- 코드 작성하면서 어려웠던 점
>- 코드 리뷰 시, 멘토님이 중점적으로 리뷰해줬으면 하는 부분 


## **과제 수행**

<details>
<summary>요구사항 1</summary>

1. 요구사항 시나리오를 보고 부족해 보이는 기능을 하나 이상 체크하여 README에 내용을 작성하시오.
- 기능 상세 설명
    - 아이디 찾기 페이지
        - FE
            - 이름 / 이메일 입력 후 “로그인 버튼”을 클릭 시 HTTP Request body에 담아 서버로 요청
        - BE
            - Request로 이름 / 이메일을 입력 받아 Service Class로 전달
            - 데이터베이스에서 이름 / 이메일을 조회 후 Service Class에서 유효한 사용자인지 판단
            - 유효한 사용자일 경우 사용자 아이디 반환
    - 비밀번호 찾기 페이지
        - FE
            - 이름 / 이메일 / 아이디 입력 후 “로그인 버튼”을 클릭 시 HTTP Request body에 담아 서버로 요청
        - BE
            - Request로 이름 / 이메일 / 아이디를 입력 받아 Service Class로 전달
            - 데이터베이스에서 이름 / 이메일을 조회 후 Service Class에서 유효한 사용자인지 판단
            - 유효한 사용자일 경우 사용자 비밀번호 변경 페이지로 리턴
- 인터페이스 요구사항
    - 아이디 찾기 페이지
        - 입력
            - 이름, 이메일 입력 후 아이디 찾기 버튼 클릭
            - 사용자 아이디 정보 페이지 리턴
    - 비밀번호 찾기 페이지
        - 입력
            - 이름, 이메일, 아이디 입력 후 비밀번호 찾기 버튼 클릭
        - 출력
            - 사용자 비밀번호 변경 페이지 리턴

</details>

</br>

<details>
<summary>요구사항 2</summary>


2. 제시된 화면설계를 보고 해당 화면설계와 배포된 기존 서버의 API주소를 매칭하여 README에 내용을 작성하시오. (카카오 화면설계 시나리오가 있음)

    👉 Local 서버를 띄우고 Postman으로 API URL 호출하면서 테스트

    
    * 회원 가입 버튼 클릭 시
    ![회원가입](https://github.com/boseungk/TIL/assets/95980754/d6b90d51-7580-4800-9694-5244455fb677)
      * http://kakao-app-env.eba-kfsgeb74.ap-northeast-2.elasticbeanstalk.com/join
    

    * 로그인 버튼 클릭 시
    ![로그인](https://github.com/boseungk/TIL/assets/95980754/e86b71f8-2a81-4583-be1a-b0d5d681aec5)
      * http://kakao-app-env.eba-kfsgeb74.ap-northeast-2.elasticbeanstalk.com/login

    
    * 전체 상품 조회 시
    ![전체 상품](https://github.com/boseungk/TIL/assets/95980754/58420baa-7a07-4e78-b12a-5163162fc6be)
      * http://kakao-app-env.eba-kfsgeb74.ap-northeast-2.elasticbeanstalk.com/products?page=1

    
    * 개별 상품 / 옵션 조회 시
    ![개별 상품](https://github.com/boseungk/TIL/assets/95980754/16d690cf-55f9-47c8-88be-037147edb81f)
      * http://kakao-app-env.eba-kfsgeb74.ap-northeast-2.elasticbeanstalk.com/products/1
  
    
    * 장바구니 담기 버튼 클릭 시 
    ![장바구니 담기](https://github.com/boseungk/TIL/assets/95980754/1142d55f-8e94-4321-aab5-d47d33fd20e4)
      * http://kakao-app-env.eba-kfsgeb74.ap-northeast-2.elasticbeanstalk.com/carts/add
   
   * 장바구니 조회 시
   ![장바구니 조회](https://github.com/boseungk/TIL/assets/95980754/cce205fa-1c55-4316-8672-3af26330359e)
     * http://kakao-app-env.eba-kfsgeb74.ap-northeast-2.elasticbeanstalk.com/carts

    * 장바구니 수량 수정 시
    ![장바구니 수정](https://github.com/boseungk/TIL/assets/95980754/cce205fa-1c55-4316-8672-3af26330359e)
      * http://kakao-app-env.eba-kfsgeb74.ap-northeast-2.elasticbeanstalk.com/carts/update

    * 장바구니 주문하기 버튼 클릭 시
    ![장바구니 수정](https://github.com/boseungk/TIL/assets/95980754/cce205fa-1c55-4316-8672-3af26330359e)
      * http://kakao-app-env.eba-kfsgeb74.ap-northeast-2.elasticbeanstalk.com/carts/update
      * request body 부분이 다름

    * 결제하기 버튼 클릭 시
    ![결제하기](https://github.com/boseungk/TIL/assets/95980754/ba97ee67-f544-49d9-9fb6-7766e9517064)
      * http://kakao-app-env.eba-kfsgeb74.ap-northeast-2.elasticbeanstalk.com/orders/save

    * 주문 조회 시
    ![주문 조회](https://github.com/boseungk/TIL/assets/95980754/314adac8-8935-4980-b2f2-1fd54bd24eba)
      * http://kakao-app-env.eba-kfsgeb74.ap-northeast-2.elasticbeanstalk.com/orders/1

</details>

</br>

<details>
<summary>요구사항 3</summary>

3. 배포된 서버에 모든 API를 POSTMAN으로 요청해본 뒤 응답되는 데이터를 확인하고 부족한 데이터가 무엇인지 체크하여 README에 내용을 작성하시오.

    👉 요구사항 2과 3번을 같이 진행하면서 Postman으로 API를 전부 각각 요청해보았고, 부족한 데이터만 기입

    ![전체 상품](https://github.com/boseungk/TIL/assets/95980754/342f93b4-2eda-4c51-8bce-6ebf58502260)
    * 전체 상품 조회 시 응답되는 데이터
      * {"id": 10,
      "productName": "통영 홍 가리비 2kg, 2세트 구매시 1kg 추가증정",
      "description": "",
      "image": "/images/10.jpg",
      "price": 8900}
    * 무료배송이나 특별가 데이터가 부족함

    ![개별 상품](https://github.com/boseungk/TIL/assets/95980754/44658742-9ea1-4770-a748-9a2f27e80a06)
   * 개별 상품 / 옵션 조회 시
       * {
    "success": true,
    "response": {
        "id": 1,
        "productName": "기본에 슬라이딩 지퍼백 크리스마스/플라워에디션 에디션 외 주방용품 특가전",
        "description": "",
        "image": "/images/1.jpg",
        "price": 1000,
        "starCount": 5,
        
        "options": [
            {
                "id": 1,
                "optionName": "01. 슬라이딩 지퍼백 크리스마스에디션 4종",
                "price": 10000
            }, ...
        ]
    },
    "error": null}
   * 옵션 조회 시 배송비에 대한 데이터가 부족함

</details>

</br>

<details>
<summary>요구사항 4</summary>

4. 테이블 설계를 하여 README에 ER-Diagram을 추가하여 제출하시오.
    
    ![erd](https://github.com/boseungk/TIL/assets/95980754/35a2a042-2554-4fdb-9579-0bb515a48394)

    ```sql
    Create Table product (
    productId int auto_increment primary key not null,
    productName varchar(255) not null,
    picture	varchar(255) not null,
    price int	NOT NULL,
    date	Date	NOT NULL,
    description	varchar(255)	NOT NULL
    );

    Create Table productOption(
    productOptionId int auto_increment primary key not null,
    productOptionName varchar(255) not null,
    price int not null,
    productId int auto_increment not null,
    foreign key (productId) references product(productId)
    );

    Create Table user(
    userId int auto_increment primary key not null,
    userName varchar(10) not null,
    email varchar(255) not null,
    password varchar(255)
    );

    Create Table cart(
    cartId int auto_increment primary key not null,
    optionNum int not null,
    userId int not null,
    productOptionId int not null,
    foreign key(userId) references user(userId),
    foreign key(productOptionId) references productOption(productOptionId)
    );

    Create Table orderItem(
    orderItem int auto_increment primary key not null,
    quantity int not null,
    price int not null,
    ordersId int not null,
    productOptionId int not null,
    foreign key(ordersId) references orders(ordersId),
    foreign key(productOptionId) references productOption(productOptionId)
    );

    Create Table orders(
    ordersId int auto_increment primary key not null,
    userId int not null,
    foreign key(userId) references user(userId)
    );

    ```
    
- **과제 수행 (코드 작성) 하면서 어려웠던 점 (선택)**
    - 처음에는 테이블 설계가 금방 끝날 줄 알았는데, 여러가지 어려움이 있었다.
    - 테이블의 데이터 타입을 선정하느라 어려움을 겪었는데, 어차피 JPA를 사용할 거라는 생각이 들어서 자바 데이터 타입으로 정했다.
    - 테이블 간의 연관관계 매핑에서 어려움을 겪다가 다른 레퍼런스를 찾아보면서 이해하게 되었다.
    - 처음에는 화면 설계도를 보면서 테이블을 설계하다가 이해가 안되는 부분이 생겨서 API를 보면서 다시 테이블을 설계했다.
        - 예를 들어서 화면 설계도만 보고 Cart 테이블을 그대로 Order하면 될 거 같다고 생각했다가 API를 보고 item 테이블로 구성되어 있는 것을 보고 다시 테이블을 설계하게 되었다.
      
- 실습 멘토님 Q&A
  - 실제 개발할 때 어떤 것을 보면서 테이블을 설계하는지 궁금합니다.
    - 기능 정의 → 테이블 설계 → API
    - 어떤 식으로 데이터를 주고 받기 위해서는 어떤 데이터를 가지고 있는지가 중요하기 때문에 보통 테이블 설계를 먼저 진행
  - 프론트엔드와도 테이블 설계 시 얘기를 하나요?
    - 보통 DBA나 팀내의 백엔드끼리만 얘기하고 프론트와는 잘 이야기하지 않는다
  - 신입 개발자가 SQL을 어느정도까지 알고 쿼리를 작성할 수 있어야 하나요?
    - 확실하게 이 정도다! 라고는 말할 수 없지만 현재 프로젝트에서 JPA를 사용하지만 안 사용하는 기업들도 매우 많기 때문에 우리 프로젝트 내에서 사용하는 쿼리문들은 다 알아야 함!
    - DBA는 대게 백엔드 개발자가 짠 쿼리를 검사하지 직접 짜는 것은 드물다.
</details>

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

## **과제 상세 : 수강생들이 과제를 진행할 때, 유념해야할 것**
아래 항목은 반드시 포함하여 과제 수행해주세요!
>- 전체 API 주소 설계가 RestAPI 맞게 설계되었는가? (예를 들어 배포된 서버는 POST와 GET으로만 구현되었는데, 학생들은 PUT과 DELETE도 배울 예정이라 이부분이 반영되었고, 주소가 RestAPI에 맞게 설계되었는지)
>- 가짜 데이터를 설계하여 Mock API를 잘 구현하였는가? (예를 들어 DB연결없이 컨트롤러만 만들어서 배포된 서버의 응답과 동일한 형태로 데이터가 응답되는지 여부)
</br>

## **코드리뷰 관련: PR시, 아래 내용을 포함하여 코멘트 남겨주세요.**
**1. PR 제목과 내용을 아래와 같이 작성 해주세요.**

>- PR 제목 : 부산대BE_라이언_2주차 과제

</br>

**2. PR 내용 :**

>- 코드 작성하면서 어려웠던 점
>- 코드 리뷰 시, 멘토님이 중점적으로 리뷰해줬으면 하는 부분

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
