package com.springboot.aoplog.entity;

import com.springboot.aoplog.annotation.Column;
import com.springboot.aoplog.annotation.Table;

import java.util.Date;

@Table("sys_dict")
public class SysDict {
    @Column(name = "id", length = 64, isNotNull = true, isAutoIncrement = true, comment = "编号", isPrimaryKey = true)
    int id;
    @Column(name = "type", length = 100, defaultValue = "NULL")
    String type;
    @Column(name = "description", length = 100, defaultValue = "NULL")
    String description;
    @Column(name = "create_time", isNotNull = true, defaultValue = "CURRENT_TIMESTAMP", comment = "创建时间")
    Date createtime;
    @Column(name = "updatetime", isNotNull = true, defaultValue = "CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP", comment = "更新时间")
    Date updatetime;
    @Column(name = "remark", length = 255, defaultValue = "NULL")
    String remark;
    @Column(name = "system", length = 1, defaultValue = "0")
    char system;
    @Column(name = "del_flag", length = 1, defaultValue = "0")
    char delflag;
    @Column(name = "tenant_id", length = 11, isNotNull = true, defaultValue = "0", comment = "所属租户")
    int tenantid;
}
