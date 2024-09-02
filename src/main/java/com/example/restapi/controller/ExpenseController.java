package com.example.restapi.controller;

import com.example.restapi.dto.ExpenseDTO;
import com.example.restapi.io.ExpenseRequest;
import com.example.restapi.io.ExpenseResponse;
import com.example.restapi.service.ExpenseService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

/**
 * This is controller class for Expense module
 * @author boroboro01
 */
@RestController
@RequiredArgsConstructor
@Slf4j
@CrossOrigin("*")
public class ExpenseController {

    private final ExpenseService expenseService;
    private final ModelMapper modelMapper;

    /**
     * It will fetch the expenses from database
     * @return list
     */
    @GetMapping("/expenses")
    public List<ExpenseResponse> getExpenses() {
        log.info("API GET /expenses called");
        // Call the service method
        List<ExpenseDTO> list = expenseService.getAllExpenses();
        log.info("Printing the data from service {}", list);
        // Convert the Expense DTO to Expense Response
        List<ExpenseResponse> response = list.stream().map(expenseDTO -> mapToExpenseResponse(expenseDTO)).collect(Collectors.toList());
        // Return the list/response
        return response;
    }

    /**
     * It will fetch the single expense from database
     * @param expenseId
     * @return ExpenseResponse
     */
    @GetMapping("/expenses/{expenseId}")
    public ExpenseResponse getExpenseByExpenseId(@PathVariable String expenseId) {
        log.info("API GET /expenses/{} called", expenseId);
        ExpenseDTO expenseDTO = expenseService.getExpenseByExpenseId(expenseId);
        log.info("Printing the expense details {}", expenseDTO);
        return mapToExpenseResponse(expenseDTO);
    }

    /**
     * It will delete the expense from database
     * @param expenseId
     * @return void
     */
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/expenses/{expenseId}")
    public void deleteExpenseById(@PathVariable String expenseId) {
        log.info("API DELETE /expenses{} called", expenseId);
        expenseService.deleteExpenseByExpenseId(expenseId);
    }


    /**
     * It will save the expense details to database
     * @param expenseRequest (expense request)
     * @return expenseResponse
     */
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/expenses")
    public ExpenseResponse saveExpenseDetails(@Valid @RequestBody ExpenseRequest expenseRequest) {
        log.info("API POST /expenses called {}", expenseRequest);
        ExpenseDTO expenseDTO = mapToExpenseDTO(expenseRequest);
        expenseDTO = expenseService.saveExpenseDetails(expenseDTO);
        log.info("Printing the expense dto {}", expenseDTO);
        return mapToExpenseResponse(expenseDTO);
    }

    /**
     * It will update the expense details to database
     * @param updateRequest (expense request)
     * @param expenseId (expense id)
     * @return expenseResponse
     */
    @PutMapping("/expenses/{expenseId}")
    public ExpenseResponse updateExpenseDetails(@RequestBody ExpenseRequest updateRequest, @PathVariable String expenseId) {
        log.info("API PUT /expenses/{} request body {}", expenseId, updateRequest);
        ExpenseDTO updateExpenseDTO = mapToExpenseDTO(updateRequest);
        expenseService.updateExpenseDetails(updateExpenseDTO, expenseId);
        log.info("Printing the updated expense DTO details {}", updateExpenseDTO);
        return mapToExpenseResponse(updateExpenseDTO);
    }

    /**
     * Mapper method for converting ExpenseRequest object to ExpenseDTO
     * @param expenseRequest (expense request)
     * @return expenseDTO
     */
    private ExpenseDTO mapToExpenseDTO(ExpenseRequest expenseRequest) {
        return modelMapper.map(expenseRequest, ExpenseDTO.class);
    }

    /**
     * Mapper method for converting ExpenseDTO object to ExpenseResponse
     * @param expenseDTO (expense data transfer object)
     * @return ExpenseResponse
     */
    private ExpenseResponse mapToExpenseResponse(ExpenseDTO expenseDTO) {
        return modelMapper.map(expenseDTO, ExpenseResponse.class);
    }
}
