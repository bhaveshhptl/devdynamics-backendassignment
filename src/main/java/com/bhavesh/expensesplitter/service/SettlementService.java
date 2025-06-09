package com.bhavesh.expensesplitter.service;

import com.bhavesh.expensesplitter.model.SettlementTransaction;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.*;

@Service
public class SettlementService {
    public List<SettlementTransaction> simplifyBalances(Map<String, BigDecimal> balances) {
        List<SettlementTransaction> settlements = new ArrayList<>();
        Map<String, BigDecimal> positive = new HashMap<>();
        Map<String, BigDecimal> negative = new HashMap<>();

        for (var entry : balances.entrySet()) {
            if (entry.getValue().compareTo(BigDecimal.ZERO) > 0) {
                positive.put(entry.getKey(), entry.getValue());
            } else if (entry.getValue().compareTo(BigDecimal.ZERO) < 0) {
                negative.put(entry.getKey(), entry.getValue().negate());
            }
        }

        Iterator<Map.Entry<String, BigDecimal>> posIt = positive.entrySet().iterator();
        Iterator<Map.Entry<String, BigDecimal>> negIt = negative.entrySet().iterator();

        Map.Entry<String, BigDecimal> pos = posIt.hasNext() ? posIt.next() : null;
        Map.Entry<String, BigDecimal> neg = negIt.hasNext() ? negIt.next() : null;

        while (pos != null && neg != null) {
            BigDecimal min = pos.getValue().min(neg.getValue());
            settlements.add(new SettlementTransaction(neg.getKey(), pos.getKey(), min));

            pos.setValue(pos.getValue().subtract(min));
            neg.setValue(neg.getValue().subtract(min));

            if (pos.getValue().compareTo(BigDecimal.ZERO) == 0) pos = posIt.hasNext() ? posIt.next() : null;
            if (neg.getValue().compareTo(BigDecimal.ZERO) == 0) neg = negIt.hasNext() ? negIt.next() : null;
        }
        return settlements;
    }
}
