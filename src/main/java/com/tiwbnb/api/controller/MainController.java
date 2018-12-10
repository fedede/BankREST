package com.tiwbnb.api.controller;



import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.tiwbnb.api.domains.*;


@RestController
@CrossOrigin
public class MainController {
	
	@Autowired
	TransactionDAO daotransaction;
	@Autowired
	UserDAO daouser;
	@Autowired
	HouseDAO daohouse;
	
	
	@RequestMapping(method = RequestMethod.POST, value="/transaction/new")
	public ResponseEntity<Transaction> saveTransaction(@RequestBody @Validated TransactionRequest ptransaction) {
		User user = daouser.findById(ptransaction.getInvoiced()).orElse(null);
		House house = daohouse.findById(ptransaction.getHouse()).orElse(null);
		if(user == null){
			ResponseEntity.status(HttpStatus.METHOD_NOT_ALLOWED).body(null);
		}
		if(house == null){
			ResponseEntity.status(HttpStatus.METHOD_NOT_ALLOWED).body(null);
		}
		Transaction newTransaction = new Transaction(ptransaction);
		newTransaction.setInvoiced(user);
		newTransaction.setHouse(house);
		
		try{
			daotransaction.save(newTransaction);
			return ResponseEntity.status(HttpStatus.CREATED).body(newTransaction);
		}
		catch (Exception e){
			return ResponseEntity.status(HttpStatus.METHOD_NOT_ALLOWED).body(null);
		}
		
		
	}
	
	
	@RequestMapping("/transaction/{transactionId}/accept")
	public ResponseEntity<Transaction> acceptTransaction(@PathVariable Long transactionId){
		Transaction accepted =  daotransaction.findById(transactionId).orElse(null);
		if(accepted == null){
			return ResponseEntity.status(HttpStatus.METHOD_NOT_ALLOWED).body(null);
		}
		if (accepted.getStatus().equals(Transaction.PENDING)){
			accepted.accept();
			daotransaction.save(accepted);
			return ResponseEntity.status(HttpStatus.ACCEPTED).body(accepted);
		}else{
			
			return ResponseEntity.status(HttpStatus.NOT_MODIFIED).body(null);
		}
	}
	
	@RequestMapping("/transaction/{transactionId}/reject")
	public ResponseEntity<Transaction> rejectTransaction(@PathVariable Long transactionId){
		Transaction rejected =  daotransaction.findById(transactionId).orElse(null);
		if(rejected == null){
			return ResponseEntity.status(HttpStatus.METHOD_NOT_ALLOWED).body(null);
		}
		
		if (rejected.getStatus().equals(Transaction.PENDING)){
			
			rejected.reject();
			daotransaction.save(rejected);
			return ResponseEntity.status(HttpStatus.ACCEPTED).body(rejected);
			
		}else{
			
			return ResponseEntity.status(HttpStatus.NOT_MODIFIED).body(null);
					
		}
		
		
		
	}
	
	
}
