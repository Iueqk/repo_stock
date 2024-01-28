package com.cqut.stock.mapper;

import com.cqut.stock.pojo.entity.SysUserRole;

/**
* @author WSH4950
* @description 针对表【sys_user_role(用户角色表)】的数据库操作Mapper
* @createDate 2024-01-25 16:01:34
* @Entity com.cqut.stock.pojo.entity.SysUserRole
*/
public interface SysUserRoleMapper {

    int deleteByPrimaryKey(Long id);

    int insert(SysUserRole record);

    int insertSelective(SysUserRole record);

    SysUserRole selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(SysUserRole record);

    int updateByPrimaryKey(SysUserRole record);

}
