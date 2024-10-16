package com.karansproject.onlinebankingsystem.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.karansproject.onlinebankingsystem.databaseconnection.DatabaseConnection;
import com.karansproject.onlinebankingsystem.entity.Customer;
import com.karansproject.onlinebankingsystem.exception.CustomerException;

public class CustomerDaoImplementation implements CustomerDao {

	@Override
	public Customer loginCustomer(String customerUsername, String customerPassword, int customerAccountNumber)
			throws CustomerException {

		Customer customer = null;

		try {
			Connection conn = DatabaseConnection.provideConnection();
			PreparedStatement ps = conn.prepareCall(
					"select * from customerinformation i inner join account a on i.cid = a.cid where customerName = ? and customerPassword = ? and customerAccountNumber = ?");
			ps.setString(1, customerUsername);
			ps.setString(2, customerPassword);
			ps.setInt(3, customerAccountNumber);
			ResultSet rs = ps.executeQuery();

			if (rs.next()) {
				int balance = rs.getInt("customerBalance");
				String email = rs.getString("customerMail");
				String mobile = rs.getString("customerMobile");
				String address = rs.getString("customerAddress");

				customer = new Customer(customerAccountNumber, customerUsername, balance, customerPassword, email,
						mobile, address);

			} else {
				throw new CustomerException("Invalid Customer name and password !!!! Please Try Again !!!! ");
			}

		} catch (SQLException e) {
			throw new CustomerException(e.getMessage());
		}

		return customer;
	}

	@Override
	public int viewBalance(int customerAccountNumber) throws CustomerException {

		int b = -1;

		try {
			Connection conn = DatabaseConnection.provideConnection();
			PreparedStatement ps = conn
					.prepareStatement("select customerBalance from account where customerAccountNumber = ?");

			ps.setInt(1, customerAccountNumber);

			ResultSet rs = ps.executeQuery();

			if (rs.next()) {
				b = rs.getInt("customerBalance");
			}
		} catch (Exception e) {
			throw new CustomerException(e.getMessage());
		}

		return b;
	}

	@Override
	public int depositMoney(int customerAccountNumber, int amount) throws CustomerException {

		int b = -1;

		try {
			Connection conn = DatabaseConnection.provideConnection();
			PreparedStatement ps = conn.prepareStatement(
					"update account set customerBalance = customerBalance + ? where customerAccountNumber = ?");

			ps.setInt(1, amount);
			ps.setInt(2, customerAccountNumber);

			int x = ps.executeUpdate();			

		} catch (SQLException e) {
			throw new CustomerException(e.getMessage());
		}

		return b;
	}

	@Override
	public int withdrawMoney(int customerAccountNumber, int amount) throws CustomerException {

		int b = -1;

		try {
			Connection conn = DatabaseConnection.provideConnection();
			PreparedStatement ps = conn.prepareStatement(
					"update account set customerBalance = customerBalance - ? where customerAccountNumber = ?");
			ps.setInt(1, amount);
			ps.setInt(2, customerAccountNumber);
			
			int x = ps.executeUpdate();

		} catch (Exception e) {
			throw new CustomerException(e.getMessage());
		}

		return b;
	}

	@Override
	public int transferMoney(int customerAccountNumber1, int customerAccountNumber2, int amount)
			throws CustomerException {
		
		int b = -1;
		
		try {
			
			Connection conn = DatabaseConnection.provideConnection();
			PreparedStatement ps1 = conn.prepareStatement("update account set customerBalance = customerBalance - ? where customerAccountNumber = ?");
			ps1.setInt(1, amount);
			ps1.setInt(2, customerAccountNumber1);
			int x = ps1.executeUpdate();
			
			PreparedStatement ps2 = conn.prepareStatement("update account set customerBalance = customerBalance + ? where customerAccountNumber = ?");
			ps2.setInt(1,  amount);
			ps2.setInt(2,  customerAccountNumber2);
			int y = ps2.executeUpdate();
			
		} catch(Exception e) {
			throw new CustomerException(e.getMessage());
		}
		
		return b;
	}

}
