package com.xxy.rbac_cloud_upms_biz.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.xxy.common.core.util.R;
import com.xxy.rbac.admin.api.dto.UserDTO;
import com.xxy.rbac.admin.api.dto.UserInfo;
import com.xxy.rbac.admin.api.entity.SysUser;
import com.xxy.rbac.admin.api.vo.UserVO;

import java.util.List;

public interface SysUserService extends IService<SysUser> {

    /**
     * 查询用户信息
     * @param sysUser
     * @return
     */
    UserInfo getUserInfo(SysUser sysUser);

    /**
     * 分页查询用户信息
     *
     * @param page 分页对象
     * @param userDTO 参数列表
     * @return
     */
    IPage getUserWithRolePage(Page page, UserDTO userDTO);

    /**
     * 删除用户
     * @param sysUser 用户
     * @return
     */
    Boolean removeUserById(SysUser sysUser);

    /**
     * 更新当前用户信息
     * @param userDTO 用户信息
     * @return
     */
    R<Boolean> updateUserInfo(UserDTO userDTO);

    /**
     * 更新指定用户信息
     * @param userDTO 用户信息
     * @return
     */
    R<Boolean> updateUser(UserDTO userDTO);

    /**
     * 通过ID查询用户信息
     * @param id 用户ID
     * @return 用户信息
     */
    UserVO getUserVoById(Integer id);

    /**
     * 查询上级部门的用户信息
     * @param username 用户名
     * @return R
     */
    List<SysUser> listAncestorUsersByUserName(String username);

    /**
     * 保存用户信息
     *
     * @param userDTO
     * @return
     */
    Boolean saveUser(UserDTO userDTO);

}
