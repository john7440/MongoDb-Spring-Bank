package fr.fms.mongodb_bank.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Document
public abstract class Transaction {
    @Id
    private String id;
    private String sourceAccountId;
    private double amount;
    private LocalDateTime transactionDate;
    private String description;
}
