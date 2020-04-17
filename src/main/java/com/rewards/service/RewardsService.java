package com.rewards.service;

import java.io.FileNotFoundException;
import java.text.ParseException;
import java.util.HashMap;
import java.util.Map;

public class RewardsService {	
	
	TransactionService transactionService = new TransactionService();	
	CSVReaderService csvReaderService = new CSVReaderService();
	
	private Map<Integer,Integer> customerRewards = new HashMap<>();
	
	public void calculateTotalRewards() throws ParseException, NumberFormatException, FileNotFoundException {	

		System.out.println("Total Transactions Counted : " + this.generateCustomerRewards());
	}
	
	public void calculateTotalRewardsInBatchMode(int batchSize) throws NumberFormatException, FileNotFoundException, ParseException {
		
		int recordCount =  csvReaderService.getRecordCount("/transaction-data.csv");
		
		int noofBatches = Math.round(recordCount/batchSize);
		if(((recordCount/batchSize) == noofBatches) && (recordCount%batchSize != 0)) {
			noofBatches = noofBatches + 1;
		}
		System.out.println("Total Number of batches : " + noofBatches);
		int processedRecords = 0;
		for(int i = 1; i <= noofBatches; i++) {			
			processedRecords += this.generateCustomerRewards(i,batchSize);			
		}
		
		System.out.println("Total Transactions Counted : " + processedRecords);
		
	}
	
	private long generateCustomerRewards(int... args) throws NumberFormatException, FileNotFoundException, ParseException {
		
		long count = transactionService.getTransactionData(args).stream().map( transaction -> {
			if(customerRewards.containsKey(transaction.getCustId())) {
				int accumilatedRewards = customerRewards.get(transaction.getCustId()) + calculateRewards(transaction.getAmount());
				customerRewards.put(transaction.getCustId(), accumilatedRewards);
			}else{
				customerRewards.put(transaction.getCustId(), calculateRewards(transaction.getAmount()));
			}			
			return transaction;
		}).count();		
		return count;		
	}
	
	public void calculateCustomerRewards(int custId) throws ParseException, NumberFormatException, FileNotFoundException {

		long rewardsCount = transactionService.getTransactionData().stream()
				.filter(transaction -> transaction.getCustId() == custId)		
				.map( transaction ->  calculateRewards(transaction.getAmount())).reduce(0, Integer::sum);

		StringBuilder builder = new StringBuilder("Customer : ");
		builder.append(custId)
		.append(", ")
		.append("Rewards : ")
		.append(rewardsCount);
		System.out.println(builder.toString());

	}
	
   public void showTotalRewards() {		
	   for(Integer custId : this.customerRewards.keySet()) {
		   StringBuilder builder = new StringBuilder("Customer : ");
		   builder.append(custId).append(", ").append("Rewards : ").append(customerRewards.get(custId));
		   System.out.println(builder.toString());
	   }
   }
	
	/**
	 * Calculates the rewards as per business.
	 * @param amount
	 * @return
	 */
	private int calculateRewards(int amount) {		
		int rewards = 0;		
		amount = amount  - 50;					// No Rewards upto 50 points.
		if(amount > 0 && amount <= 50) {
			rewards = amount * 2;
		}else if(amount > 50) {		
			rewards = 50 * 2;
			amount = amount  - 50;		
			rewards += amount * 1;
		}		
		return rewards;		
	}

}
