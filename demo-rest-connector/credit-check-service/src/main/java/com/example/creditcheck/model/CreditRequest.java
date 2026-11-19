package com.example.creditcheck.model;

public record CreditRequest(
    String customerName,
    double monthlyIncome,
    double existingEmi,
    double loanAmount,
    int tenureMonths
) {}
