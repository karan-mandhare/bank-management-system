package com.karansproject.onlinebankingsystem.dao;

import com.karansproject.onlinebankingsystem.entity.Customer;
import com.karansproject.onlinebankingsystem.exception.CustomerException;

public interface CustomerDao {

	public Customer loginCustomer(String customerUsername, String CustomerPassword, int customerAccountNumber)
			throws CustomerException;

	public int viewBalance(int customerAccountNumber) throws CustomerException;
	
	public int depositMoney(int customerAccountNumber, int amount) throws CustomerException;
	
	public int withdrawMoney(int customerAccountNumber, int amount) throws CustomerException;
	
	public int transferMoney(int customerAccountNumber1, int customerAccountNumber2, int amount) throws CustomerException;
}
