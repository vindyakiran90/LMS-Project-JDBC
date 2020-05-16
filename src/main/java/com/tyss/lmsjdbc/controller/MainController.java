package com.tyss.lmsjdbc.controller;

import java.util.Scanner;

import com.tyss.lmsjdbc.exception.LMSException;

public class MainController {

	public static void main(String[] args) {
		while(true) {
			try(Scanner scanner = new Scanner(System.in)){
				System.out.println("----------------------------------------------------------------------------------------"
						+ "---------------------------------------------------------------------------------------------");
				System.out.println("                                                                        Welcome to Library Management System");
				System.out.println("----------------------------------------------------------------------------------------"
						+ "---------------------------------------------------------------------------------------------");
				System.out.println("Enter your choice \n1:Student Login    2:Admin Login   3:Registeration");
				int choice = 0;
				if(scanner.hasNext()) {
					choice = scanner.nextInt();
				} 
				switch(choice) {
				case 1:
					StudentLogin studentLogin = new StudentLogin();
					studentLogin.login();	
					break;
				case 2:
					AdminLogin adminLogin = new AdminLogin();
					adminLogin.login();
					break;
				case 3:
					Registeration registeration = new Registeration();
					registeration.register();
					break;
				default:
					System.out.println("Invalid Input");
					System.exit(0);
				}
			}catch(Exception e) {
				throw new LMSException("Invalid entry");
			}
		}
	}
}
