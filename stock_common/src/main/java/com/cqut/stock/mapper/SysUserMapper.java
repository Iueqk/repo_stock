package com.cqut.stock.mapper;

import com.cqut.stock.pojo.entity.SysUser;
import org.apache.ibatis.annotations.Param;

/**
* @author WSH4950
* @description 针对表【sys_user(用户表)】的数据库操作Mapper
* @createDate 2024-01-25 16:01:34
* @Entity com.cqut.stock.pojo.entity.SysUser
*/
public interface SysUserMapper {

    int deleteByPrimaryKey(Long id);

    int insert(SysUser record);

    int insertSelective(SysUser record);

    SysUser selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(SysUser record);

    int updateByPrimaryKey(SysUser record);

    SysUser selectByUsernameSysUser(@Param("username") String username);

}
