package com.example.restapi.service;

import com.example.restapi.dto.ExpenseDTO;

import java.util.List;

/**
 * Service interface for Expense module
 * @author boroboro01
 */
public interface ExpenseService {

    /**
     * It will fetch the expenses from database
     * @return list
     */
    List<ExpenseDTO> getAllExpenses();

    /**
     * It will fetch the single expense details from database
     * @param expenseId
     * @return ExpenseDTO
     */
    ExpenseDTO getExpenseByExpenseId(String expenseId);
}
