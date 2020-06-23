package com.springboot.aoplog.mapper;

import com.springboot.aoplog.entity.SysLog;

/**
 * 日志操作类
 */
public interface SysLogMapper {

    int saveSysLog(SysLog sysLog);
}
