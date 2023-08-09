package com.example.kakao._core.utils;

import lombok.Data;
import lombok.experimental.Delegate;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

@Data
public class ValidList<E> implements List<E> {

    @Delegate
    @NotEmpty(message = "데이터가 비어있습니다.")
    @Valid
    private List<E> validList = new ArrayList<>();
}
