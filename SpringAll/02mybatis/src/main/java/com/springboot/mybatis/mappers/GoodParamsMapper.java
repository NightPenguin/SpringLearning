package com.springboot.mybatis.mappers;

import com.springboot.mybatis.entity.GoodParams;

import java.util.List;
import java.util.Map;

public interface GoodParamsMapper {

    // 根据id 查找相应的信息
    public GoodParams findById(Integer gpId);

    // 批量查询
    public List<Map> findGoodParams();

    // 使用map 对象包含多个参数
    public List<Map> findGoodParamsByParam(Map param);

    // 动态查询
    public List<Map> findGoodParamsDynamic(Map param);

    // 添加数据
    public void create(GoodParams goodParams);

    //更新数据
    public void update(GoodParams goodParams);

    // 删除数据
    public void delete(Integer gpId);
}
