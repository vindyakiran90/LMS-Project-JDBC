package com.tyss.lmsjdbc.dto;

import java.io.Serializable;
import java.util.Date;

public class IssueBook implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -8039576523218094099L;
	
	private int issueId;
	private int bookId;
	private int userId;
	private Date issueDate;
	private Date returnDate;
	
	public int getIssueId() {
		return issueId;
	}

	public void setIssueId(int issueId) {
		this.issueId = issueId;
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

	public Date getIssueDate() {
		return issueDate;
	}

	public void setIssueDate(Date issueDate) {
		this.issueDate = issueDate;
	}

	public Date getReturnDate() {
		return returnDate;
	}

	public void setReturnDate(Date returnDate) {
		this.returnDate = returnDate;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	@Override
	public String toString() {
		return String.format("%-6s %-5s %-10s %-15s", userId, bookId, issueDate, returnDate);
	}
}
