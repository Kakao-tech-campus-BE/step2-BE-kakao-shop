# step2-BE-kakao-shop

카카오 테크 캠퍼스 2단계 카카오 쇼핑하기 백엔드 클론 프로젝트 레포지토리입니다.

# 1주차

카카오 테크 캠퍼스 2단계 - BE - 2주차 클론 과제
</br>
</br>

## **과제명**

```
1. API주소를 설계하여 README에 내용을 작성
2. 가짜 데이터를 설계하여 응답하는 스프링부트 컨트롤러를 작성하고 소스코드를 업로드
```

## API 주소 설계

1. cart/response 내부의 ProductOptionDTO와 product/response 내부의 ProductOptionDTO가 겹친다고 생각하여 cart쪽의 DTO 를 CartProductOptionDTO로 수정하였습니다.

2. 구현되지 않은 "/orders/{id}"와 내부 DTO들을 구현하였습니다.

### 아이디어 방향성은 이렇습니다.

cart에 넣어 주문을 하면 어떤 유저가 어떠한 상품을 저장했는지에 대한 정보가 저장됩니다. user_id를 이용하여 cart를 조회할수 있다는 뜻이죠. 따라서, Orderitem에서는 Order_id에 대한 User_id에 해당하는 카트들을 조회해서 불러오는 Controller를 작성하려 하였습니다.  
하지만 여기서 문제가 조금 생겼습니다. 어떻게 해야 Order_id에 대한 User_id를 불러오는지 알기 어려웠습니다. 그리고 현재 존재하는 CartRestController 에서도 user와 연결되어 있지는 않은것 같아서 mockData에 카트를 그대로 넣고 요청에 따라 반환하는 방식으로 진행하였습니다.

## 프로젝트 중 발생한 궁금한 사항

1. 아이디어를 구현하지 못한 이유 중 하나입니다. /carts 를 호출하면 cart안에 담긴 물건들이 나옵니다.
   이를 orderController에서 호출하여 cart를 반환하면 orderitem을 불러올 수는 없나요? 또는 그렇게 하지 않는 이유는 무엇일까요?  
   그래서 카트에담겨있는 내용물이 그대로 order형태가 될 예정이기 때문에 다음과 위와같이 구현하였습니다.
   문제점이 될만한 부분이 있을까요?

2. DTO와 DTO가 아닌것의 차이가 무엇인가요? cart는 왜 DTO만 있고 cart가 없는지? productOption, productOptionDTO는 왜있는지? SQL에서 연결되는것과 프로그램 내부에서 이동하는것의 차이일까요?

3. 테이블설계 week1 정답 pdf중 ordertable 에서 user_id키는 외래키 제약이 걸리지 않은이유가 무엇일까요?

![ordertable](https://lh3.googleusercontent.com/fife/AKsag4NxAU2vqWUeyFeMkQXYPkctQg1OgDrYUOGi4MSvf-Sz9af8lErWz2rTlwLvXlo7IpjGBaMNLgcVo3QNiA_QpdG0tqfZsxh9RPF6SfWS6ytW7UkJcj43XTNsS59IkrJ29zRyIYyYyaGzZB7QyW5Bx2VKhA3aRa47_mUSyVHdTkxCj8YAen93FsP0G0fzCPasAnVY7KctlZKGgdxRnbKlmrgiv-6xxn8yzhHF3MKIWpIOrkfAhPn4cZHFBkszWmJeaUz6sFnf78kf6vCByIAJa7Dq4uwXKBRp2UWOyvolgMjhBXIpSxgyLTGWA4Htl-NLTQ3CL8T_vNHT7WTUeu9oKGnj4Mfds1te9dp2uz3dtT4vCDc4dft3XxulmCmr9NSP3doFWjPnaCSxpV5CaQEHn3kMnrKG4VjLLApfDHUADXVP9UggZiUzfBsMOWFXyzTTe8lu3_ysogg874RFDEapkbWxzUJ9tEJUTcrUPKg--Kbawtetj3nt9eAPQlCdHjbllBo1FP10Ot19Tl85mFxo5-m4LC6v44OURNegKqlfsR8Hl8x1F-ZJHhpI2gKHNBcj1Wf8EvZiH60FgBpDN42Eqi9GFkrEiKbHZ0HP6QocDQGLRvafP4WRLG3PggZ2rgLF18EXCSASDFLMicOyECFsmfRXAxfS-Wnyyg2YGZUR7KYcFTeZiYjOYEWJXbiTiFXZ0fZjVoZaj0VnaSU0cBd9ZVAbrxM0gmWUDk_GgcB_Zj87lwC9HKeylZBL-MouUUUs68v_3sCFFLqJ2k6qiBBx3n_W2gIQKwfzxk69aZE_LYEbhTeGf_U07nn16Rnsd15ZrYwgnqnoKxDhx5wp7dBVDC2mXad7iiyNZKOO9etKZxaj5szy8ZBZqp7S8RYz_AZpqKtKKFLnL9dQDaNk6QWOrVOLnxeg5EWrrcuN2GAZt8HKGOq3v0rCzjNUJiqjw3CfGbSrzFaTqPMyJYCn48SmaqzxS6iFvTd6CHlpd-akxMZHEBlt1-lI9soEXInmvmzyJtwGptTJ5mBwZPHZ0em8xJQX-d9_ghBswV9_5e10pKM70xVGvWZ_V33Kq648Kdy54-fgvYk-83fwgy4eCFlkNrFn95LbARA6PtYATQ8P6dcmxFO9zzOryzY55avnhiX06D58KH89DQxIdSf01Bmfnqm-icYEiK3MlvCwGSzc65LGWopDmlkt5jLdf_Sx0dMHcPtHjqkU1FXMYsaQ3UlYC2t6FMlTy9J0dWNvG2xk1kBFpTewMuS1BxWUGbOHapaU34hRcD0UkuwTkGKaGa9MxNjEU6VixTmzGv0Kj1EwHHWnDOQ2HfWzjBZbdTN5ZlWsINJbR1Gf_5qSpf-EjqIIxzGaCHI8gpa3qvXKLuReVyP1mWsJw_Fd2g43w9dumQJQvSKOsF5H-4dQt6mW3WIz6htLfXMfmIetFmpllFqxpzedNd0W5zbNa8r1aXw2QgDNZI7oaWpWZ80pqEMcnpVjU3jtTQ3K9-SaFTOB_JbwFARLtoCCwaCbnoKx9ZishnEpRHKaNZ0P=w1920-h937)

## 어려웠던점

```
cartItemDTO1.setOption(CartProductOptionDTO.builder()
                                .id(1)
                                .optionName("01. 슬라이딩 지퍼백 크리스마스에디션 4종")
                                .price(10000)
                                .build());
        cartItemDTOList.add(cartItemDTO1);
```

1. 위 코드는 원래 주어진 CartRestController 내부 코드인데 여기서 setOption 함수가 어디서 왔는지 찾아보는데 시간이 조금 걸렸습니다. 아마 @Setter 어노테이션에서 오는것 같다고 추측합니다. 이러한 것들처럼 구현하는데 편리하지만 모르기 떄문에 사용할 수 없는 함수들이 꽤나 있을것 같습니다.

2. 이렇게 하는게 맞나 싶은 부분이 많습니다. 데이터에 맞게 생각하여 잘 구현해 낸것같지만 실제로 데이터가 들어오면 잘 처리가 될까 하는 부분들이 있습니다. 이번에 구현한 내용만 봐도 그렇습니다. 이는 경험을 쌓으면서 해결할 수 있을 것이라고 생각합니다.
