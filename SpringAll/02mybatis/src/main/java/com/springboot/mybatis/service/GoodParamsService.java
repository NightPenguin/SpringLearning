package com.springboot.mybatis.service;

import com.springboot.mybatis.entity.GoodParams;
import com.springboot.mybatis.mappers.GoodParamsMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class GoodParamsService {

    @Resource
    private GoodParamsMapper goodParamsMapper;

    /**
     * 根据id 查询结果
     * @param gpId
     * @return
     */
    public GoodParams findById(Integer gpId){
        return goodParamsMapper.findById(gpId);
    }

    /**
     *  批量查询
     * @return
     */
    public List<Map> findGoodParams(){
        return goodParamsMapper.findGoodParams();
    }

    public List<Map> findByParams(int goodId, int goodOrder){
        Map<Object,Object> params = new HashMap<>();
        params.put("goodId", goodId);
        params.put("goodOrder",goodOrder);
        return goodParamsMapper.findGoodParamsByParam(params);
    }
}
