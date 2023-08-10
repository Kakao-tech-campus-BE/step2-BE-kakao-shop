# week6

### 1. 통합테스트 구현

UserRestControllerTest

- join_test
    
    회원가입이 잘 되는지 테스트한다.
    

![Untitled](week6%20b1121c4e33344d718974801027812226/Untitled.png)

- login_test
    
    로그인이 제대로 적용되는지 테스트한다.
    

![Untitled](week6%20b1121c4e33344d718974801027812226/Untitled%201.png)

OrderRestControllerTest

- save_test
    
    주문을 제대로 저장할 수 있는지를 테스트한다.
    

![Untitled](week6%20b1121c4e33344d718974801027812226/Untitled%202.png)

- findById_test
    
    주문의 내용을 확인할 수 있는지를 테스트한다.
    

![Untitled](week6%20b1121c4e33344d718974801027812226/Untitled%203.png)

### 2. 실패상황 테스트 추가

- join_fail_test
1. 이미 존재하는 이메일로 회원가입을 하려는 경우 테스트
2. 유효성 검사 테스트

![Untitled](week6%20b1121c4e33344d718974801027812226/Untitled%204.png)

![Untitled](week6%20b1121c4e33344d718974801027812226/Untitled%205.png)

- login_fail_test
1. 존재하지 않는 유저로 로그인 시도하는 경우

![Untitled](week6%20b1121c4e33344d718974801027812226/Untitled%206.png)

- addCartList_fail_test
1. 중복된 옵션id가 들어오는 경우
2. 존재하지않는 옵션id 추가를 요청하는 경우

![Untitled](week6%20b1121c4e33344d718974801027812226/Untitled%207.png)

![Untitled](week6%20b1121c4e33344d718974801027812226/Untitled%208.png)

- update_fail_test
1. 동일한 장바구니 id를 여러번 요청하는 경우
2. 본인에게 없는 장바구니 id를 요청하는 경우
3. 장바구니에 카트가 존재하지 않는 경우

![Untitled](week6%20b1121c4e33344d718974801027812226/Untitled%209.png)

![Untitled](week6%20b1121c4e33344d718974801027812226/Untitled%2010.png)

![Untitled](week6%20b1121c4e33344d718974801027812226/Untitled%2011.png)

- order_fave_fail_test
1. 장바구니가 비어있을 때 쓸모없는 주문을 방지

![Untitled](week6%20b1121c4e33344d718974801027812226/Untitled%2012.png)

- order_findById_fail_test
1. 존재하지 않는 주문내역을 요청하는 경우

![Untitled](week6%20b1121c4e33344d718974801027812226/Untitled%2013.png)

### 3. 생성된 api문서

![Untitled](week6%20b1121c4e33344d718974801027812226/Untitled%2014.png)

![Untitled](week6%20b1121c4e33344d718974801027812226/Untitled%2015.png)

![Untitled](week6%20b1121c4e33344d718974801027812226/Untitled%2016.png)

![Untitled](week6%20b1121c4e33344d718974801027812226/Untitled%2017.png)

![Untitled](week6%20b1121c4e33344d718974801027812226/Untitled%2018.png)

![Untitled](week6%20b1121c4e33344d718974801027812226/Untitled%2019.png)

![Untitled](week6%20b1121c4e33344d718974801027812226/Untitled%2020.png)

![Untitled](week6%20b1121c4e33344d718974801027812226/Untitled%2021.png)

![Untitled](week6%20b1121c4e33344d718974801027812226/Untitled%2022.png)

![Untitled](week6%20b1121c4e33344d718974801027812226/Untitled%2023.png)