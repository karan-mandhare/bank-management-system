package com.karansproject.onlinebankingsystem;

import java.util.Scanner;

import com.karansproject.onlinebankingsystem.dao.AccountantDao;
import com.karansproject.onlinebankingsystem.dao.AccountantDaoImplementation;
import com.karansproject.onlinebankingsystem.dao.CustomerDao;
import com.karansproject.onlinebankingsystem.dao.CustomerDaoImplementation;
import com.karansproject.onlinebankingsystem.entity.Accountant;
import com.karansproject.onlinebankingsystem.entity.Customer;
import com.karansproject.onlinebankingsystem.exception.AccountantException;
import com.karansproject.onlinebankingsystem.exception.CustomerException;

public class App {
	public static void main(String[] args) {

		Scanner sc = new Scanner(System.in);

		boolean f = true;

		while (f) {

			System.out.println("-------- WELCOME TO ONLINE BANKING SYSTEM ----------");
			System.out.println("----------------------------------------------------");
			System.out.println("1. ADMIN LOGIN PORTAL \r\n" + "2. CUSTOMER");

			System.out.println("Choose your option ");
			int choice = sc.nextInt();

			switch (choice) {
			case 1:
				System.out.println("Admin Login Credentials ----------- Accountant");
				System.out.println("Enter Username : ");
				String username = sc.next();
				System.out.println("Enter Password : ");
				String pass = sc.next();

				AccountantDao ad = new AccountantDaoImplementation();
				try {
					Accountant a = ad.LoginAccountant(username, pass);
					if (a == null) {
						System.out.println("wrong credentials");
						break;
					}

					System.out.println("Login Successfully!!!");

					System.out.println(
							"Welcome to : " + a.getAccountantUsername() + " as Admin of Online Banking System");

					boolean y = true;

					while (y) {
						System.out.println("---------------------\r\n" + "1. Add New Customer Account \r\n"
								+ "2. Update Customer Address \r\n"
								+ "3. Remove/Delete the Account by Account Number \r\n"
								+ "4. View Particular Account Details by Given Account Number \r\n"
								+ "5. View all Account/Customer List \r\n" + "6. Account Logout \r\n"

						);

						int x = sc.nextInt();

						if (x == 1) {
							System.out.println("---------New Account --------");
							System.out.println("Enter CustomerName : ");
							String a1 = sc.next();
							System.out.println("Enter Account Opening Balance");
							int bal = sc.nextInt();
							System.out.println("Enter CustomerMail : ");
							String a2 = sc.next();
							System.out.println("Enter CustomerPassword : ");
							String a3 = sc.next();
							System.out.println("Enter CustomerMobile Number : ");
							String a4 = sc.next();
							System.out.println("Enter CustomerAdress : ");
							String a5 = sc.next();

							int s1 = -1;

							try {
								s1 = ad.addCustomer(a1, a2, a3, a4, a5);

								try {
									ad.addAccount(bal, s1);
								} catch (CustomerException e) {
									e.printStackTrace();
								}

							} catch (CustomerException e) {
								System.out.println(e.getMessage());
							}
							System.out.println("--------------------");
						}

						if (x == 2) {
							System.out.println("Update Customer Address......");
							System.out.println("Enter Customer Account Number ..");
							int u = sc.nextInt();
							System.out.println("Enter New Address ....");
							String u2 = sc.next();

							try {
								String mes = ad.updateCustomer(u, u2);
							} catch (Exception e) {
								e.printStackTrace();
							}
						}

						if (x == 3) {
							System.out.println("....... Remove Account ......");
							System.out.println("Enter Customer Account Number ..");
							int u = sc.nextInt();

							String s = null;

							try {
								s = ad.deleteAccount(u);
							} catch (Exception e) {
								e.printStackTrace();
							}

							if (s != null) {
								System.out.println(s);
							}
						}

						if (x == 4) {
							System.out.println(".... Customer Information ....");
							System.out.println("Enter Customer Account Number ..");
							int u = sc.nextInt();

							try {
								Customer cus = ad.displayData(u);

								if (cus != null) {
									System.out.println("*************************");
									System.out.println("Account Number : " + cus.getCustomerAccountNumber());
									System.out.println("Name : " + cus.getCustomerName());
									System.out.println("Balance : " + cus.getCustomerBalance());
									System.out.println("Email : " + cus.getCustomerMail());
									System.out.println("Mobile : " + cus.getCustomerMobile());
									System.out.println("Password : " + cus.getCustomerPassword());
									System.out.println("Addresss : " + cus.getCustomerAddress());
									System.out.println("-------------------------");

								} else {
									System.out.println("Account does not exists .....");
								}

							} catch (CustomerException e) {
								e.printStackTrace();
							}
						}

						if (x == 5) {
							try {
								System.out.println("--------- All Customer List ---------");

								Customer cus = ad.viewAllCustomer();

							} catch (Exception e) {
								e.printStackTrace();
							}
						}

						if (x == 6) {
							System.out.println("------ Account logout Successfully -------");
							y = false;
						}
					}
					break;

				} catch (AccountantException e) {
					System.out.println(e.getMessage());
				}
				break;

			case 2:
				System.out.println("LOGIN << ----------- >> CUSTOMER");
				System.out.println("----------------------------");
				System.out.println("Enter Username : ");
				String cusName = sc.next();
				System.out.println("Enter Password : ");
				String cusPass = sc.next();
				System.out.println("Enter Account Number : ");
				int cusAcc = sc.nextInt();

				CustomerDao cd = new CustomerDaoImplementation();

				try {
					Customer cust = cd.loginCustomer(cusName, cusPass, cusAcc);
					if (cust == null) {
						System.out.println("Wrong credentials");
						break;
					}

					System.out.println("Welcome : " + cust.getCustomerName());

					boolean m = true;

					while (m) {
						System.out.println(
								"--------------------------- \r\n" + "1. View Balance \r\n" + "2. Deposit Money \r\n"
										+ "3. Withdraw Amount \r\n" + "4. Transfer Money \r\n" + "5. Account Logout");

						int x = sc.nextInt();

						if (x == 1) {
							System.out.println("-------- Balance --------");
							System.out.println("Current Account Balance ----");
							System.out.println(cd.viewBalance(cusAcc));
							System.out.println("--------------------------");
						}

						if (x == 2) {
							System.out.println("--------DEPOSITE-------");
							System.out.println("Enter Amount to Deposite.......");
							int am = sc.nextInt();

							cd.depositMoney(cusAcc, am);
							System.out.println("Balance After Deposite");

							System.out.println(cd.viewBalance(cusAcc));
							System.out.println("-----------------------------");
						}

						if (x == 3) {
							System.out.println("------ WITHDRAW --------");
							System.out.println("Enter Amount to Withdraw.......");
							int am = sc.nextInt();

							int b = cd.viewBalance(cusAcc);
							if (b < am) {
								System.out.println("You dont have sufficient balance to withdraw amount \r\n"
										+ "Your Balance " + b + "\nPlease select a valid amount");

							} else {
								cd.withdrawMoney(cusAcc, am);

								b = cd.viewBalance(cusAcc);

								System.out.println("Balance After Withdraw");

								System.out.println(b);
								System.out.println("-----------------------------");
							}

						}

						if (x == 4) {
							System.out.println("------ TRANSFER --------");
							System.out.println("Enter Amount to Transfer.......");
							int am = sc.nextInt();

							System.out.println("Enter Account Number of Receiver.....");
							int acc2 = sc.nextInt();

							int b1 = cd.viewBalance(cusAcc);
							int b2 = cd.viewBalance(acc2);

							if (b1 < am) {
								System.out.println("You dont have sufficient balance to withdraw amount \r\n"
										+ "Your Balance " + b1 + "\nPlease select a valid amount");

							} else {
								cd.transferMoney(cusAcc, acc2, am);

								b1 = cd.viewBalance(cusAcc);
								b2 = cd.viewBalance(acc2);

								System.out.println("Balance After Transfer : " + b1);
								System.out.println("Receiver Balance : " + b2);

								System.out.println("-----------------------------");
							}

						}

						if (x == 5) {
							System.out.println("Account logout successfully !!!!");
							m = false;
						}

					}
					break;

				} catch (Exception e) {
					e.printStackTrace();
				}

				break;

			default:
				System.out.println("Choose a valid option");
			}
		}

	}
}
