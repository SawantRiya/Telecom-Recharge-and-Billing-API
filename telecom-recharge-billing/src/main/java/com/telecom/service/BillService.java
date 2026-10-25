package com.telecom.service;

import com.telecom.model.Bill;
import com.telecom.repository.BillRepository;
import org.springframework.stereotype.Service;

@Service
public class BillService {

    private final BillRepository billRepository;

    public BillService(BillRepository billRepository) {
        this.billRepository = billRepository;
    }

    public Bill generateBill(int rechargeId, int customerId, double amount) {

        double tax = amount * 0.18;
        double totalAmount = amount + tax;

        Bill bill = new Bill();

        bill.setRechargeId(rechargeId);
        bill.setCustomerId(customerId);
        bill.setAmount(amount);
        bill.setTax(tax);
        bill.setTotalAmount(totalAmount);

        return billRepository.generateBill(bill);
    }

    public Bill getBillById(int id) {
        return billRepository.getBillById(id);
    }
}