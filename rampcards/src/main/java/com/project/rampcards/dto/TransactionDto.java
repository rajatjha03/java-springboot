package com.project.rampcards.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.project.rampcards.entity.User;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.*;

import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class TransactionDto {
    @Id
    private int id;
    private String name;
    private Double amount;
    @JsonFormat(pattern = "dd-MM-yyyy")
    private LocalDate date;
    private String receipt;
    private Double memo;

    private int userId;
    private int quickbookId;
    private int cardId;
}
