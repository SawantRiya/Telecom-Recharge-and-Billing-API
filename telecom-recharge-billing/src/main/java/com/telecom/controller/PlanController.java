package com.telecom.controller;

import com.telecom.model.RechargePlan;
import com.telecom.service.PlanService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/plans")
public class PlanController {

    private final PlanService planService;

    public PlanController(PlanService planService) {
        this.planService = planService;
    }

    @PostMapping
    public RechargePlan addPlan(@RequestBody RechargePlan plan) {

        return planService.addPlan(plan);
    }

    @GetMapping
    public List<RechargePlan> getAllPlans() {

        return planService.getAllPlans();
    }

    @GetMapping("/{id}")
    public RechargePlan getPlanById(@PathVariable int id) {

        return planService.getPlanById(id);
    }
}