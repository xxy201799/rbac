package com.xxy.rbac_cloud_upms_biz.service.impl;

import cn.hutool.core.util.ArrayUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xxy.common.core.constants.CacheConstants;
import com.xxy.common.core.constants.CommonConstants;
import com.xxy.common.core.util.R;
import com.xxy.rbac.admin.api.dto.UserDTO;
import com.xxy.rbac.admin.api.dto.UserInfo;
import com.xxy.rbac.admin.api.entity.SysDept;
import com.xxy.rbac.admin.api.entity.SysRole;
import com.xxy.rbac.admin.api.entity.SysUser;
import com.xxy.rbac.admin.api.entity.SysUserRole;
import com.xxy.rbac.admin.api.vo.MenuVO;
import com.xxy.rbac.admin.api.vo.UserVO;
import com.xxy.rbac_cloud_upms_biz.mapper.SysUserMapper;
import com.xxy.rbac_cloud_upms_biz.service.*;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Slf4j
@Service
@AllArgsConstructor
public class SysUserServiceImpl extends ServiceImpl<SysUserMapper, SysUser> implements SysUserService {
    private static final PasswordEncoder ENCODER = new BCryptPasswordEncoder();
    private final SysMenuService sysMenuService;
    private final SysRoleService sysRoleService;
    private final SysDeptService sysDeptService;
    private final SysUserRoleService sysUserRoleService;
    private final SysDeptRelationService sysDeptRelationService;
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
        return baseMapper.getUserVosPage(page, userDTO);
    }

    /**
     * 删除用户
     *
     * @param sysUser 用户
     * @return Boolean
     */
    @Override
    @CacheEvict(value = CacheConstants.USER_DETAILS, key = "#sysUser.username")
    public Boolean removeUserById(SysUser sysUser) {
        sysUserRoleService.removeRoleByUserId(sysUser.getUserId());
        this.removeById(sysUser.getUserId());
        return Boolean.TRUE;
    }

    /**
     * 修改用户信息，有密码则修改密码
     * @param userDto
     * @return
     */
    @Override
    @CacheEvict(value =CacheConstants.USER_DETAILS, key = "#userDto.username")
    public R<Boolean> updateUserInfo(UserDTO userDto) {
        UserVO userVO = baseMapper.getUserVoByUsername(userDto.getUsername());
        SysUser sysUser = new SysUser();
        if (StrUtil.isNotBlank(userDto.getPassword())
                && StrUtil.isNotBlank(userDto.getNewpassword1())) {
            if (ENCODER.matches(userDto.getPassword(), userVO.getPassword())) {
                sysUser.setPassword(ENCODER.encode(userDto.getNewpassword1()));
            } else {
                log.warn("原密码错误，修改密码失败:{}", userDto.getUsername());
                return R.failed("原密码错误，修改失败");
            }
        }
        sysUser.setPhone(userDto.getPhone());
        sysUser.setUserId(userVO.getUserId());
        sysUser.setAvatar(userDto.getAvatar());
        return R.ok(this.updateById(sysUser));
    }


    @Override
    @CacheEvict(value = CacheConstants.USER_DETAILS, key = "#userDto.username")
    public Boolean updateUser(UserDTO userDto) {
        SysUser sysUser = new SysUser();
        BeanUtils.copyProperties(userDto, sysUser);
        sysUser.setUpdateTime(LocalDateTime.now());

        if (StrUtil.isNotBlank(userDto.getPassword())) {
            sysUser.setPassword(ENCODER.encode(userDto.getPassword()));
        }
        this.updateById(sysUser);

        sysUserRoleService.remove(Wrappers.<SysUserRole>update().lambda()
                .eq(SysUserRole::getUserId, userDto.getUserId()));
        userDto.getRole().forEach(roleId -> {
            SysUserRole userRole = new SysUserRole();
            userRole.setUserId(sysUser.getUserId());
            userRole.setRoleId(roleId);
            userRole.insert();
        });
        return Boolean.TRUE;
    }


    @Override
    public UserVO getUserVoById(Integer id) {
        return baseMapper.getUserVoById(id);
    }



    /**
     * 查询上级部门的用户信息
     *
     * @param username 用户名
     * @return R
     */
    @Override
    public List<SysUser> listAncestorUsersByUsername(String username) {
        SysUser sysUser = this.getOne(Wrappers.<SysUser>query().lambda()
                .eq(SysUser::getUsername, username));

        SysDept sysDept = sysDeptService.getById(sysUser.getDeptId());
        if (sysDept == null) {
            return null;
        }

        Integer parentId = sysDept.getParentId();
        return this.list(Wrappers.<SysUser>query().lambda()
                .eq(SysUser::getDeptId, parentId));
    }

    /**
     * 保存用户信息
     *
     * @param userDto DTO 对象
     * @return success/fail
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean saveUser(UserDTO userDto) {
        SysUser sysUser = new SysUser();
        BeanUtils.copyProperties(userDto, sysUser);
        sysUser.setDelFlag(CommonConstants.STATUS_NORMAL);
        sysUser.setPassword(ENCODER.encode(userDto.getPassword()));
        baseMapper.insert(sysUser);
        List<SysUserRole> userRoleList = userDto.getRole()
                .stream().map(roleId -> {
                    SysUserRole userRole = new SysUserRole();
                    userRole.setUserId(sysUser.getUserId());
                    userRole.setRoleId(roleId);
                    return userRole;
                }).collect(Collectors.toList());
        return sysUserRoleService.saveBatch(userRoleList);
    }

}
