package com.example.restapi.service.impl;

import com.example.restapi.dto.ExpenseDTO;
import com.example.restapi.entity.ExpenseEntity;
import com.example.restapi.repository.ExpenseRepository;
import com.example.restapi.service.ExpenseService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.CrossOrigin;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Service implementation for Expense module
 * @author boroboro01
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class ExpenseServiceImpl implements ExpenseService {

    private final ExpenseRepository expenseRepository;
    private final ModelMapper modelMapper;

    /**
     * It will fetch the expenses from database
     * @return list
     */
    @Override
    public List<ExpenseDTO> getAllExpenses() {
        // Call the repository method
        List<ExpenseEntity> list = expenseRepository.findAll();
        log.info("Printing the data from repository {}", list);
        // Convert the Entity object to DTO object
        List<ExpenseDTO> listOfExpenses = list.stream().map(expenseEntity -> mapToExpenseDTO(expenseEntity)).collect(Collectors.toList());
        // Return the list
        return listOfExpenses;
    }

    /**
     * Mapper method for converting ExpenseEntity object to ExpenseEntity
     * @param expenseEntity (expense entity)
     * @return ExpenseDTO
     */
    private ExpenseDTO mapToExpenseDTO(ExpenseEntity expenseEntity) {
        return modelMapper.map(expenseEntity, ExpenseDTO.class);
    }
}