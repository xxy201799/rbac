package com.xxy.rbac_cloud_upms_biz.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xxy.rbac.admin.api.dto.DictDTO;
import com.xxy.rbac.admin.api.dto.UserDTO;
import com.xxy.rbac.admin.api.entity.SysDict;
import com.xxy.rbac.admin.api.entity.SysMenu;
import com.xxy.rbac.admin.api.vo.DictVO;
import com.xxy.rbac.admin.api.vo.UserVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface SysDictMapper extends BaseMapper<SysDict> {
    IPage<List<DictVO>> getDictVosPage(Page page, @Param("query")DictDTO dictDTO);
}
