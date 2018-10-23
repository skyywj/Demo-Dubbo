package com.sky.hrpro.dao;

import com.sky.hrpro.util.Constant;
import com.sky.hypro.service.wallet.WalletBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;

/**
 * @Author: CarryJey @Date: 2018/10/22 18:06:34
 */
@Repository
public class WalletDao {

    @Autowired
    private NamedParameterJdbcTemplate jdbcTemplate;

    private static final BeanPropertyRowMapper<WalletBean> rowMapper =
        BeanPropertyRowMapper.newInstance(WalletBean.class);

    public BigDecimal getBalaceByUserId(long userId) {
        String sql = "SELECT * FROM user_wallet WHERE user_id = :userId";
        List<WalletBean> res =
            jdbcTemplate.query(sql, new MapSqlParameterSource(Constant.IM_USER_ID, userId), rowMapper);
        if (res.size() >0 ) {
            return res.get(0).getBalance();
        }
        return null;
    }

    public void updateBalace(long userId, BigDecimal balance, long updateTime) {
        String sql = "UPDATE user_wallet SET balance=:balance,update_time=:updateTime WHERE user_id = :userId";
        int rows =
            jdbcTemplate.update(
                sql,
                new MapSqlParameterSource(Constant.IM_USER_ID, userId)
                    .addValue("balance", balance)
                    .addValue("updateTime", updateTime));
        assert rows == 1;
    }

    public void initWallet(WalletBean walletBean) {
        String sql =
            "INSERT INTO user_wallet (id,user_id,balance,create_time,update_time) "
                + " VALUES (:id,:userId,:balance,:createTime,:updateTime)";
        int rows = jdbcTemplate.update(sql, new BeanPropertySqlParameterSource(walletBean));
        assert rows == 1;
    }
}
