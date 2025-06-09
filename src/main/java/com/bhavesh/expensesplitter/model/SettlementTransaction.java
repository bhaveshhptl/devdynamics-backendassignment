package com.bhavesh.expensesplitter.model;

import java.math.BigDecimal;

public record SettlementTransaction(String from, String to, BigDecimal amount) {

}
