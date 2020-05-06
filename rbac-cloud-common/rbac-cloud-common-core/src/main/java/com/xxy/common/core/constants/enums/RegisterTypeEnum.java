package com.xxy.common.core.constants.enums;

import com.baomidou.mybatisplus.core.enums.IEnum;

/**
 * @Author: XXY
 * @Date: 2020/5/2 15:56
 */
public enum RegisterTypeEnum implements IEnum<String> {
    ZOOKEEPER("zookeeper"), NACOS("nacos"), REDIS("redis");
    private String type;
    RegisterTypeEnum(String type){
        this.type = type;
    }

    @Override
    public String getValue() {
        return type;
    }
}
