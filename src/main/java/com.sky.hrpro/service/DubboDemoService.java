package com.sky.hrpro.service;

import com.alibaba.dubbo.config.annotation.Service;
import com.sky.hrpro.dao.TestDao;
import com.sky.hypro.service.DemoBean;
import com.sky.hypro.service.DemoInterface;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @Author: CarryJey @Date: 2018/9/28 下午1:20
 */
@Service(version = "1.0.0")
public class DubboDemoService implements DemoInterface {

    @Autowired
    private TestDao testDao;

    /**
     * dubbo服务层去实现service-api定义的接口 返回对应的返回值 entity层就上浮到service-api中
     */
    @Override
    public DemoBean getDemoBean(int id) {
        return testDao.getById(id);
    }
}
