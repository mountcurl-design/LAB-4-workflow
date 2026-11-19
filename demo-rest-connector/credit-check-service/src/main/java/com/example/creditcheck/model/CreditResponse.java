package com.example.creditcheck.model;

public record CreditResponse(
    String customerName,
    int demoScore,
    double estimatedEmi,
    double totalMonthlyEmi,
    double emiRatio,
    boolean eligible,
    String decision,
    String message
) {}
