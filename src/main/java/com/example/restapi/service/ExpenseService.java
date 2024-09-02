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

    /**
     * It will delete the expense from database
     * @param expenseId
     * @return void
     */
    void deleteExpenseByExpenseId(String expenseId);

    /**
     * It will save the expense details to database
     * @param expenseDTO (expense dto)
     * @return ExpenseDTO
     */
    ExpenseDTO saveExpenseDetails(ExpenseDTO expenseDTO);

    /**
     * It will update the expense details to database
     * @param expenseDTO (expense dto)
     * @param expenseId (expense id)
     * @return ExpenseDTO
     */
    ExpenseDTO updateExpenseDetails(ExpenseDTO expenseDTO, String expenseId);
}
