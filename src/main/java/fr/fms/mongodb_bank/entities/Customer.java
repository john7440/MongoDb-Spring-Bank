package fr.fms.mongodb_bank.entities;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document
public class Customer {
    @Id
    private String id;

    @NotBlank(message="The first name is mandatory !")
    @Size(min =  3, max = 50)
    private String firstName;

    @NotBlank(message="The name is mandatory !")
    @Size(min =  3, max = 50)
    private String lastName;

    @NotBlank(message = "Email is mandatory")
    @Email
    @Size(max = 120)
    private String email;

    @NotBlank(message = "Address is mandatory!")
    @Size(min = 3, max = 150)
    private String address;
}
