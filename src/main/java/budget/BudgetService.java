package budget;

import lombok.RequiredArgsConstructor;

import exceptions.ResourceNotFoundException;
import exceptions.UserNotAuthorizedException;
import todo.Todo;
import todo.TodoResponse;
import user.User;
import utils.AuthenticationValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Service
@RequiredArgsConstructor
public class BudgetService {
    @Autowired
    private BudgetRepository budgetRepository;

    public BudgetResponse createBudget(BudgetRequest budgetRequest) {
        User user = AuthenticationValidator.getAuthenticatedUser();
        String nameToUpperCase = Optional.ofNullable(budgetRequest.getName())
                .filter(name -> !name.isEmpty())
                .map(name -> name.substring(0, 1).toUpperCase() + name.substring(1))
                .orElse(budgetRequest.getName());

        Budget budget = Budget.builder()
                .amount(budgetRequest.getAmount())
                .name(nameToUpperCase)
                .color(budgetRequest.getColor())
                .user(user)
                .build();

        budgetRepository.save(budget);
        return BudgetResponse.builder()
                .budget(budget)
                .build();
    }

    public BudgetResponse getAllBudgetsByUser() throws ResourceNotFoundException {
        User user = AuthenticationValidator.getAuthenticatedUser();
        List<Budget> budgets = budgetRepository.findAllByUser(user);

        if (budgets.isEmpty()) {
            throw new ResourceNotFoundException("No budgets found for the user");
        }
        return BudgetResponse.builder()
                .budgetList(budgets)
                .build();
    }

    public BudgetResponse getBudgetById(Long id) throws ResourceNotFoundException, UserNotAuthorizedException {
        User user = AuthenticationValidator.getAuthenticatedUser();
        Budget budget = budgetRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("No Budgets found with ID: " + id));

        if (!budget.getUser().getId().equals(user.getId())) {
            throw new UserNotAuthorizedException("Unauthorized to get Budget with ID: " + id);
        }
        return BudgetResponse.builder()
                .budget(budget)
                .build();
    }

    public BudgetResponse updateBudgetAmount(Long id, double newAmount) throws ResourceNotFoundException, UserNotAuthorizedException {
        User user = AuthenticationValidator.getAuthenticatedUser();
        Budget budget = budgetRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Budget not found with id: " + id));

        if (!budget.getUser().getId().equals(user.getId())) {
            throw new UserNotAuthorizedException("Unauthorized to update budget with ID: " + id);
        }
        budget.setAmount(newAmount);
        budgetRepository.save(budget);
        return BudgetResponse.builder()
                .budget(budget)
                .build();
    }

    public BudgetResponse deleteExpenseById(Long id) throws ResourceNotFoundException, UserNotAuthorizedException {
        User user = AuthenticationValidator.getAuthenticatedUser();
        Budget budget = budgetRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("No budget found with ID: " + id));

        if (!budget.getUser().getId().equals(user.getId())) {
            throw new UserNotAuthorizedException("No authorization to delete budget with ID: " + id);
        }

        budgetRepository.deleteById(id);
        return BudgetResponse.builder()
                .message("Successfully deleted budget with ID: " + id)
                .budget(budget)
                .build();
    }
}
