package com.springboot.aoplog;

import com.springboot.aoplog.entity.SysDict;
import com.springboot.aoplog.util.InitTableAndColumnUtil;

public class CreateSqlTest {

    public static void main(String[] args) {
        System.out.println(InitTableAndColumnUtil.createTable(SysDict.class));
    }
}
