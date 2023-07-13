## 과제 수행 - API 설계

**장바구니 담기**

- Method : Post
- Local URL : http://localhost:8080/carts

**장바구니 조회**

- Method : Get
- Local URL : http://localhost:8080/carts

**장바구니 수정**

- Method : Put
- Local URL : http://localhost:8080/carts

**결제하기**

- Method : Post
- Local URL : http://localhost:8080/orders

**주문 결과 확인**

- Method : Get
- Local URL : http://localhost:8080/orders/{id}


위의 방식은 REST API이고, 실제 구현은 API 문서에 맞게 진행하겠습니다.

평소 DTO를 사용할 때는 DTO에 담을 정보들을 대부분 아래와 같이 정보를 필드들을 나열해서 응답하였습니다.
이번 과제를 통해서, 관련성 있는 정보들은 프론트와 협의 후 객체로 만들어서 보내줘도 괜찮겠다 라는 생각이 들었습니다.

```java
    @Getter @Setter
    public class ProductDTO {
    
        private int id;
        private String productName;
    }
```


그리고, 아래와 같은 고민을 해보았습니다.


### DTO의 field에 final을 붙여야 하는 가??

final 키워드를 field에 사용하면, 해당 객체가 생성된 이후부터 그 필드는 재할당이 불가능해집니다.
그렇다면, DTO를 생성한 다음에 재할당을 해야할 일이 있을까요??

일반적으로, DTO를 생성한 다음에 협업하는 다른 개발자가 실수로 DTO를 수정하지 않는 이상 다시 재할당을 하는 일은 없을 것 같네요

그래서, 제가 내린 결론은 굳이 붙일 필요가 없다입니다!


### DTO의 제공 단위에 대한 고민

1. 모든 응답 케이스에 대응하게끔 DTO를 만든다.
2. 비슷한 응답 케이스들은 공통 응답 DTO를 만들어 사용한다.

1번의 경우에는 코드가 중복되는 것처럼 보이더라도, 명확하게 설계할 수 있다.
또한, 서비스가 확장되고 요구사항이 변경되더라도 쉽게 대응할 수 있다.
하지만, DTO가 너무 많을 경우에 유지보수가 어렵고, 네이밍 하기도 난해합니다.

2번의 경우에는 아래와 같은 의문이 생길 것입니다.
공통 응답 DTO는 어떤 기준으로 만들 것인가

공통 응답 DTO를 만드는 데 저만의 기준은 아래와 같습니다.

- 추후 요구사항이 변경될 여지가 있는 가?


이번 과제를 수행하며, DTO의 변수명을 짓는 제 나름의 결론을 내려봤습니다!

### DTO 변수 명을 짓는 방법

1. 요청은 ~Request ~ DTO, 응답은 ~Response ~ DTO라고 팀끼리 미리 협의를 해두고, 요청과 응답이 아닌 DTO는 ~DTO라고 끝나도록 이름을 짓는다.
    1. 첫 번째 ~는 상위 디렉토리의 이름을 사용하고, 두번째 ~ 는 메서드 명을 활용한다.
    2. 두번째 ~ 는 구별이 필요할 때만 사용한다.
2. DTO의 내용이 어떤 테이블과 관련이 있는 지를 파악하라