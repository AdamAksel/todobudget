package com.application.expenses;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/expenses")
public class ExpenseController {
    @Autowired
    private ExpenseService expenseService;

    @PostMapping("/create/{budgetID}")
    public ResponseEntity<ExpenseResponse> createExpense(@RequestBody ExpenseRequest expenseRequest, @PathVariable Long budgetID) {
        return ResponseEntity.ok(expenseService.createExpense(expenseRequest, budgetID));
    }

    @GetMapping("all")
    public ResponseEntity<ExpenseResponse> getAllExpensesByUser() {
        return ResponseEntity.ok(expenseService.getAllExpensesByAuthenticatedUser());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ExpenseResponse> getExpenseById(@PathVariable Long id) {
        return ResponseEntity.ok(expenseService.getExpenseById(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ExpenseResponse> deleteExpenseById(@PathVariable Long id) {
        return ResponseEntity.ok(expenseService.deleteExpenseById(id));
    }
}