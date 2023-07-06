# 2주차 과제
## API 주소 설계

### User
| API | URI | HTTP Method | 
| --- | --- | --- |
| 회원가입 | /auth/join | POST | 
| 로그인 | /auth/login | POST |

### Product
| API | URI | HTTP Method | 
| --- | --- | --- |
| 전체 상품 조회 | /products | GET | 
| 개별 상품 조회 | /products/{id} | GET |
<br>
(전체 상품 조회는 페이지를 Param으로 받아 매핑한다. page의 디폴트 값은 0이다.)

### Cart
| API | URI | HTTP Method | 
| --- | --- | --- |
| 장바구니 조회 | /carts | GET | 
| 장바구니 담기 | /carts/add | POST |
| 장바구니 수정 | /carts/update | POST |

### Order
| API | URI | HTTP Method | 
| --- | --- | --- |
| 결제하기 | /orders/save | POST |
| 주문 확인 | /orders/{id} | GET |

## Mock API Controller 구현

<br> 강사님이 제공해주신 베이스 소스가 있어, 제가 구현해야할 API는 4가지였습니다.
<br> RequestBody에 상관없이 Controller에서 더미 데이터를 작성하고 ResponseBody만 테스트하는 과제이기에 이에 중점을 뒀습니다.

### 장바구니 담기
클라이언트측에서 요청이 담긴 DTO를 작성하였습니다.
<br> 두개의 소스파일보다 하나의 소스파일에서 이너 클래스로 관리하는 것이 효율적이라 생각하여 이너 클래스로 작성하였습니다.
```java
import lombok.Getter;

public class CartRequest {
    @Getter
    public static class AddDTO {
        private int optionId;
        private int quantity;
    }

    @Getter
    public static class UpdateDTO {
        private int cartId;
        private int quantity;
    }
}
```
base URI를 기준으로 분기를 하기 위해 base URI를 기준으로 맵핑하였습니다.
```java
@RequestMapping(value = "/carts")
public class CartRestController {
...
  @PostMapping("/add")
  public ResponseEntity<?> cartAdd(@RequestBody List<CartRequest.AddDTO> addDTOs) {
      return ResponseEntity.ok(ApiUtils.success(null));
  }
...
```
RequestBody의 요청은 여러개의 상품 담기를 요청하기 때문에 List 자료형으로 받아옵니다.
```json
//과제에서 요구하는 JSON 응답
{
    "success": true,
    "response": null,
    "error": null
}
//요청을 제대로 전달받았는지 확인하는 JSON 응답
{
    "success": true,
    "response": [
        {
            "optionId": 1,
            "quantity": 5
        },
        {
            "optionId": 2,
            "quantity": 5
        }
    ],
    "error": null
}
```
