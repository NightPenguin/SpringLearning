package com.springboot.multidatasource.controller;

import com.springboot.multidatasource.entity.Category;
import com.springboot.multidatasource.entity.GoodParams;
import com.springboot.multidatasource.service.master.MasterService;
import com.springboot.multidatasource.service.slave.SlaveService;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
public class TestController {
    @Resource
    private MasterService masterService;

    @Resource
    private SlaveService slaveService;

    @RequestMapping(value = "/master/{id}", method = RequestMethod.GET)
    public GoodParams getGoodeParams(@PathVariable(value = "id") int id){
        return masterService.getGoodParams(id);
    }

    @RequestMapping(value = "/slave/{id}", method = RequestMethod.GET)
    public Category getCategory(@PathVariable(value = "id") int id){
        return slaveService.getCategory(id);
    }
}
