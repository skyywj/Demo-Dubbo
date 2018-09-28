package com.sky.hrpro.service;

import com.alibaba.dubbo.config.annotation.Service;
import com.sky.hrpro.dao.TestDao;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @Author: CarryJey
 * @Date: 2018/9/28 下午1:20
 */
@Service(version = "1.0.0")
public class DubboDemoService {

    @Autowired
    private TestDao testDao;

    public void addtest(){
        testDao.addTest();
    }

}
