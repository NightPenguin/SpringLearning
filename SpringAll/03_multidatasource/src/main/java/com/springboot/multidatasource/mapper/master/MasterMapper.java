package com.springboot.multidatasource.mapper.master;

import com.springboot.multidatasource.entity.GoodParams;

public interface MasterMapper {
    public GoodParams findById(int gpId);
}
