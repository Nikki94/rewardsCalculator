package com.rewards.service;

import java.io.FileNotFoundException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import com.rewards.beans.Transaction;



public class TransactionService {
	
	CSVReaderService datareader = new CSVReaderService();
	
	public List<Transaction> getTransactionData(int... args) throws ParseException, NumberFormatException, FileNotFoundException{
	   List<Transaction> listData = new ArrayList<>();
	   datareader.setCsvFile("/transaction-data.csv");
	   datareader.setSeperator(",");
	   for(String[] data : datareader.getDataAsArray(args)) {
		   Transaction transaction = new Transaction();
		   transaction.setTransactionId(Integer.parseInt(data[0]));
		   transaction.setCustId(Integer.parseInt(data[1]));
		   transaction.setAmount(Integer.parseInt(data[2]));		 
		   transaction.setDate(new SimpleDateFormat("yyyy-MM-dd").parse(data[3].trim()));
		   listData.add(transaction);
	   }	   
	   return listData;	  
	}

}
