package com.xxy.rbac_cloud_upms_biz.service.impl;

import cn.hutool.core.util.ArrayUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xxy.common.core.util.R;
import com.xxy.rbac.admin.api.dto.UserDTO;
import com.xxy.rbac.admin.api.dto.UserInfo;
import com.xxy.rbac.admin.api.entity.SysRole;
import com.xxy.rbac.admin.api.entity.SysUser;
import com.xxy.rbac.admin.api.vo.MenuVO;
import com.xxy.rbac.admin.api.vo.UserVO;
import com.xxy.rbac_cloud_upms_biz.mapper.SysUserMapper;
import com.xxy.rbac_cloud_upms_biz.service.SysMenuService;
import com.xxy.rbac_cloud_upms_biz.service.SysRoleService;
import com.xxy.rbac_cloud_upms_biz.service.SysUserService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Slf4j
@Service
@AllArgsConstructor
public class SysUserServiceImpl extends ServiceImpl<SysUserMapper, SysUser> implements SysUserService {
    private static final PasswordEncoder ENCODER = new BCryptPasswordEncoder();

    private final SysRoleService sysRoleService;
    private final SysMenuService sysMenuService;
    /**
     * 查指定用户的全部信息
     * @param sysUser
     * @return
     */
    @Override
    public UserInfo getUserInfo(SysUser sysUser) {
        UserInfo userInfo = new UserInfo();
        userInfo.setSysUser(sysUser);
        //设置角色列表（ID）
        List<Integer> roleIds = sysRoleService.listRolesByUserId(sysUser.getUserId())
                .stream()
                .map(SysRole::getRoleId)
                .collect(Collectors.toList());
        userInfo.setRoles(ArrayUtil.toArray(roleIds, Integer.class));

        //设置权限列表
        Set<String> permissions = new HashSet<>();
        roleIds.forEach(roleId -> {
            List<String> permissionList = sysMenuService.getMenuByRoleId(roleId)
                    .stream()
                    .filter(menuVO -> StringUtils.isNotEmpty(menuVO.getPermission()))
                    .map(MenuVO::getPermission)
                    .collect(Collectors.toList());
            permissions.addAll(permissionList);
        });
        userInfo.setPermissions(ArrayUtil.toArray(permissions, String.class));
        return userInfo;
    }

    @Override
    public IPage getUserWithRolePage(Page page, UserDTO userDTO) {
        return null;
    }

    @Override
    public Boolean removeUserById(SysUser sysUser) {
        return null;
    }

    @Override
    public R<Boolean> updateUserInfo(UserDTO userDTO) {
        return null;
    }

    @Override
    public R<Boolean> updateUser(UserDTO userDTO) {
        return null;
    }

    @Override
    public UserVO getUserVoById(Integer id) {
        return null;
    }

    @Override
    public List<SysUser> listAncestorUsersByUserName(String username) {
        return null;
    }

    @Override
    public Boolean saveUser(UserDTO userDTO) {
        return null;
    }
}
