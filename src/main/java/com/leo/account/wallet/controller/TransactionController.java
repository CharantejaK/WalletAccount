package com.leo.account.wallet.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.leo.account.wallet.delegate.TransactionDelegate;
import com.leo.account.wallet.dto.BalanceEnquiryResponse;
import com.leo.account.wallet.dto.GenericResponse;
import com.leo.account.wallet.dto.TransactionDto;
import com.leo.account.wallet.dto.TransactionHistoryResponse;
import com.leo.account.wallet.exception.BusinessException;

/**
 * The public API of the wallet Transaction solution.
 */
@RestController
public class TransactionController {
	

	@Autowired
	TransactionDelegate transactionDelegate;
	
	  /**
     * Handling the save transaction decision for both Debit and Credit Transactions, for debit pass "debit" and for credit pass "credit" as 
     * transaction type in the request
     *
     * @param transactionDto  request with the transaction amount, code and transaction Type
     * @return the generic response
     */
	@PostMapping(value = "/saveTransaction", produces = (MediaType.APPLICATION_JSON_VALUE))
	public @ResponseBody ResponseEntity<GenericResponse> saveTransaction(@RequestBody TransactionDto transactionDto) {
		GenericResponse response = null;
		try {
			response = transactionDelegate.saveTransaction(transactionDto);
		} catch (BusinessException e) {
			response = new GenericResponse();
			response.setSuccess(Boolean.FALSE);
			response.setMessage(e.getMessage());
		}
		HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.setContentType(MediaType.APPLICATION_JSON);
		return new ResponseEntity<>(response, httpHeaders, HttpStatus.OK);
	} 
	
	  /**
     * Handling the get balance request
     *
     * @param playerId  request with the playerId for whom the balance needs to be fetched
     * @return the balance response with balance
     */
	@GetMapping(value = "/getBalance", produces = (MediaType.APPLICATION_JSON_VALUE))
	public @ResponseBody ResponseEntity<BalanceEnquiryResponse> getBalance(
			@RequestParam(required = true) Long playerId) {
		
		BalanceEnquiryResponse response = null;
		try {
			response = transactionDelegate.getBalance(playerId);
		} catch (BusinessException e) {			
			response = new BalanceEnquiryResponse();
			response.setSuccess(Boolean.FALSE);
			response.setMessage(e.getMessage());
		}
		HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.setContentType(MediaType.APPLICATION_JSON);
		return new ResponseEntity<>(response, httpHeaders, HttpStatus.OK);
	}
	
	  /**
     * Handling the get balance request
     *
     * @param playerId  request with the playerId for whom the transaction history to be fetched
     * @return the TransactionHistoryResponse response with the history of transactions 
     */
	@GetMapping(value = "/transactionHistory", produces = (MediaType.APPLICATION_JSON_VALUE))
	public @ResponseBody ResponseEntity<TransactionHistoryResponse> getTransactionHistory(
			@RequestParam(required = true) Long playerId) {
		
		TransactionHistoryResponse response = null;
		try {
			response = transactionDelegate.getPlayerTransactions(playerId);
		} catch (BusinessException e) {			
			response = new TransactionHistoryResponse();
			response.setSuccess(Boolean.FALSE);
			response.setMessage(e.getMessage());
		}
		HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.setContentType(MediaType.APPLICATION_JSON);
		return new ResponseEntity<>(response, httpHeaders, HttpStatus.OK);
	}

}
