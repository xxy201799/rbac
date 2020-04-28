package com.xxy.rbac.admin.api.vo;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class DictVO implements Serializable {
    private static final long serialVersionUID = 1L;

    private  Integer id;
    private  String value;
    private  String label;
    private  String type;
    private  String description;
    private Integer sort;
private Date  createTime;
private  Date updateTime;
private  String remarks;
private   String delFlag;

}
