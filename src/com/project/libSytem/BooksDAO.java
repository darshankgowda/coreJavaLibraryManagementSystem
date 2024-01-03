package com.project.libSytem;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.*;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class BooksDAO implements DeptUnit, StudentUnit{
	private Connection connection;
	private int bookId;
	private String title, author;
	Scanner scanner = new Scanner(System.in);
	
	public BooksDAO() {
	    try {
	        this.connection = DataBaseConnector.connect();
	    } catch (SQLException e) {
	        e.printStackTrace(); 
	    }
	}

	@Override
	public void addBook()  {
		
		System.out.println("Enter Book Id:");
		bookId = scanner.nextInt();
		scanner.nextLine();
		System.out.println("Enter the title of the Book:");
		title = scanner.nextLine();
		System.out.println("Enter the Author of the Book:");
		author = scanner.nextLine();
		
		String sql = "INSERT INTO booksData (id, name, author) VALUES (?, ?, ?)";

		try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
			preparedStatement.setInt(1, bookId);
            preparedStatement.setString(2, title);
            preparedStatement.setString(3, author);
            int rowsAffected = preparedStatement.executeUpdate();

            if (rowsAffected > 0) {
                System.out.println("Book added successfully.");
            } else {
                System.out.println("Failed to add the book.");
            }
		}
		catch(SQLException err) {
			err.printStackTrace();
		}
		//finally block will be use when we don't use try-with-resources to close the connection of preparedStatement
	}

	@Override
	public void removeBook() {
		System.out.println("Enter the Book Id to remove it from DataBase:\n");
		bookId = scanner.nextInt();
		
		String sql = "DELETE FROM booksData WHERE ID = ?";
		
		try(PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
			
			preparedStatement.setInt(1, bookId);
			
			int rowsAffected = preparedStatement.executeUpdate();
			
			if(rowsAffected > 0) {
				System.out.println("Book with Id "+bookId+" is removed Successfully...");
			}
			else {
				System.out.println("Failed to remove the Book!");
			}
		}
		catch(SQLException err) {
			err.printStackTrace();
		}	
	}

	@Override
	public void searchBook() {
		System.out.println("Enter the book Id to Search the Book:\n");
		bookId = scanner.nextInt();
		
		String sql = "SELECT name, author FROM booksData WHERE id = ?";
		
		try(PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
			
			preparedStatement.setInt(1, bookId);
			
			try(ResultSet resultSet = preparedStatement.executeQuery()) {
				
				if (resultSet.next()) {
					String name = resultSet.getString("name");
					String author = resultSet.getString("author");
					
					System.out.println("Book title: "+name+"\nAuthor: "+author);
				}
				else {
					System.out.println("Book with Id "+bookId+" is not found!");
				}
			}
			
		}
		catch(SQLException err) {
			err.printStackTrace();
		}
		
	}
	@Override
	public void seeRequestedBooks() throws IOException {
		Path filePath = Paths.get("./src/requestedBooksData.txt");
		List<String> requestedBooksData = Files.readAllLines(filePath);
		requestedBooksData.forEach(System.out::println);
		
	}

	@Override
	public void borrowBook() {
		boolean condition = true;
		while(condition) {
			System.out.println("Enter the Book Id to Borrow the Book:\n");
			bookId = scanner.nextInt();
			scanner.nextLine();
			String sql = "SELECT name, author FROM booksData WHERE id = ?";
			
			try(PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
				
				preparedStatement.setInt(1, bookId);
				
				try(ResultSet resultSet = preparedStatement.executeQuery()) {
					
					if (resultSet.next()) {
						String userConfirm;
						String name = resultSet.getString("name");
						String author = resultSet.getString("author");
						
						System.out.println("Book title: "+name+"\nAuthor: "+author);
						System.out.println("Press 'Y' to Borrow | Press 'N' to Search Again:");
						userConfirm = scanner.nextLine();
						if (userConfirm.equals("y")) {
							String sqlBorrow = "DELETE FROM booksData WHERE ID = ?";
							
							try(PreparedStatement psBorrow = connection.prepareStatement(sqlBorrow)) {
								
								psBorrow.setInt(1, bookId);
								
								int rowsAffected = psBorrow.executeUpdate();
								
								if(rowsAffected > 0) {
									System.out.println("You have borrowed the Book with Id "+bookId+" Successfully...");
									condition = false;
								}
								else {
									System.out.println("Failed to Borrow the Book!");
									condition = false;
								}
							}
							catch(SQLException err) {
								err.printStackTrace();
							}
						}
						else {
							condition = true;
						}
					}
					else {
						System.out.println("Book with Id "+bookId+" is not in stock!");
						condition = false;
					}
				}
				
			}
			catch(SQLException err) {
				err.printStackTrace();
			}
		}	
	}

	@Override
	public void returnBook() {
		System.out.println("Enter the Book Id that you want to return:");
		bookId = scanner.nextInt();
		scanner.nextLine();
		System.out.println("Enter the title of the Book:");
		title = scanner.nextLine();
		System.out.println("Enter the Author of the Book:");
		author = scanner.nextLine();
		
		String sql = "INSERT INTO booksData (id, name, author) VALUES (?, ?, ?)";

		try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
			preparedStatement.setInt(1, bookId);
            preparedStatement.setString(2, title);
            preparedStatement.setString(3, author);
            int rowsAffected = preparedStatement.executeUpdate();

            if (rowsAffected > 0) {
                System.out.println("Book returned successfully.");
            } else {
                System.out.println("Failed to return the book.");
            }
		}
		catch(SQLException err) {
			err.printStackTrace();
		}
		
	}

	@Override
	public void requestABook() throws IOException {
		System.out.println("Please Provide the title of the Book that you want to Request:\n");
		title = scanner.nextLine();
		System.out.println("Enter the Author name:\n");
		author = scanner.nextLine();
		Path filePath = Paths.get("./src/requestedBooksData.txt");
		List<String> bookInfo = Arrays.asList(title + " - " + author);
		Files.write(filePath, bookInfo, StandardOpenOption.APPEND);
		System.out.println("Book Requested Successfully...");	
	}

}

