package com.project.rampcards.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;


@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@Getter
@Setter
@Table(name = "transaction")
public class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "name", length = 200, nullable = false)
    private String name;
    @Column(name = "amount", length = 200, nullable = false)
    private Double amount;
    @Column(name = "date", length = 200, nullable = false)
    private LocalDate date;
    @Column(name = "receipt", length = 200, nullable = false)
    private String receipt;
    @Column(name = "memo", length = 200, nullable = false)
    private Double memo;

    @ManyToOne
    @JoinColumn(name ="user_id",nullable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name = "quickbook_id",nullable = false)
    private Quickbook quickbook;

    @OneToOne
    @JoinColumn(name = "card_id",nullable = false)
    private Card card;

}

