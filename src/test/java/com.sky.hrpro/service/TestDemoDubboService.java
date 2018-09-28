package com.sky.hrpro.service;


import com.alibaba.dubbo.config.annotation.Reference;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.stereotype.Component;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @Author: CarryJey
 * @Date: 2018/9/28 下午1:22
 */

@SpringBootTest
@RunWith(SpringRunner.class)
@Component
public class TestDemoDubboService {

    @Reference(version = "1.0.0")
    private DubboDemoService dubboDemoService;


    @Test
    public void testDubbo(){
        dubboDemoService.addtest();
    }

}
