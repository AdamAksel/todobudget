package budget;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class BudgetResponse {
    private String message;
    private boolean errorOccurred;
    private Budget budget;
    private List<Budget> budgetList;
}