package com.karansproject.onlinebankingsystem.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.karansproject.onlinebankingsystem.databaseconnection.DatabaseConnection;
import com.karansproject.onlinebankingsystem.entity.Accountant;
import com.karansproject.onlinebankingsystem.entity.Customer;
import com.karansproject.onlinebankingsystem.exception.AccountantException;
import com.karansproject.onlinebankingsystem.exception.CustomerException;

public class AccountantDaoImplementation implements AccountantDao {

	@Override
	public Accountant LoginAccountant(String accountantUsername, String accountantPassword) throws AccountantException {
		Accountant acc = null;

		try {
			Connection conn = DatabaseConnection.provideConnection();
			PreparedStatement ps = conn.prepareStatement(
					"select * from accountant where accountantUsername = ? and accountantPassword = ?");

			ps.setString(1, accountantUsername);
			ps.setString(2, accountantPassword);

			ResultSet rs = ps.executeQuery();

			if (rs.next()) {
				String n = rs.getString("accountantUsername");
				String e = rs.getString("accountantEmail");
				String p = rs.getString("accountantPassword");

				acc = new Accountant(n, e, p);
			}

		} catch (SQLException e) {
			throw new AccountantException("Invalid username and password");
		}

		return acc;
	}

	@Override
	public int addCustomer(String customerName, String customerMail, String customerPassword, String customerMobile,
			String customerAddress) throws CustomerException {
		int cid = -1;
		try {
			Connection conn = DatabaseConnection.provideConnection();
			PreparedStatement ps = conn.prepareStatement(
					"insert into customerinformation (customerName, customerMail, customerPassword, customerMobile, customerAddress) values(?, ?, ?, ?, ?)");

			ps.setString(1, customerName);
			ps.setString(2, customerMail);
			ps.setString(3, customerPassword);
			ps.setString(4, customerMobile);
			ps.setString(5, customerAddress);

			int x = ps.executeUpdate();

			if (x > 0) {

				PreparedStatement ps2 = conn.prepareStatement(
						"select cid from customerinformation where customerMail = ? and customerMobile = ?");
				ps2.setString(1, customerMail);
				ps2.setString(2, customerMobile);

				ResultSet rs = ps2.executeQuery();

				if (rs.next()) {
					cid = rs.getInt("cid");
				} else {
					System.out.println("Inserted Data is Incorrect Please Try again");
				}

			} else {
				System.out.println("Customer not added successfully");
			}

		} catch (Exception e) {
			System.out.println("sql query related error");
		}
		return cid;
	}

	@Override
	public String addAccount(int customerBalance, int cid) throws CustomerException {

		String message = null;
		try {
			Connection conn = DatabaseConnection.provideConnection();
			PreparedStatement ps = conn.prepareStatement("insert into account(customerBalance, cid) values(?, ?)");

			ps.setInt(1, customerBalance);
			ps.setInt(2, cid);

			int x = ps.executeUpdate();

			if (x > 0) {
				System.out.println("Account added successfully");
			} else {
				System.out.println("Inserted not added successfully");
			}

		} catch (SQLException e) {
			System.out.println("SQL related exception");
		}

		return message;
	}

	@Override
	public String updateCustomer(int customerAccountNumber, String customerAddress) throws CustomerException {

		String message = null;

		try {
			Connection conn = DatabaseConnection.provideConnection();
			PreparedStatement ps = conn.prepareStatement(
					"update customerinformation i inner join account a on i.cid = a.cid and a.customerAccountNumber = ? set i.customerAddress = ?");

			ps.setInt(1, customerAccountNumber);
			ps.setString(2, customerAddress);

			int x = ps.executeUpdate();

			if (x > 0) {
				System.out.println("Address updated successfully....");
			} else {
				System.out.println("Customer updation is not successfull....");
				System.out.println("----------------------------------------");
			}

		} catch (SQLException e) {
			message = e.getMessage();
		}

		return message;
	}

	@Override
	public String deleteAccount(int customerAccountNumber) throws CustomerException {

		String message = null;
		try {
			Connection conn = DatabaseConnection.provideConnection();
			PreparedStatement ps = conn.prepareStatement(
					"delete i from customerinformation i inner join account a on i.cid = a.cid where a.customerAccountNumber = ?");

			ps.setInt(1, customerAccountNumber);

			int x = ps.executeUpdate();

			if (x > 0) {
				System.out.println("Account deleted successfully....");
			} else {
				System.out.println("Deletion is failed .......... Account not found");
				System.out.println("----------------------------------------");
			}

		} catch (SQLException e) {
			e.printStackTrace();
			message = e.getMessage();
		}

		return message;
	}

	@Override
	public Customer displayData(int customerAccountNumber) throws CustomerException {
		Customer cu = null;

		try {
			Connection conn = DatabaseConnection.provideConnection();
			PreparedStatement ps = conn.prepareStatement(
					"select * from customerinformation i inner join account a on i.cid = a.cid where a.customerAccountNumber = ?");

			ps.setInt(1, customerAccountNumber);

			ResultSet rs = ps.executeQuery();

			if (rs.next()) {
				String n = rs.getString("customerName");
				int b = rs.getInt("customerBalance");
				String m = rs.getString("customerMail");
				String p = rs.getString("customerPassword");
				String mob = rs.getString("customerMobile");
				String a = rs.getString("customerAddress");

				cu = new Customer(customerAccountNumber, n, b, p, m, mob, a);

			} else {
				throw new CustomerException("Invalid Account Number...");
			}

		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}

		return cu;
	}

	@Override
	public Customer viewAllCustomer() throws CustomerException {

		Customer cu = null;

		try {
			Connection conn = DatabaseConnection.provideConnection();

			PreparedStatement ps = conn
					.prepareStatement("select * from customerinformation i inner join account a on i.cid = a.cid");

			ResultSet rs = ps.executeQuery();

			while (rs.next()) {
				int a = rs.getInt("customerAccountNumber");
				String n = rs.getString("customerName");
				int b = rs.getInt("customerBalance");
				String m = rs.getString("customerMail");
				String p = rs.getString("customerPassword");
				String mob = rs.getString("customerMobile");
				String ad = rs.getString("customerAddress");

				System.out.println("*************************");
				System.out.println("Account Number : " + a);
				System.out.println("Name : " + n);
				System.out.println("Balance : " + b);
				System.out.println("Email : " + m);
				System.out.println("Mobile : " + mob);
				System.out.println("Password : " + p);
				System.out.println("Addresss : " + ad);
				System.out.println("-------------------------");

				cu = new Customer(a, n, b, p, m, mob, ad);

			}

		} catch (SQLException e) {
			throw new CustomerException("Invalid Account Number !!!");
		}

		return cu;
	}

}
