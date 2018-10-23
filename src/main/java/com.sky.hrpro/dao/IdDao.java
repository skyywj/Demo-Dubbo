package com.sky.hrpro.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Author: CarryJey @Date: 2018/10/22 19:47:10
 */
@Repository
public class IdDao {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    public List<Long> nextIds(int count) {
        return jdbcTemplate.queryForList(
                "SELECT id_gen.nextVal FROM dual WHERE count = ?", new Object[] {count}, Long.class);
    }

    public long nextId() {
        return nextIds(1).get(0);
    }
}
