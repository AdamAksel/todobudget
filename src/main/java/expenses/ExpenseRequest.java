package expenses;

import budget.Budget;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ExpenseRequest {
    @NotBlank(message = "Name must not be null or empty")
    private String name;
    @NotNull(message = "Amount cannot be null or empty, please include an amount number")
    private double amount;
    @NotNull(message = "Budget must not be null or empty, please specify which budget you would like to create expense in")
    private Budget budget;
}

