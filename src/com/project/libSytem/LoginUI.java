package com.project.libSytem;
import java.io.IOException;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.project.exceptions.AccessDeniedException;

public class LoginUI {
	private static BooksDAO operation = new BooksDAO();
	private static String name, password;
	private static int userChoice;
	static Scanner scanner = new Scanner(System.in);
	public static void departmentLogin() throws AccessDeniedException, IOException {	
		System.out.println("Enter the User Name:\n");
		name = scanner.nextLine();
		System.out.println("Enter the Password:\n");
		password = scanner.nextLine();
		if(name.equals("root") && password.equals("sjcit")) {
			System.out.println("Logged in successfully...");
			System.out.println("Select the type of operation:\n1.Add Book\t2.Remove Book\t3.Search Book\n4.See the Requested Books");
			userChoice = scanner.nextInt();
			switch(userChoice) {
			case 1 -> {
				operation.addBook();
				break;
			}
			case 2 -> {
				operation.removeBook();
				break;
			}
			case 3 -> {
				operation.searchBook();
				break;
			}
			case 4 -> {
				operation.seeRequestedBooks();
			}
			default -> System.out.println("Invalid Entry!!");
			}
			
		}
		else {
			throw new AccessDeniedException("Incorrect Username or Password!");
		}
	}
	public static void studentLogin() throws IOException, AccessDeniedException {
		String usn, password;
		int userChoice;
		System.out.println("Enter your USN:\n");
		usn = scanner.nextLine();
		String usnRegex = "^[1][S|s][J|j][1-9]{2}[A-Z a-z]{2}[0-9]{3}$";
		Pattern pattern = Pattern.compile(usnRegex);
		Matcher matcher = pattern.matcher(usn);
		System.out.println("Enter your password:\n");
		password = scanner.nextLine();
		if(matcher.matches()) {
			if(password.equals("sjcit")) {
				System.out.println("Logged in successfully...");
				System.out.println("Choose your option:\n1.Borrow Book\t2.Return Book\t3.Request a Book");
				userChoice = scanner.nextInt();
				switch(userChoice) {
				case 1 -> {
					operation.borrowBook();
				}
				case 2 -> {
					operation.returnBook();
				}
				case 3 -> {
					operation.requestABook();
				}
				default -> System.out.println("Invalid Choice!");
				}
			}
			else {
				throw new AccessDeniedException("Invalid Password!");
			}
			
		}
		else {
			throw new AccessDeniedException("Invalid USN!");
		}
		
	}
}
