package com.telecom.service;

import com.telecom.model.RechargePlan;
import com.telecom.repository.PlanRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PlanService {

    private final PlanRepository planRepository;

    public PlanService(PlanRepository planRepository) {
        this.planRepository = planRepository;
    }

    public RechargePlan addPlan(RechargePlan plan) {

        return planRepository.addPlan(plan);
    }

    public List<RechargePlan> getAllPlans() {

        return planRepository.getAllPlans();
    }

    public RechargePlan getPlanById(int id) {

        return planRepository.getPlanById(id);
    }
}