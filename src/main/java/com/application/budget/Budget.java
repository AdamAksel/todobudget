package com.application.budget;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import com.application.expenses.Expense;
import com.application.user.User;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "budget")
public class Budget {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String name;
    @Builder.Default
    private LocalDateTime createdAt = LocalDateTime.now(ZoneId.systemDefault());
    private double amount;
    private String color;

    @ManyToOne()
    @JsonIgnore
    @JoinColumn(name = "_user_id")
    private User user;

    @OneToMany(mappedBy = "budget", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Expense> expenses;

}