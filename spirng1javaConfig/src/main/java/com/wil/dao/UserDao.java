package com.wil.dao;

import com.wil.entity.User;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.SingleColumnRowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

/**
 * Created by wil on 2017/10/30.
 */
@Repository
public class UserDao {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public void save() {
        String sql = "insert into t_user (name, address_id) values (?, ?)";
        jdbcTemplate.update(sql, "spring", 2);
    }

    public User findById(int id) {
        String sql = "select * from t_user where id = ?";
        return jdbcTemplate.queryForObject(sql, new BeanPropertyRowMapper<>(User.class),id);
    }

    public List<User> findAll() {
        String sql = "select * from t_user";
        return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(User.class));
    }

    public Long count() {
        String sql = "select count(*) from t_user";
        return jdbcTemplate.queryForObject(sql, Long.class);
    }

    public User findByName(String name) {
        String sql = "select * from t_user where name = ?";
        return jdbcTemplate.queryForObject(sql, new RowMapper<User>() {
            @Override
            public User mapRow(ResultSet rs, int rowNum) throws SQLException {
                User user = new User();
                user.setId(rs.getInt("id"));
                user.setName(rs.getString("name"));
                user.setAddress_id(rs.getInt("address_id"));
                return user;
            }
        }, name);
    }
}
