package com.example.programming.dmaker.exception;

import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import static com.example.programming.dmaker.exception.DMakerErrorCode.*;
import com.example.programming.dmaker.dto.DMakerErrorResponse;

import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;

/*
 * 나 같이 머리 안좋은 사람들은 영어도 해석못하고 지난 프로젝트는 잊어버리기에 이렇게 예외 처리를 해놓으면 편할 것 같다. 컨트롤러가 많아지면 ExceptionHandler를
 * 다 넣는게 힘들다. 그래서 요즘은 Exception을 각 컨트롤러에서 처리하는게 아니라 글로벌Exception을 따로 만들어서 거기에서 처리하게 한다. 각 컨트롤러에다가
 * 조언을 해주는 Controller에 Advice 역할을 한다. Bean으로 등록을 하기 위한 어노테이션 (@Controller에 @ResponseBody추가) 중복되는
 * Exception들을 하나의 클래스에 모아서 처리할 수 있다.
 */

@Slf4j
@RestControllerAdvice
public class DMakerExceptionHandler {
  @ExceptionHandler(DMakerException.class)
  public DMakerErrorResponse handleException(DMakerException e, HttpServletRequest request) {
    log.error("ErrorCode: {}, url: {}, message: {}", e.getDMakerErrorCode(),
        request.getRequestURI(), e.getDetailMessage());
    return DMakerErrorResponse.builder().errorCode(e.getDMakerErrorCode())
        .errorMessage(e.getMessage()).build();

  }

  /*
   * 구체적인( Controller에서 Method 매핑을 했는데 다른 메소드로 매핑하였을 때, 자바 빈 벨리데이션을 사용하고 있는데 이러한 벨리데이션에서 문제가 발생하였을 때
   * ) Exception 예외 처리
   */
  @ExceptionHandler(
      value = {HttpRequestMethodNotSupportedException.class, MethodArgumentNotValidException.class})
  public DMakerErrorResponse handleBadRequest(Exception e, HttpServletRequest request) {
    log.error("url: {}, message: {}", request.getRequestURI(), e.getMessage());
    return DMakerErrorResponse.builder().errorCode(INVALID_REQUEST)
        .errorMessage(INVALID_REQUEST.getMessage()).build();

  }

  // 어떤 Exception을 일으킬지 모를 때도 있는데 최후의 보류로 Exception.class 를 넣어서
  // Internal Server Error 를 띄운다.
  @ExceptionHandler(value = Exception.class)
  public DMakerErrorResponse handleException(Exception e, HttpServletRequest request) {
    log.error("url: {}, message: {}", request.getRequestURI(), e.getMessage());
    return DMakerErrorResponse.builder().errorCode(INTERNAL_SERVER_ERROR)
        .errorMessage(INTERNAL_SERVER_ERROR.getMessage()).build();
  }
}
