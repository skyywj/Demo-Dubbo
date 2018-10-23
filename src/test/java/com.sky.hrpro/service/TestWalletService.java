package com.sky.hrpro.service;

import com.sky.hypro.service.wallet.RecordBean;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.stereotype.Component;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.util.List;

/**
 * @Author: CarryJey
 * @Date: 2018/10/22 19:37:39
 */
@Component
@SpringBootTest
@RunWith(SpringRunner.class)
public class TestWalletService {

    @Autowired
    private WalletService walletService;

    @Test
    public void testChange(){
        RecordBean recordBean = new RecordBean();
        recordBean.setId(8);
        recordBean.setAmount(new BigDecimal(110));
        recordBean.setType("OUT");
        recordBean.setDesc("ceshi");
        recordBean.setUserId(12345);
        recordBean.setUserPhone("+86123456");
        recordBean.setFromUserId(98765);
        recordBean.setFromUserPhone("+8698765");
        recordBean.setCreateTime(System.currentTimeMillis());
        recordBean.setUpdateTime(System.currentTimeMillis());
        walletService.updateBalance(recordBean);
    }

    @Test
    public void getIncomeRecord(){
        List<RecordBean> res = walletService.getIncomeRecord(123456);
        for(RecordBean r : res){
            System.out.println(r);
        }
    }

    @Test
    public void getOutcomeRecord(){
        List<RecordBean> res = walletService.getOutcomeRecord(123456);
        for (RecordBean r : res){
            System.out.println(r);
        }
    }

    @Test
    public void getBalance(){
        BigDecimal res = walletService.getBalanceByUserId(123456);
        System.out.println(res);
    }

}
