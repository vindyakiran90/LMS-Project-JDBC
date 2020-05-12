package com.tyss.lmsjdbc.controller;

import java.util.InputMismatchException;
import java.util.Scanner;

public class MainController {

	public static void main(String[] args) {
		try(Scanner scanner = new Scanner(System.in)){
			while(true) {
				System.out.println("----------------------------------------------------------------------------------------"
						+ "---------------------------------------------------------------------------------------------");
				System.out.println("                                                                        Welcome to Library Management System");
				System.out.println("----------------------------------------------------------------------------------------"
						+ "---------------------------------------------------------------------------------------------");
				System.out.println("Enter your choice \n1:Student Login    2:Admin Login   3:Registeration");
				int choice = 0;
				if(scanner.hasNextInt()) {
					choice = scanner.nextInt();
				} else {
					choice = 0;
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
					System.out.println("Invalid entry");
				}
			}
		}catch(InputMismatchException e) {
			e.printStackTrace();
		}
	}
}
