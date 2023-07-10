package com.example.kakaoshop.order;

import com.example.kakaoshop._core.utils.ApiUtils;
import com.example.kakaoshop._core.utils.ApiUtils.ApiResult;
import com.example.kakaoshop.order.response.OrderNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@ResponseBody
public class OrderNotFoundExceptionAdvice {
  @ExceptionHandler(OrderNotFoundException.class)
  @ResponseStatus(HttpStatus.NOT_FOUND)
  public ApiResult<?> orderNotFoundHandler(Exception e) {
    return ApiUtils.error(HttpStatus.NOT_FOUND.getReasonPhrase(), HttpStatus.NOT_FOUND);
  }
}
