package com.xxy.rbac_cloud_upms_biz.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xxy.common.core.util.R;
import com.xxy.common.security.annotation.Inner;
import com.xxy.common.security.utils.SecurityUtils;
import com.xxy.rbac.admin.api.dto.DictDTO;
import com.xxy.rbac.admin.api.dto.UserDTO;
import com.xxy.rbac.admin.api.entity.SysDict;
import com.xxy.rbac.admin.api.entity.SysUser;
import com.xxy.rbac_cloud_common.log.annotation.SysLog;
import com.xxy.rbac_cloud_upms_biz.service.SysDictService;
import com.xxy.rbac_cloud_upms_biz.service.SysUserService;
import lombok.AllArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/dict")
public class SysDictController {
    private final SysDictService dictService;

    @GetMapping("/{type}")
    public R info(@PathVariable Integer type) {
        List<SysDict> dicts = dictService.getByType(Wrappers.<SysDict>query().lambda().eq(SysDict::getType, type));
        return R.ok(dicts);
    }


    @SysLog("删除字典")
    @DeleteMapping("/{id}")
    public R userDel(@PathVariable Integer id) {
        return R.ok(dictService.deleteDictById(id));
    }


    @SysLog("添加字典")
    @PostMapping
    public R user(@RequestBody DictDTO dictDTO) {
        return R.ok(dictService.saveDict(dictDTO));
    }


    @SysLog("更新字典信息")
    @PutMapping
    public R updateDict(@Valid @RequestBody DictDTO dictDTO) {
        return R.ok(dictService.updateDict(dictDTO));
    }

    /**
     * 分页查询字典
     *
     */
    @GetMapping("/page")
    public R getUserPage(Page page, DictDTO dictDTO) {
        return R.ok(dictService.getUserWithRolePage(page, dictDTO));
    }





}
