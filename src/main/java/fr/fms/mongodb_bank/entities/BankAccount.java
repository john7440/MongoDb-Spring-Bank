package fr.fms.mongodb_bank.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Document
public class BankAccount {
    @Id
    private String id;
    private String accountNumber;
    private double balance;
    private LocalDate creationDate;
    private AccountStatus status;
    private String customerId;
}
