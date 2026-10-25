package com.telecom.controller;

import com.telecom.model.RechargeRequest;
import com.telecom.model.RechargeResponse;
import com.telecom.service.RechargeService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/recharges")
public class RechargeController {

    private final RechargeService rechargeService;

    public RechargeController(RechargeService rechargeService) {
        this.rechargeService = rechargeService;
    }

    @PostMapping
    public RechargeResponse createRecharge(
            @RequestBody RechargeRequest request) {

        return rechargeService.createRecharge(request);
    }
}