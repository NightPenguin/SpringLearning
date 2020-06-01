package com.springboot.mybatis.controller;

import com.springboot.mybatis.entity.GoodParams;
import com.springboot.mybatis.service.GoodParamsService;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

@RestController
public class GoodParamsController {

    @Resource
    private GoodParamsService goodParamsService;

    /**
     * 根据ID 查询结果
     *
     * @param gpId
     * @return
     */
    @RequestMapping(value = "/goodparam/{gpId}", method = RequestMethod.GET)
    public GoodParams findById(@PathVariable("gpId") Integer gpId) {
        GoodParams goodParams = goodParamsService.findById(gpId);
        System.out.println(goodParams.getGpParamName() + "--" + goodParams.getGpParamValue());
        return goodParams;
    }

    /**
     * 批量查询
     * @return
     */
    @RequestMapping(value = "/goodparam/list", method = RequestMethod.GET)
    public List<Map> findGoodParams() {
        return goodParamsService.findGoodParams();
    }

    @RequestMapping(value = "/goodparam/byparams", method = RequestMethod.GET)
    public List<Map> findByParams(){

        return goodParamsService.findByParams(777,100);
    }
}
