package com.telecom.repository;

import com.telecom.model.Bill;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.Statement;

@Repository
public class BillRepository {

    private final JdbcTemplate jdbcTemplate;

    public BillRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public Bill generateBill(Bill bill) {

        String sql = "INSERT INTO bills " +
                "(recharge_id, customer_id, amount, tax, total_amount) " +
                "VALUES (?, ?, ?, ?, ?)";

        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(connection -> {

            PreparedStatement ps = connection.prepareStatement(
                    sql,
                    Statement.RETURN_GENERATED_KEYS
            );

            ps.setInt(1, bill.getRechargeId());
            ps.setInt(2, bill.getCustomerId());
            ps.setDouble(3, bill.getAmount());
            ps.setDouble(4, bill.getTax());
            ps.setDouble(5, bill.getTotalAmount());

            return ps;

        }, keyHolder);

        bill.setBillId(keyHolder.getKey().intValue());

        return bill;
    }

    public Bill getBillById(int id) {

        String sql = "SELECT * FROM bills WHERE bill_id = ?";

        return jdbcTemplate.queryForObject(
                sql,
                (rs, rowNum) -> {

                    Bill bill = new Bill();

                    bill.setBillId(rs.getInt("bill_id"));
                    bill.setRechargeId(rs.getInt("recharge_id"));
                    bill.setCustomerId(rs.getInt("customer_id"));
                    bill.setBillDate(rs.getString("bill_date"));
                    bill.setAmount(rs.getDouble("amount"));
                    bill.setTax(rs.getDouble("tax"));
                    bill.setTotalAmount(rs.getDouble("total_amount"));

                    return bill;
                },
                id
        );
    }
}