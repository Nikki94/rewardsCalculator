package com.rewards.service;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;



public class CSVReaderService {
	
	private String csvFile;
	private String seperator;
	private int batchSize;
	private int currentBatchNumber;
	private boolean enableBatchProcessing;
	
	/**
	 * Reads the file Data as list of records.
	 * @param csvFile
	 * @param seperator
	 * @return
	 * @throws FileNotFoundException 
	 */
	public List<String[]> getDataAsArray(int... args) throws FileNotFoundException {
		
		final List<String[]> data = new ArrayList<>();
		if(args.length > 0) {
			this.setCurrentBatchNumber(args[0]);
			this.setBatchSize(args[1]);
			this.setEnableBatchProcessing(true);
		}
		this.fileReader(text -> data.add(((String) text).split(seperator)));		
		return data;

	}
	
	public int getRecordCount(String csvFile) {
		
		final List<Integer> countList = new ArrayList<>();
		countList.add(0);
		this.setCsvFile(csvFile);
		this.setEnableBatchProcessing(false);
		this.fileReader(text -> countList.add(0, countList.get(0)+1));
		return countList.get(0);
	}
	
	private void fileReader(Consumer<Object> consumer) {
		
		String line;		
		try (InputStream is = getClass().getResourceAsStream(csvFile)) {
			  if (null == is) {
			    throw new FileNotFoundException(csvFile);
			  }
			  try (BufferedReader br = new BufferedReader(new InputStreamReader(is, StandardCharsets.UTF_8))) {
				  for(int i=1;(line = br.readLine()) != null;i++) {	
					  if(isEnableBatchProcessing()) {
						  if(i > getTargetRecordCount()) break;			
						  if(getSkipRecordCount() >= i) continue;
					  }					
					  consumer.accept(line);					  
					  
				  }
			  }
			
		} catch (IOException e) {			
			e.printStackTrace();
		}
	}
	

	/**
	 * @return the csvFile
	 */
	public String getCsvFile() {
		return csvFile;
	}

	/**
	 * @param csvFile the csvFile to set
	 */
	public void setCsvFile(String csvFile) {
		this.csvFile = csvFile;
	}

	/**
	 * @return the seperator
	 */
	public String getSeperator() {
		return seperator;
	}

	/**
	 * @param seperator the seperator to set
	 */
	public void setSeperator(String seperator) {
		this.seperator = seperator;
	}

	

	/**
	 * @return the currentBatchNumber
	 */
	private int getCurrentBatchNumber() {
		return currentBatchNumber;
	}

	/**
	 * @param currentBatchNumber the currentBatchNumber to set
	 */
	private void setCurrentBatchNumber(int currentBatchNumber) {
		this.currentBatchNumber = currentBatchNumber;
	}

	/**
	 * @return the batchSize
	 */
	private int getBatchSize() {
		return batchSize;
	}

	/**
	 * @param batchSize the batchSize to set
	 */
	private void setBatchSize(int batchSize) {
		this.batchSize = batchSize;
	}
	
	private int getSkipRecordCount() {		
		return ((this.currentBatchNumber - 1) * this.batchSize);
	}
	
	
	private int getTargetRecordCount() {		
		return (this.currentBatchNumber * this.batchSize);
	}

	/**
	 * @return the enableBatchProcessing
	 */
	public boolean isEnableBatchProcessing() {
		return enableBatchProcessing;
	}

	/**
	 * @param enableBatchProcessing the enableBatchProcessing to set
	 */
	public void setEnableBatchProcessing(boolean enableBatchProcessing) {
		this.enableBatchProcessing = enableBatchProcessing;
	}
	
	
	
	
	
	 
}
