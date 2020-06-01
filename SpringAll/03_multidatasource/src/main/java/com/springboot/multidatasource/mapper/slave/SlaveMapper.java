package com.springboot.multidatasource.mapper.slave;

import com.springboot.multidatasource.entity.Category;

public interface SlaveMapper {
    public Category findById(int gpId);
}
