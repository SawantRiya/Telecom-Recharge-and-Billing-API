package com.telecom.repository;

import com.telecom.model.Customer;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.List;

@Repository
public class CustomerRepository {

    private final JdbcTemplate jdbcTemplate;

    public CustomerRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public Customer addCustomer(Customer customer) {

        String sql = "INSERT INTO customers (name, mobile_number, email) VALUES (?, ?, ?)";

        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(connection -> {

            PreparedStatement ps = connection.prepareStatement(
                    sql,
                    Statement.RETURN_GENERATED_KEYS
            );

            ps.setString(1, customer.getName());
            ps.setString(2, customer.getMobileNumber());
            ps.setString(3, customer.getEmail());

            return ps;

        }, keyHolder);

        customer.setCustomerId(keyHolder.getKey().intValue());

        return customer;
    }

    public List<Customer> getAllCustomers() {

        String sql = "SELECT * FROM customers";

        return jdbcTemplate.query(sql, (rs, rowNum) -> {

            Customer customer = new Customer();

            customer.setCustomerId(rs.getInt("customer_id"));
            customer.setName(rs.getString("name"));
            customer.setMobileNumber(rs.getString("mobile_number"));
            customer.setEmail(rs.getString("email"));

            return customer;
        });
    }

    public Customer getCustomerById(int id) {

        String sql = "SELECT * FROM customers WHERE customer_id = ?";

        return jdbcTemplate.queryForObject(
                sql,
                (rs, rowNum) -> {

                    Customer customer = new Customer();

                    customer.setCustomerId(rs.getInt("customer_id"));
                    customer.setName(rs.getString("name"));
                    customer.setMobileNumber(rs.getString("mobile_number"));
                    customer.setEmail(rs.getString("email"));

                    return customer;
                },
                id
        );
    }
}