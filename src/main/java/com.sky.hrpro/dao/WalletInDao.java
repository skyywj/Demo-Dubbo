package com.sky.hrpro.dao;

import com.sky.hrpro.util.Constant;
import com.sky.hypro.service.wallet.RecordBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Author: CarryJey @Date: 2018/10/22 19:11:24
 */
@Repository
public class WalletInDao {
    @Autowired
    private NamedParameterJdbcTemplate jdbcTemplate;

    private static final BeanPropertyRowMapper<RecordBean> rowMapper =
        BeanPropertyRowMapper.newInstance(RecordBean.class);

    public List<RecordBean> getIncomeRecord(long userId) {
        String sql = "SELECT * FROM wallet_in WHERE user_id = :userId ORDER BY create_time DESC ";
        List<RecordBean> res =
            jdbcTemplate.query(sql, new MapSqlParameterSource(Constant.IM_USER_ID, userId), rowMapper);
        return res;
    }

    //注意desc是mysql中的关键字，用的话需要在字段加``
    public int addIncomeRecord(RecordBean recordBean){
        String sql = "INSERT INTO wallet_in (id,user_id,user_phone,from_user_id,from_user_phone,amount,type,`desc`,create_time,update_time) " +
                " VALUES (:id,:userId,:userPhone,:fromUserId,:fromUserPhone,:amount,:type,:desc,:createTime,:updateTime)";
        int rows = jdbcTemplate.update(sql,new BeanPropertySqlParameterSource(recordBean));
        assert rows == 1;
        return rows;
    }
}
