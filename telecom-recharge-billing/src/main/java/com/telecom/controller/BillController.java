package com.telecom.controller;

import com.telecom.model.Bill;
import com.telecom.service.BillService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/bills")
public class BillController {

    private final BillService billService;

    public BillController(BillService billService) {
        this.billService = billService;
    }

    @GetMapping("/{id}")
    public Bill getBillById(@PathVariable int id) {

        return billService.getBillById(id);
    }
}