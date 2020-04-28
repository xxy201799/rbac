package com.xxy.rbac_cloud_upms_biz.service.impl;

import cn.hutool.core.lang.Dict;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xxy.rbac.admin.api.dto.DictDTO;
import com.xxy.rbac.admin.api.entity.SysDict;
import com.xxy.rbac.admin.api.entity.SysRole;
import com.xxy.rbac_cloud_upms_biz.mapper.SysDictMapper;
import com.xxy.rbac_cloud_upms_biz.service.SysDictService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class SysMenuServiceImply extends ServiceImpl<SysDictMapper, SysDict> implements SysDictService {
@Autowired
SysDictMapper dictMapper;

    @Override
    public List<SysDict> getByType(LambdaQueryWrapper<SysDict> eq) {

        return  dictMapper.selectList(eq);
    }

    @Override
    public SysDict getById(Integer id) {
        return dictMapper.selectById(id);
    }

    @Override
    public Object deleteDictById(Integer id) {
    return     dictMapper.deleteById(id);
    }

    @Override
    public Object updateDict(DictDTO dictDTO) {
        SysDict sysDict=new SysDict();
        sysDict.setValue(dictDTO.getValue());
        sysDict.setLabel(dictDTO.getLabel());
        sysDict.setDescription(dictDTO.getDescription());
        sysDict.setRemarks(dictDTO.getRemarks());
        sysDict.setType(dictDTO.getType());
        sysDict.setId(dictDTO.getId());
       return dictMapper.updateById(sysDict);
    }

    @Override
    public IPage getUserWithRolePage(Page page, DictDTO dictDTO) {
        return dictMapper.getDictVosPage(page,dictDTO);
    }

    @Override
    public Object saveDict(DictDTO dictDTO) {
        SysDict sysDict=new SysDict();
        sysDict.setValue(dictDTO.getValue());
        sysDict.setLabel(dictDTO.getLabel());
        sysDict.setDescription(dictDTO.getDescription());
        sysDict.setRemarks(dictDTO.getRemarks());
        sysDict.setType(dictDTO.getType());
        sysDict.setId(dictDTO.getId());

        return sysDict.insert();
    }
}
