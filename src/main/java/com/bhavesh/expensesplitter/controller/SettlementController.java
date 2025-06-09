package com.bhavesh.expensesplitter.controller;

import com.bhavesh.expensesplitter.model.SettlementTransaction;
import com.bhavesh.expensesplitter.service.BalanceService;
import com.bhavesh.expensesplitter.service.SettlementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

@RestController
public class SettlementController {
    @Autowired
    private BalanceService balanceService;
    @Autowired
    private SettlementService settlementService;

    @GetMapping("/settlements")
    public List<SettlementTransaction> getSettlements() {
        return settlementService.simplifyBalances(balanceService.calculateBalances());
    }

    @GetMapping("/balances")
    public Map<String, BigDecimal> getBalances() {
        return balanceService.calculateBalances();
    }
}
