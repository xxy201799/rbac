package com.xxy.rbac_cloud_upms_biz.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xxy.common.core.constants.CacheConstants;
import com.xxy.common.core.util.R;
import com.xxy.rbac.admin.api.entity.SysMenu;
import com.xxy.rbac.admin.api.vo.MenuVO;
import com.xxy.rbac_cloud_upms_biz.mapper.SysMenuMapper;
import com.xxy.rbac_cloud_upms_biz.service.SysMenuService;
import lombok.AllArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 菜单权限表 服务实现类
 * </p>
 *
 */
@Service
@AllArgsConstructor
public class SysMenuServiceImpl extends ServiceImpl<SysMenuMapper, SysMenu> implements SysMenuService {
    @Override
    @Cacheable(value = CacheConstants.MENU_DETAILS, key = "#roleId + '_menu'")
    public List<MenuVO> getMenuByRoleId(Integer roleId) {
        return baseMapper.listMenusByRoleId(roleId);
    }

    @Override
    public R removeMenuById(Integer id) {
        return null;
    }

    @Override
    public Boolean updateMenuById(SysMenu sysMenu) {
        return null;
    }
}
