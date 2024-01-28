package com.cqut.stock.exception;

public class StockException extends RuntimeException{
    private final Integer code;
    private final String message;

    public StockException(Integer code,String message){
        this.code = code;
        this.message = message;
    }

    public StockException(StockExceptionEnum exceptionEnum){
        this(exceptionEnum.getCode(),exceptionEnum.getMsg());
    }

    public Integer getCode() {
        return code;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
