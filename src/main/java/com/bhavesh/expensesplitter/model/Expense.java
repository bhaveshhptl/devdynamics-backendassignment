package com.bhavesh.expensesplitter.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;
import lombok.Data;

@Data
@Entity
public class Expense {
        public enum SplitType { EQUAL, PERCENTAGE, EXACT }

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;

        @Positive(message = "Amount must be positive")
        @Column(precision = 10, scale = 2)
        private BigDecimal amount;

        @NotBlank(message = "Description is required")
        private String description;

        @NotBlank(message = "Paid by is required")
        private String paidBy;

        @ElementCollection
        @CollectionTable(name = "expense_shares", joinColumns = @JoinColumn(name = "expense_id"))
        @MapKeyColumn(name = "person")
        @Column(name = "share")
        private Map<String, BigDecimal> shares = new HashMap<>();

        @Enumerated(EnumType.STRING)
        private SplitType splitType = SplitType.EQUAL;

        // Getters and setters omitted for brevity (use Lombok @Data if you like)
}
