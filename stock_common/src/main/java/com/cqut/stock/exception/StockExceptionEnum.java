package com.cqut.stock.exception;

public enum StockExceptionEnum {
    /*用户模块*/
    NEED_USER_NAME(10001, "用户名不能为空"),
    NEED_PASSWORD(10002, "密码不能为空"),


    /*大盘板块*/
    NO_STOCK_BLOCK_RESPONSE_DATA(20001,"此刻没有大盘板块数据"),
    SYSTEM_ERROR(55555555,"系统异常");

    Integer code;
    String msg;

    StockExceptionEnum(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
