package com.karansproject.onlinebankingsystem.dao;

import com.karansproject.onlinebankingsystem.entity.Accountant;
import com.karansproject.onlinebankingsystem.entity.Customer;
import com.karansproject.onlinebankingsystem.exception.AccountantException;
import com.karansproject.onlinebankingsystem.exception.CustomerException;

public interface AccountantDao {
	public Accountant LoginAccountant(String accountantUsername, String accountantPassword)
			throws AccountantException;
	
	public int addCustomer(String customerName, String customerMail, String customerPassword, 
			String customerMobile, String customerAddress) throws CustomerException;
	
	public String addAccount(int customerBalance, int cid) throws CustomerException;
	
	public String updateCustomer(int customerAccountNumber, String customerAddress) throws CustomerException;
	
	public String deleteAccount(int customerAccountNumber) throws CustomerException;
	
	public Customer displayData(int customerAccountNumber) throws CustomerException;
	
	public Customer viewAllCustomer() throws CustomerException;
	
	
}
