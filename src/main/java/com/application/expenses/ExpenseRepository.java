package com.application.expenses;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.application.user.User;
import java.util.List;

@Repository
public interface ExpenseRepository extends JpaRepository<Expense, Long> {
    List<Expense> findAllByBudgetId(Long budgetId);
    List<Expense> findAllByUser(User user);
}
