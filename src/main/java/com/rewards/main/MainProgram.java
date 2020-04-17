package com.rewards.main;

import java.io.FileNotFoundException;
import java.text.ParseException;
import java.util.Scanner;

import com.rewards.service.RewardsService;

public class MainProgram {

	public static void main(String[] args) {
		
		try(Scanner scanner = new Scanner(System.in)) {
			System.out.println("Please select option :");
			System.out.println("1. Display all Customers Rewards");
			System.out.println("2. Display all Customers Rewards in batch mode.");
			System.out.println("3. Display Selected Customer Rewards");
			
			
			String option = scanner.nextLine();
			
			RewardsService rewardsService = new RewardsService();
			
			switch(option) {
			  case "1" : { rewardsService.calculateTotalRewards(); rewardsService.showTotalRewards(); } break;
			  case "2" : { 
				  System.out.println("Please enter the batch size.");
				  rewardsService.calculateTotalRewardsInBatchMode(collectInput()); 
				  rewardsService.showTotalRewards(); 
			  } 
			  break;
			  case "3" : {
				  System.out.println("Please enter the customer Id.");
				  rewardsService.calculateCustomerRewards(collectInput());
			  }
			  break;
			  default : System.out.println("Wrong Option, Exiting the program.");
			}			
			
		} catch (ParseException | NumberFormatException | FileNotFoundException e) {			
			e.printStackTrace();
		}
	}
	
	private static int collectInput() {		
		int input = 0;
		try(Scanner scanner = new Scanner(System.in)) {  input = Integer.parseInt(scanner.nextLine()); } 
		  catch (NumberFormatException e) { 
			  System.out.println("Invalid input, Exiting the program.");
			  System.exit(0);
		  }	
		return input;
		
	}

}
