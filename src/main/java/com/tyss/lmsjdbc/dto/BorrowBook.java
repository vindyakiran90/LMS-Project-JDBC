package com.tyss.lmsjdbc.dto;

import java.io.Serializable;
import java.sql.Date;

public class BorrowBook implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -5905964161779021200L;
	
	private int borrowId;
	private int bookId;
	private int userId;
	private int noOfBooksBorrowed;
	private Date dateOfBorrowed;
	private Date dateOfReturn;
	private double fees;
	
	public int getBorrowId() {
		return borrowId;
	}

	public void setBorrowId(int borrowId) {
		this.borrowId = borrowId;
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

	public int getNoOfBooksBorrowed() {
		return noOfBooksBorrowed;
	}

	public void setNoOfBooksBorrowed(int noOfBooksBorrowed) {
		this.noOfBooksBorrowed = noOfBooksBorrowed;
	}

	public Date getDateOfBorrowed() {
		return dateOfBorrowed;
	}

	public void setDateOfBorrowed(Date dateOfBorrowed) {
		this.dateOfBorrowed = dateOfBorrowed;
	}

	public Date getDateOfReturn() {
		return dateOfReturn;
	}

	public void setDateOfReturn(Date dateOfReturn) {
		this.dateOfReturn = dateOfReturn;
	}

	public double getFees() {
		return fees;
	}

	public void setFees(double fees) {
		this.fees = fees;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	@Override
	public String toString() {
		return String.format("%-10s %-10s %-10s %-20s %-20s %-15s %-5s", borrowId, userId, bookId, noOfBooksBorrowed, 
				dateOfBorrowed, dateOfReturn, fees);
	}
}
