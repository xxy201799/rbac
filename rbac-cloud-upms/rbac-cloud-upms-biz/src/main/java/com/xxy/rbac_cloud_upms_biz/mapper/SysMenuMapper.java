package com.xxy.rbac_cloud_upms_biz.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.xxy.rbac.admin.api.entity.SysMenu;
import com.xxy.rbac.admin.api.vo.MenuVO;

import java.util.List;

/**
 * <p>
 * 菜单权限表 Mapper 接口
 * </p>
 *
 */
public interface SysMenuMapper extends BaseMapper<SysMenu> {
    /**
     * 通过角色编号查询菜单
     *
     * @param roleId 角色ID
     * @return
     */
    List<MenuVO> listMenusByRoleId(Integer roleId);

    /**
     * 通过角色ID查询权限
     *
     * @param roleIds Ids
     * @return
     */
    List<String> listPermissionsByRoleIds(String roleIds);

}
