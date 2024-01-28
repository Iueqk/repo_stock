package com.cqut.stock.service;

import com.cqut.stock.pojo.entity.SysUser;

import java.util.Map;


public interface UserService {
    SysUser selectUserByUsername(String username);

    Map getCaptchaCode();
}
