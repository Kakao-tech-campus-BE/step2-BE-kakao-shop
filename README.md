# Step-2.-Week-1
카카오 테크 캠퍼스 2단계 - BE - 1주차 클론 과제
</br>
</br>

## **1번**
```
첫번째, 판매자 계정을 통한 상품 등록 및 삭제 기능이 추가된다면 좋을 것 같다. 실제 카카오쇼핑에서 판매자가 직접 글으 올리는지, 카카오에서 상품에 대한 검수를 거친 후에 최고판매관리자가 판매상품을 등록하지는 잘 모르겠지만, 전자로 설계한다 햇을때  Seller 테이블을 추가하고 Product와Option테이블을 수정할 수 있는 api를 만들면 될것 같다.
 또한, Product 테이블에 seller_id 외래키를 추가함으로써 각각의 판매자가 본인의 product를 관리하면 될 듯하다.
두번째, 사용자 테이블에 컬럼으로 status를 추가하여, 휴먼상태 탈퇴상태 활성상태등을 구별하면 서비스가 오래되었을때, 서버관리에 좋을 것 같다.
```
## **2번**
```
(기능1) 회원 가입 : /join RequestBody로 이메일, 이름, 비밀번호, 비밀번호 확인 입력 후, 회원가입 버튼을 클릭시 해당 정보를 post방식으로 /join API 연결
(기능1-2)이메일 중복체크 : /check 화면설계에는 버튼이 따로있지는 않지만, 이메일 정보를 기존 User 테이블의 정보와 중복체크하는 역할을 한다. 중복체크 버튼을 따로 두던지, 아니면 회원가입 버튼을 클릭하면 첫번째로 중복체크를 먼저하는 식으로 진행하면 될것 같다.
(기능2) 로그인 : /login 입력된 이메일과 비밀번호를 통해 기존 User테이블의 이메일과 비밀번호를 비교하여 로그인을 진행한다.
(기능3) 로그아웃 : /logout 현재 로그인 JWT토큰을 삭제? 한다. jwt토큰에대해 잘 아는게 없어서 공부를 해야할것 같다.
(기능4) 전체 상품 목록 조회 : /products
(기능5) 개별 상품 상세 조회 : /products/{product_id}
(기능6) 상품 옵션 선택 : /products/{product_id}, 선택을 하는 기능은 프론트에서 체크하여 장바구니에 담을때 데이터를 넘겨받는것이 좋을것 같다.
(기능7) 옵션 확인 및 수량 결정 : (수량버튼 + 혹은 -를 누를때) /product/{product_id}/{option_id}/add 혹은 /products/{product_id}/{option_id}/remove 근데 이런식으로 한다면.. 그때마다 DB에 접근해야되서 사실 실용성이 없을 것 같다. 개수는 프론트에서 넘겨받는게 나을듯하다. 
(기능8) 장바구니 담기 : /carts/add 프론트에서 넘겨주는 데이터를 바탕으로 장바구니버튼을 클릭하면 cart 테이블에 데이터를 추가한다. cart 테이블의 user_id는 현재 토큰의 user_id를 담아야한다.
(기능9) 장바구니 보기 : /carts 현재 토근의 user_id를 가지는 cart들을 조회한다.
(기능10) 장바구니 상품 옵션 확인 및 수량 결정 :/cart , 기능7과 마찬가지로, 주문하기를 눌렀을때 수정된 최종데이터를 주문하기 버튼을 통해 넘겨주는것이 좋을 것 같다.
(기능11) 주문 : /carts/update , 수정된 최종 option_id와 option_count를 데이터로 받고 이를 통해 carts 데이터를 update 한다.
(기능12) 결제 : /order , 결제하기 버튼을 클릭하면, user_id를 외래키로 가지는 order 테이블에 데이터를 추가하여 주고, 해당 order_id를 외래키로 가지는 order_item 테이블에 데이터를 추가해준다. order_item 테이블은 order_id를 외래키로 가진다는 점 빼고는 모두 cart 테이블과 컬럼이 동일하다. 
```
##**3번**##
```
첫번째, 전체상품목록조회에 [무료배송]에 대한 데이터를 받지 않고 있다.
두번째, 개별상품상세조회에서 [배송비]에 대한 데이터를 받지 않고 있다.
세번째, 장바구니담기에서 [총 가격]에 대한 데이터를 받지 않고 프론트에서 계산하여 표시하고 있다.

```
추가로 과제에 명시되어 있지 않지만, jpa로 테이블을 작성할때 order나 option 을 테이블명으로 하면 오류가 발생하였다고 팀원분께서 말씀하셨다. 아마도 키워드로 지정되어있는것 같아 수정하는게 좋을것 같다.

</br>

##**4번**##
###domain 작성![kakaoShopping drawio (1)](https://github.com/yuseonkim/step2-BE-kakao-shop/assets/81663729/9835363c-d4ec-479d-88fb-ebf0e4d19b88)


###domain 작성

product 테이블

```
public class Product extends BaseEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "product_id", nullable = false)
    private Long id;

    @Setter
    @Column(name = "product_name", nullable = false)
    private String name;
    @Setter
    @Column(nullable = true)
    private String description;

    @Setter
    @Column(name = "product_price" ,nullable = false)
    private int price;

    @Setter
    @Column(nullable = true)
    private String image;
    
    @OneToMany(mappedBy = "product")
    @Setter
    @JoinColumn()
    private List<Options> options = new ArrayList<>();
}
```
User 테이블

```
@Getter
@Entity
public class User extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id", nullable = false)
    private Long id;

    @Setter
    @Column(nullable = false)
    private String email;

    @Setter
    @Column(nullable = false)
    private String name;

    @Setter
    @Column(nullable = false, length = 20)
    private String passwd;

    @Setter
    @Column(nullable = false)
    private String status;

    @OneToMany(mappedBy = "user")
    @Setter
    private List<Cart> cart_list = new ArrayList<>();

    @OneToMany(mappedBy = "user")
    @Setter
    private List<OrderItem> item_list = new ArrayList<>();


}
```
Option 테이블

```
@Entity
@Getter
public class Options {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "option_id", nullable = false)
    private Long id;

    @Setter
    @Column(name = "option_name" , nullable = false)
    private String name;

    @Setter
    @Column(name = "option_price" , nullable = false)
    private int price;

    @Setter
    @Column(name = "option_count", nullable = false)
    private int count;

    @Setter
    @ManyToOne
    @JoinColumn(name="product_id")
    private Product product;

    @Setter
    @OneToOne(mappedBy ="option")
    private Cart cart;

    @Setter
    @OneToOne(mappedBy= "option")
    private OrderItem orderItem;
}
```

Cart 테이블

```
@Getter
@Entity
public class Cart extends BaseEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "cart_id", nullable = false)
    private Long id;

    @OneToOne
    @Setter
    @JoinColumn(name = "option_id")
    private Options option;

    @ManyToOne
    @Setter
    @JoinColumn(name = "user_id")
    private User user;

    @Column(nullable = false)
    @Setter
    private int count;


}
```
Order_Item 테이블

```
@Getter
@Entity
public class OrderItem{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "item_id", nullable = false)
    private Long id;

    @OneToOne
    @Setter
    @JoinColumn(name = "option_id")
    private Options option;

    @ManyToOne
    @Setter
    @JoinColumn(name="user_id")
    private User user;

    @ManyToOne
    @Setter
    @JoinColumn(name="order_id")
    private Order order;

}

```

Order 테이블

```
@Entity
@Getter
public class Order extends BaseEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private Long id;

    @OneToOne
    @Setter
    @JoinColumn(name="user_id")
    private User user;

    @OneToMany(mappedBy = "order")
    @Setter
    private List<OrderItem> item_list = new ArrayList<>();

}
```
