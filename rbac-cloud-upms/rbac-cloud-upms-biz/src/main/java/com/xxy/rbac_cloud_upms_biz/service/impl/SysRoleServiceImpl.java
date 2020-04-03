package com.xxy.rbac_cloud_upms_biz.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xxy.rbac.admin.api.entity.SysRole;
import com.xxy.rbac_cloud_upms_biz.mapper.SysRoleMapper;
import com.xxy.rbac_cloud_upms_biz.service.SysMenuService;
import com.xxy.rbac_cloud_upms_biz.service.SysRoleService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 角色服务实现类
 * </p>
 *
 */
@Service
@AllArgsConstructor
public class SysRoleServiceImpl extends ServiceImpl<SysRoleMapper, SysRole> implements SysRoleService {

    /**
     * 通过用户ID，查询角色信息
     *
     * @param userId
     * @return
     */
    @Override
    public List listRolesByUserId(Integer userId) {
        return baseMapper.listRolesByUserId(userId);
    }

    @Override
    public Boolean removeRoleById(Integer id) {
        return null;
    }
}
