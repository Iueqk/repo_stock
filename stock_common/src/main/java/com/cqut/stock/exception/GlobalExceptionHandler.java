package com.cqut.stock.exception;

import com.cqut.stock.common.ApiRestResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
/**
 * 异常捕获 返回Json格式的ApiRestResponse对象
 */
@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class)
    @ResponseBody
    public Object handleException(Exception e) {
        log.error("Default Exception", e);
        return ApiRestResponse.error(StockExceptionEnum.SYSTEM_ERROR);
    }

    @ExceptionHandler(StockException.class)
    @ResponseBody
    public Object handleStockException(StockException e) {
        log.error("StockException: ", e);
        return ApiRestResponse.error(e.getCode(), e.getMessage());
    }
}
