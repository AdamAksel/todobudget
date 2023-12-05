package com.application.budget;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/budgets")
public class BudgetController {

    @Autowired
    private BudgetService budgetService;

    @PostMapping("/create")
    public ResponseEntity<BudgetResponse> createBudget(@Valid @RequestBody BudgetRequest budgetRequest) {
        return ResponseEntity.ok(budgetService.createBudget(budgetRequest));
    }

    @GetMapping("/all")
    public ResponseEntity<BudgetResponse> getAllBudgetsByUser() {
        return ResponseEntity.ok(budgetService.getAllBudgetsByUser());
    }

    @GetMapping("/{id}")
    public ResponseEntity<BudgetResponse> getBudgetById(@PathVariable Long id) {
        return ResponseEntity.ok(budgetService.getBudgetById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<BudgetResponse> updateBudgetAmount(@PathVariable Long id, @RequestParam(value = "newAmount") double newAmount) {
        return ResponseEntity.ok(budgetService.updateBudgetAmount(id, newAmount));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<BudgetResponse> deleteExpenseById(@PathVariable Long id) {
        return ResponseEntity.ok(budgetService.deleteExpenseById(id));
    }
}