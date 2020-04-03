package com.xxy.rbac_cloud_upms_biz.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.xxy.rbac.admin.api.entity.SysRole;

import java.util.List;

public interface SysRoleMapper extends BaseMapper<SysRole> {
    /**
     * 通过用户ID，查询角色信息
     *
     * @param userId
     * @return
     */
    List<SysRole> listRolesByUserId(Integer userId);
}
