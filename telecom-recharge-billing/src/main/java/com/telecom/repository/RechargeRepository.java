package com.telecom.repository;

import com.telecom.model.Recharge;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.Statement;

@Repository
public class RechargeRepository {

    private final JdbcTemplate jdbcTemplate;

    public RechargeRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public Recharge addRecharge(Recharge recharge) {

        String sql = "INSERT INTO recharges " +
                "(customer_id, plan_id, amount, status) " +
                "VALUES (?, ?, ?, ?)";

        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(connection -> {

            PreparedStatement ps = connection.prepareStatement(
                    sql,
                    Statement.RETURN_GENERATED_KEYS
            );

            ps.setInt(1, recharge.getCustomerId());
            ps.setInt(2, recharge.getPlanId());
            ps.setDouble(3, recharge.getAmount());
            ps.setString(4, recharge.getStatus());

            return ps;

        }, keyHolder);

        recharge.setRechargeId(keyHolder.getKey().intValue());

        return recharge;
    }
}