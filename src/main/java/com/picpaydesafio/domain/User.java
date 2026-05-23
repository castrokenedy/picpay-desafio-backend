package com.picpaydesafio.domain;

import jakarta.persistence.*;
import lombok.*;
import java.math.BigDecimal;

@Entity(name = "users")
@Table(name = "users")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of = "id")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String firstName;
    private String lastName;

    @Column(unique = true)
    private String document; // CPF ou CNPJ

    @Column(unique = true)
    private String email;

    private String password;

    private BigDecimal balance; // Saldo da carteira

    @Enumerated(EnumType.STRING)
    private UserType userType;
}