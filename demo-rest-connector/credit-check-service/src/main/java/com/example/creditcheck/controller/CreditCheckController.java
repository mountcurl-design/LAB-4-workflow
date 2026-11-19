package com.example.creditcheck.controller;

import org.springframework.web.bind.annotation.*;
import com.example.creditcheck.model.*;
import com.example.creditcheck.service.CreditCheckService;

@RestController
@RequestMapping("/api")
public class CreditCheckController {

    private final CreditCheckService service;

    public CreditCheckController(CreditCheckService service) {
        this.service = service;
    }

    @PostMapping("/credit-check")
    public CreditResponse creditCheck(@RequestBody CreditRequest request) {
        return service.check(request);
    }
}
