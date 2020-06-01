package com.springboot.multidatasource.service.slave;

import com.springboot.multidatasource.entity.Category;
import com.springboot.multidatasource.mapper.slave.SlaveMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class SlaveService {
    @Resource
    private SlaveMapper slaveMapper;

    public Category getCategory(int id){
        return slaveMapper.findById(id);
    }
}
