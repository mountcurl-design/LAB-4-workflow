package com.example.creditcheck.service;

import org.springframework.stereotype.Service;
import com.example.creditcheck.model.*;

@Service
public class CreditCheckService {

    public CreditResponse check(CreditRequest request) {
        double income = request.monthlyIncome();
        double amount = request.loanAmount();
        int tenure = request.tenureMonths();

        if (request.customerName() == null || request.customerName().isBlank()
                || income <= 0 || amount <= 0 || tenure <= 0
                || request.existingEmi() < 0) {
            throw new IllegalArgumentException("Invalid loan application details");
        }

        // Illustrative annual interest rate: 12%
        double monthlyRate = 0.12 / 12.0;
        double emi = amount * monthlyRate
                * Math.pow(1 + monthlyRate, tenure)
                / (Math.pow(1 + monthlyRate, tenure) - 1);

        double totalEmi = request.existingEmi() + emi;
        double emiRatio = (totalEmi / income) * 100.0;

        // Simulated score for a classroom exercise only
        int score = 900;
        if (emiRatio > 50) score -= 250;
        else if (emiRatio > 40) score -= 150;
        else if (emiRatio > 30) score -= 75;
        if (income < 25000) score -= 100;
        score = Math.max(300, Math.min(900, score));

        String decision;
        if (score >= 700 && emiRatio <= 40) {
            decision = "ELIGIBLE";
        } else if (score >= 600 && emiRatio <= 50) {
            decision = "MANUAL_REVIEW";
        } else {
            decision = "NOT_ELIGIBLE";
        }

        String message = switch (decision) {
            case "ELIGIBLE" -> "Meets the demonstration eligibility rules";
            case "MANUAL_REVIEW" -> "Requires manual review";
            default -> "Does not meet the demonstration eligibility rules";
        };

        return new CreditResponse(
            request.customerName(),
            score,
            round2(emi),
            round2(totalEmi),
            round2(emiRatio),
            "ELIGIBLE".equals(decision),
            decision,
            message
        );
    }

    private double round2(double value) {
        return Math.round(value * 100.0) / 100.0;
    }
}
