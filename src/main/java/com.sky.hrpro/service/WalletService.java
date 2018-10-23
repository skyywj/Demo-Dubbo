package com.sky.hrpro.service;

import com.alibaba.dubbo.config.annotation.Reference;
import com.sky.hrpro.dao.WalletDao;
import com.sky.hrpro.dao.WalletInDao;
import com.sky.hrpro.dao.WalletOutDao;
import com.sky.hypro.service.Idservice.IdService;
import com.sky.hypro.service.wallet.RecordBean;
import com.sky.hypro.service.wallet.WalletBean;
import com.sky.hypro.service.wallet.WalletInterface;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.lang.invoke.MethodHandles;
import java.math.BigDecimal;
import java.util.List;

/**
 * @Author: CarryJey @Date: 2018/10/22 17:54:24
 * 暂时先不作为dubbo服务了，测试起来麻烦想改成dubbo服务，根据readme简单改一下就行了。
 * desc：钱包服务
 */
@Service
public class WalletService implements WalletInterface {

    private static final Logger LOG = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    @Autowired
    private WalletDao walletDao;

    @Autowired
    private WalletInDao walletInDao;

    @Autowired
    private WalletOutDao walletOutDao;

    @Reference
    private IdService idService;
    /**
     * 获取用户余额
     */
    @Override
    public BigDecimal getBalanceByUserId(long userId) {
        LOG.info("get wallet balance of userId : "+userId);
        BigDecimal balace = walletDao.getBalaceByUserId(userId);
        if(balace!=null){
            return balace;
        }
        LOG.info("Cont find userWallet of userId : " + userId);
        return BigDecimal.ZERO;
    }

    /**
     * 获取用户收入记录
     */
    @Override
    public List<RecordBean> getIncomeRecord(long userId) {
        LOG.info("get user income record of userId ：" + userId);
        List<RecordBean> incomeRecord = walletInDao.getIncomeRecord(userId);
        if(incomeRecord.size() > 0 ){
            return incomeRecord;
        }
        LOG.info("Con't find user income of userId : " + userId);
        return null;
    }

    /**
     * 获取用户支出记录
     */
    @Override
    public List<RecordBean> getOutcomeRecord(long userId) {
        LOG.info("get user outcome record of userId ：" + userId);
        List<RecordBean> outcomeRecord = walletOutDao.getOutcomeRecord(userId);
        if(outcomeRecord.size() > 0 ){
            return outcomeRecord;
        }
        LOG.info("Con't find user outcome of userId : " + userId);
        return null;
    }

    /**
     * 采用事务的方式保证一致性，防止明细变了之后出错导致钱包没变出错
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean updateBalance(RecordBean recordBean) {
        recordBean.setId(idService.snowflakeNextId());
        BigDecimal record = walletDao.getBalaceByUserId(recordBean.getUserId());
        if (record == null) {
            initUserWallet(recordBean);
        }
        if (recordBean.getType().equals("IN")) {
            if(record != null){
                walletDao.updateBalace(recordBean.getUserId(),record.add(recordBean.getAmount()),System.currentTimeMillis());
            }
            walletInDao.addIncomeRecord(recordBean);
            LOG.info("userId : " + recordBean.getUserId() + " get income : " + recordBean.getAmount());
        } else if (recordBean.getType().equals("OUT")) {
            if(record!= null){
                walletDao.updateBalace(recordBean.getUserId(),record.subtract(recordBean.getAmount()),System.currentTimeMillis());
            }
            walletOutDao.addOutcomeRecord(recordBean);
            LOG.info("userId : " + recordBean.getUserId() + " get outcome : " + recordBean.getAmount());
        }else {
            LOG.info("updateBalance type error of userId : " + recordBean.getUserId());
            return false;
        }
        LOG.info("balance update success of userId : " + recordBean.getUserId());
        return true;
    }

    private void initUserWallet(RecordBean recordBean) {
        WalletBean walletBean = new WalletBean();
        walletBean.setId(idService.snowflakeNextId());
        walletBean.setUserId(recordBean.getUserId());
        walletBean.setBalance(BigDecimal.ZERO.add(recordBean.getAmount()));
        walletBean.setCreateTime(System.currentTimeMillis());
        walletBean.setUpdateTime(System.currentTimeMillis());
        walletDao.initWallet(walletBean);
    }

}
