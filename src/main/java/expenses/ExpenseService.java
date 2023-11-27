package expenses;

import user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class ExpenseService {
    @Autowired
    private ExpenseRepository expenseRepository;
    @Autowired
    private BudgetRepository budgetRepository;

    @Transactional
    public ExpenseResponse createExpense(ExpenseRequest expenseRequest, Long budgetID) {
        User user = AuthenticationValidator.getAuthenticatedUser();

        Budget budget = budgetRepository.findById(budgetID)
                .orElseThrow(() -> new ResourceNotFoundException("Budget not found with ID: " + budgetID));

        String nameToUpperCase = Optional.ofNullable(expenseRequest.getName())
                .filter(name -> !name.isBlank())
                .map(name -> name.substring(0, 1).toUpperCase() + name.substring(1))
                .orElse(expenseRequest.getName());


        Expense expense = Expense.builder()
                .name(nameToUpperCase)
                .amount(expenseRequest.getAmount())
                .budget(budget)
                .user(user)
                .build();
        expenseRepository.save(expense);
        return ExpenseResponse.builder()
                .message("Successfully created expense! ")
                .expense(expense)
                .build();

    }

    public ExpenseResponse getAllExpensesByAuthenticatedUser() throws ResourceNotFoundException {
        User user = AuthenticationValidator.getAuthenticatedUser();

        List<Expense> expenses = expenseRepository.findAllByUser(user);
        if (expenses.isEmpty()) {
            throw new ResourceNotFoundException("No expenses found for the user");
        }
        return ExpenseResponse.builder()
                .expenseList(expenses)
                .build();

    }

    public ExpenseResponse getExpenseById(Long id) throws ResourceNotFoundException {
        User user = AuthenticationValidator.getAuthenticatedUser();

        Expense expense = expenseRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("No expense found with ID: " + id));

        if (!expense.getUser().getId().equals(user.getId())) {
            throw new UserNotAuthorizedException("Unauthorized to get expense with ID: " + id);
        }
        return ExpenseResponse.builder()
                .expense(expense)
                .build();
    }

    public ExpenseResponse deleteExpenseById(Long id) {
        User user = AuthenticationValidator.getAuthenticatedUser();

        Expense expense = expenseRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("No expense found with ID: " + id));

        if (!expense.getUser().getId().equals(user.getId())) {
            throw new UserNotAuthorizedException("Unauthorized to get expense with ID: " + id);
        }
        expenseRepository.deleteById(id);
        return ExpenseResponse.builder()
                .message("Successfully deleted expense with ID: " + id)
                .expense(expense)
                .build();
    }
}
