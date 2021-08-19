package com.group.approval.dto;

import java.util.Date;

import com.group.employee.dto.Employee;

public class Agreement {

	private Document documentNo;
	private Employee employeeId;
	private ApprovalStatus agApType;
	private Date agApDate;
	private String agApComment;
	
	
	public Agreement() {
		super();
		// TODO Auto-generated constructor stub
	}
	public Agreement(Document documentNo, Employee employeeId, ApprovalStatus agApType, Date agApDate,
			String agApComment) {
		super();
		this.documentNo = documentNo;
		this.employeeId = employeeId;
		this.agApType = agApType;
		this.agApDate = agApDate;
		this.agApComment = agApComment;
	}
	public Document getDocumentNo() {
		return documentNo;
	}
	public void setDocumentNo(Document documentNo) {
		this.documentNo = documentNo;
	}
	public Employee getEmployeeId() {
		return employeeId;
	}
	public void setEmployeeId(Employee employeeId) {
		this.employeeId = employeeId;
	}
	public ApprovalStatus getAgApType() {
		return agApType;
	}
	public void setAgApType(ApprovalStatus agApType) {
		this.agApType = agApType;
	}
	public Date getAgApDate() {
		return agApDate;
	}
	public void setAgApDate(Date agApDate) {
		this.agApDate = agApDate;
	}
	public String getAgApComment() {
		return agApComment;
	}
	public void setAgApComment(String agApComment) {
		this.agApComment = agApComment;
	}

	@Override
	public String toString() {
		return "Agreement [documentNo=" + documentNo + ", employeeId=" + employeeId + ", agApType=" + agApType
				+ ", agApDate=" + agApDate + ", agApComment=" + agApComment + "]";
	}
	
}
