package com.tiwbnb.api.controller;



import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
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
	
	@RequestMapping("/test")
	public @ResponseBody String home(){
		Transaction tr = daotransaction.findTop1ById(1L).orElse(null);
		if(tr != null){
			return tr.getCardNum();
		}
		return "ERROR";
		}
	
	@RequestMapping(method = RequestMethod.POST, value="/transactions/new")
	public String saveTransaction(@RequestBody @Validated TransactionRequest ptransaction) {
		//return ptransaction.getEndDate().toString();
		User user = daouser.findById(ptransaction.getInvoiced()).orElse(null);
		House house = daohouse.findById(ptransaction.getHouse()).orElse(null);
		if(user == null){
			return "WRONGUSER";
		}
		if(house == null){
			return "WRONGHOUSE";
		}
		Transaction newTransaction = new Transaction(ptransaction);
		newTransaction.setInvoiced(user);
		newTransaction.setHouse(house);
		
		try{
			daotransaction.save(newTransaction);
			return "SUCCESS";
		}
		catch (Exception e){
			return e.getMessage();
		}
		
		
	}
	
	
	@RequestMapping("/transactions/{transactionId}/accept")
	public String acceptTransaction(@PathVariable Long transactionId){
		Transaction accepted =  daotransaction.findById(transactionId).orElse(null);
		if(accepted == null){
			return "WRONGTRANSACTION";
		}
		accepted.accept();
		daotransaction.save(accepted);
		return "SUCCESS";
	}
	
	@RequestMapping("/transactions/{transactionId}/reject")
	public String rejectTransaction(@PathVariable Long transactionId){
		Transaction rejected =  daotransaction.findById(transactionId).orElse(null);
		if(rejected == null){
			return "WRONGTRANSACTION";
		}
		
		if (rejected.getStatus().equals(Transaction.PENDING)){
			
			rejected.reject();
			daotransaction.save(rejected);
			return "SUCCESS";
			
		}else{
			
			return "TRANSACTIONNOTPENDING";
					
		}
		
		
		
	}
	
	
}
