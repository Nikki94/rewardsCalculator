package com.rewards.main;

import java.io.FileNotFoundException;
import java.text.ParseException;
import java.util.Scanner;

import com.rewards.service.RewardsService;

public class MainProgram {

	public static void main(String[] args) {
		
		try(Scanner scanner = new Scanner(System.in)) {
			System.out.println("Please select option :");
			System.out.println("0. Display all Customers Rewards");
			System.out.println("1. Display Selected Customer Rewards");
			
			
			String option = scanner.nextLine();
			
			RewardsService rewardsService = new RewardsService();
			
			switch(option) {
			  case "0" : { rewardsService.calculateTotalRewardsInBatchMode(2); rewardsService.showTotalRewards(); } break;
			  case "1" : {
				  System.out.println("Please enter the customer Id.");
				  int custId = 0;
				  try { custId = Integer.parseInt(scanner.nextLine()); } 
				  catch (NumberFormatException e) { 
					  System.out.println("Invalid Customer Id, Exiting the program.");
					  System.exit(0);
				  }				  
				  rewardsService.calculateCustomerRewards(custId);
			  }
			  break;
			  default : System.out.println("Wrong Option, Exiting the program.");
			}			
			
		} catch (ParseException | NumberFormatException | FileNotFoundException e) {			
			e.printStackTrace();
		}
	}

}
