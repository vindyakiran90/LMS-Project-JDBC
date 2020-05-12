package com.tyss.lmsjdbc.dto;

import java.io.Serializable;

public class RequestBook implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -7852690686684404531L;
	
	private int requestId;
	private int bookId;
	private int userId;
	private String firstName;
	private String lastName;
	private String bookTitle;

	public int getRequestId() {
		return requestId;
	}

	public void setRequestId(int requestId) {
		this.requestId = requestId;
	}

	public int getBookId() {
		return bookId;
	}

	public void setBookId(int bookId) {
		this.bookId = bookId;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getBookTitle() {
		return bookTitle;
	}

	public void setBookTitle(String bookTitle) {
		this.bookTitle = bookTitle;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	@Override
	public String toString() {
		return String.format("%-10s %-10s %-10s %-15s %-15s %-10s", requestId, userId, bookId, firstName, 
				lastName, bookTitle);
	}
}
