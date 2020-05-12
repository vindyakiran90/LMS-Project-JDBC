package com.tyss.lmsjdbc.dto;

import java.io.Serializable;

public class BookBean implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -2364086599925512608L;
	
	private int bookId;
	private String bookTitle;
	private String category;
	private String author;
	private String publisherName;
	private int numberOfBooks;
	private int numberOfAvailableBooks;
	private int numberOfIssuedBooks;
	
	public int getBookId() {
		return bookId;
	}
	

	public void setBookId(int bookId) {
		this.bookId = bookId;
	}


	public String getBookTitle() {
		return bookTitle;
	}


	public void setBookTitle(String bookTitle) {
		this.bookTitle = bookTitle;
	}


	public String getCategory() {
		return category;
	}


	public void setCategory(String category) {
		this.category = category;
	}


	public String getAuthor() {
		return author;
	}


	public void setAuthor(String author) {
		this.author = author;
	}


	public String getPublisherName() {
		return publisherName;
	}


	public void setPublisherName(String publisherName) {
		this.publisherName = publisherName;
	}


	public int getNumberOfBooks() {
		return numberOfBooks;
	}


	public void setNumberOfBooks(int numberOfBooks) {
		this.numberOfBooks = numberOfBooks;
	}


	public int getNumberOfAvailableBooks() {
		return numberOfAvailableBooks;
	}


	public void setNumberOfAvailableBooks(int numberOfAvailableBooks) {
		this.numberOfAvailableBooks = numberOfAvailableBooks;
	}


	public int getNumberOfIssuedBooks() {
		return numberOfIssuedBooks;
	}


	public void setNumberOfIssuedBooks(int numberOfIssuedBooks) {
		this.numberOfIssuedBooks = numberOfIssuedBooks;
	}


	public static long getSerialversionuid() {
		return serialVersionUID;
	}


	@Override
	public String toString() {
		return String.format("%-10s %-15s %-15s %-15s %-15s %-20s %-20s %-20s", bookId, bookTitle, 
				category, author, publisherName, numberOfBooks, numberOfAvailableBooks, numberOfIssuedBooks);
	}
}
