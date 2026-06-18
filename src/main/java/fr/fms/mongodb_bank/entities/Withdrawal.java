package fr.fms.mongodb_bank.entities;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper=true)
public class Withdrawal extends Transaction {
    private double fee;
}
