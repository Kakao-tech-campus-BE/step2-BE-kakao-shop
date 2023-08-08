package com.example.kakao._core.errors;

import lombok.Data;
import lombok.experimental.Delegate;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;


//https://stackoverflow.com/questions/28150405/validation-of-a-list-of-objects-in-spring
// 강사님이 제공한 코드 중 aop로 커스텀 ValidationHandler를 이용하기에는 가장 적절한 방법이었다.
// 일반적인 상황에서는 @validated를 이용할것 같다.
@Data
public class ValidList<E> implements List<E> {
    @Valid
    @Delegate
    private List<E> list = new ArrayList<>();
}