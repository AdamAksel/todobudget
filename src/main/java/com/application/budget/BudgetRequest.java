package com.application.budget;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BudgetRequest {
    @NotBlank(message = "Name can not be null or empty")
    private String name;
    @DecimalMin(value = "0.1", message = "Amount can not be null or empty")
    private double amount;
    private String color;
}