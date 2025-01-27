package com.example.E_Commerce.modal;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.*;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class BankDetails {
    @Id
    @GeneratedValue
    private Long id;
    private String accountNumber;
    private String accountHolderName;
    private String ifscCode;
}
