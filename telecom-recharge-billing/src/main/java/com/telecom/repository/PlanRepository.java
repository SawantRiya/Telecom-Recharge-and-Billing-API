package com.telecom.repository;

import com.telecom.model.RechargePlan;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.List;

@Repository
public class PlanRepository {

    private final JdbcTemplate jdbcTemplate;

    public PlanRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public RechargePlan addPlan(RechargePlan plan) {

        String sql = "INSERT INTO recharge_plans " +
                "(plan_name, amount, validity_days, data_gb, voice_minutes, sms_count) " +
                "VALUES (?, ?, ?, ?, ?, ?)";

        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(connection -> {

            PreparedStatement ps = connection.prepareStatement(
                    sql,
                    Statement.RETURN_GENERATED_KEYS
            );

            ps.setString(1, plan.getPlanName());
            ps.setDouble(2, plan.getAmount());
            ps.setInt(3, plan.getValidityDays());
            ps.setDouble(4, plan.getDataGb());
            ps.setInt(5, plan.getVoiceMinutes());
            ps.setInt(6, plan.getSmsCount());

            return ps;

        }, keyHolder);

        plan.setPlanId(keyHolder.getKey().intValue());

        return plan;
    }

    public List<RechargePlan> getAllPlans() {

        String sql = "SELECT * FROM recharge_plans";

        return jdbcTemplate.query(sql, (rs, rowNum) -> {

            RechargePlan plan = new RechargePlan();

            plan.setPlanId(rs.getInt("plan_id"));
            plan.setPlanName(rs.getString("plan_name"));
            plan.setAmount(rs.getDouble("amount"));
            plan.setValidityDays(rs.getInt("validity_days"));
            plan.setDataGb(rs.getDouble("data_gb"));
            plan.setVoiceMinutes(rs.getInt("voice_minutes"));
            plan.setSmsCount(rs.getInt("sms_count"));

            return plan;
        });
    }

    public RechargePlan getPlanById(int id) {

        String sql = "SELECT * FROM recharge_plans WHERE plan_id = ?";

        return jdbcTemplate.queryForObject(
                sql,
                (rs, rowNum) -> {

                    RechargePlan plan = new RechargePlan();

                    plan.setPlanId(rs.getInt("plan_id"));
                    plan.setPlanName(rs.getString("plan_name"));
                    plan.setAmount(rs.getDouble("amount"));
                    plan.setValidityDays(rs.getInt("validity_days"));
                    plan.setDataGb(rs.getDouble("data_gb"));
                    plan.setVoiceMinutes(rs.getInt("voice_minutes"));
                    plan.setSmsCount(rs.getInt("sms_count"));

                    return plan;
                },
                id
        );
    }
}