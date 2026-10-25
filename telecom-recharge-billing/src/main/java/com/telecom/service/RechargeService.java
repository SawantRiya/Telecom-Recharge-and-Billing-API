package com.telecom.service;

import com.telecom.model.Customer;
import com.telecom.model.Recharge;
import com.telecom.model.RechargePlan;
import com.telecom.model.RechargeRequest;
import com.telecom.model.RechargeResponse;
import com.telecom.repository.RechargeRepository;
import org.springframework.stereotype.Service;

@Service
public class RechargeService {

    private final RechargeRepository rechargeRepository;
    private final CustomerService customerService;
    private final PlanService planService;
    private final BillService billService;

    public RechargeService(
            RechargeRepository rechargeRepository,
            CustomerService customerService,
            PlanService planService,
            BillService billService) {

        this.rechargeRepository = rechargeRepository;
        this.customerService = customerService;
        this.planService = planService;
        this.billService = billService;
    }

    public RechargeResponse createRecharge(RechargeRequest request) {

        Customer customer =
                customerService.getCustomerById(request.getCustomerId());

        RechargePlan plan =
                planService.getPlanById(request.getPlanId());

        Recharge recharge = new Recharge();

        recharge.setCustomerId(customer.getCustomerId());
        recharge.setPlanId(plan.getPlanId());
        recharge.setAmount(plan.getAmount());
        recharge.setStatus("SUCCESS");

        Recharge savedRecharge =
                rechargeRepository.addRecharge(recharge);

        var bill = billService.generateBill(
                savedRecharge.getRechargeId(),
                customer.getCustomerId(),
                plan.getAmount()
        );

        RechargeResponse response = new RechargeResponse();

        response.setMessage("Recharge successful");
        response.setRechargeId(savedRecharge.getRechargeId());
        response.setBillId(bill.getBillId());
        response.setMobileNumber(customer.getMobileNumber());
        response.setPlanName(plan.getPlanName());
        response.setRechargeAmount(plan.getAmount());
        response.setTax(bill.getTax());
        response.setTotalAmount(bill.getTotalAmount());
        response.setStatus(savedRecharge.getStatus());

        return response;
    }
}