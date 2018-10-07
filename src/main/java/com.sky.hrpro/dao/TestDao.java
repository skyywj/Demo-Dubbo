package com.sky.hrpro.dao;

import api.demo.bean.DemoBean;
import com.sky.hrpro.entity.TestEntity;
import com.sun.org.apache.bcel.internal.generic.NEW;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Author: CarryJey
 * @Date: 2018/9/27 上午10:29
 */
@Repository
public class TestDao {

    private static final BeanPropertyRowMapper<DemoBean> rowMapper = BeanPropertyRowMapper.newInstance(DemoBean.class);


    @Autowired
    private NamedParameterJdbcTemplate jdbcTemplate;

    public void addTest(TestEntity testEntity){
        String sql = "INSERT INTO test (name,age) VALUES('ywj',23)";
        int rows = jdbcTemplate.update(sql,new BeanPropertySqlParameterSource(testEntity));
        assert rows == 1;
    }


    public DemoBean getById(int id){
        String sql = "SELECT * FROM test WHERE id = :id";
        List<DemoBean> res = jdbcTemplate.query(sql,new MapSqlParameterSource("id",id),rowMapper);
        return res.get(0);
    }

}
