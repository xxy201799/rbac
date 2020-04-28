package com.xxy.rbac_cloud_upms_biz.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xxy.rbac.admin.api.dto.DictDTO;
import com.xxy.rbac.admin.api.entity.SysDict;

import java.util.List;

public interface SysDictService {
    List<SysDict> getByType(LambdaQueryWrapper<SysDict> eq);

    SysDict getById(Integer id);

    Object deleteDictById(Integer id);

    Object updateDict(DictDTO dictDTO);

    IPage getUserWithRolePage(Page page, DictDTO dictDTO);

    Object saveDict(DictDTO dictDTO);
}
