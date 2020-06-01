package com.springboot.multidatasource.service.master;

import com.springboot.multidatasource.entity.GoodParams;
import com.springboot.multidatasource.mapper.master.MasterMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class MasterService {
    @Resource
    private MasterMapper masterMapper;

    public GoodParams getGoodParams(int id){
        return masterMapper.findById(id);
    }
}
