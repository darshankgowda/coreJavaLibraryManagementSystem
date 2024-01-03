package com.project.libSytem;
import java.io.IOException;
import java.util.*;

import com.project.exceptions.AccessDeniedException;


public class MainSystemRunner extends LoginUI{

	public static void main(String[] args) throws AccessDeniedException, IOException {
		
		int userChoice;
		boolean condition = true;
		Scanner scanner = new Scanner(System.in);
		System.out.println("Welcome to SJCIT Library Department");
		while(condition) {
			System.out.println("Select the Login Type\n1.Department Login\t\t2.Student Login");
			userChoice = scanner.nextInt();
			switch(userChoice) {
			case 1 -> {
			departmentLogin();
			condition = false;
			break;
			}
			case 2 -> {
			studentLogin();
			condition = false;
			break;
			}
			default -> {
			System.out.println("Invalid Choice!!!");
			condition = true;
			break;
			}
			}
		}		
	}
}
