package com.leo.account.wallet.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

@RestController
public class TransactionController {

	Logger LOG = LoggerFactory.getLogger(TransactionController.class);

	@Autowired
	TransactionDelegate transactionDelegate;
	
	@PostMapping(value = "/saveTransaction", produces = (MediaType.APPLICATION_JSON_VALUE))
	public @ResponseBody ResponseEntity<GenericResponse> saveTeam(@RequestBody TransactionDto transactionDto) {
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
	
	
	@GetMapping(value = "/getBalance", produces = (MediaType.APPLICATION_JSON_VALUE))
	public @ResponseBody ResponseEntity<BalanceEnquiryResponse> getBalance(
			@RequestParam(required = true) Long playerId) {
		
		BalanceEnquiryResponse response = null;
		try {
			response = transactionDelegate.getBalance(playerId);
		} catch (BusinessException e) {
			LOG.error("Exception while executing the sonar report ", e);
			response = new BalanceEnquiryResponse();
			response.setSuccess(Boolean.FALSE);
			response.setMessage(e.getMessage());
		}
		HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.setContentType(MediaType.APPLICATION_JSON);
		return new ResponseEntity<>(response, httpHeaders, HttpStatus.OK);
	}
	
	@GetMapping(value = "/transactionHistory", produces = (MediaType.APPLICATION_JSON_VALUE))
	public @ResponseBody ResponseEntity<TransactionHistoryResponse> getTransactionHistory(
			@RequestParam(required = true) Long playerId) {
		
		TransactionHistoryResponse response = null;
		try {
			response = transactionDelegate.getPlayerTransactions(playerId);
		} catch (BusinessException e) {
			LOG.error("Exception while executing the sonar report ", e);
			response = new TransactionHistoryResponse();
			response.setSuccess(Boolean.FALSE);
			response.setMessage(e.getMessage());
		}
		HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.setContentType(MediaType.APPLICATION_JSON);
		return new ResponseEntity<>(response, httpHeaders, HttpStatus.OK);
	}

}
