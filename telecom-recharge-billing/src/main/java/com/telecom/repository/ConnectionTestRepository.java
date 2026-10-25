package com.telecom.repository;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class ConnectionTestRepository {

    private final JdbcTemplate jdbcTemplate;

    public ConnectionTestRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public String testConnection() {

        String sql = "SELECT message FROM test_connection WHERE id = 1";

        return jdbcTemplate.queryForObject(
                sql,
                String.class
        );
    }
}