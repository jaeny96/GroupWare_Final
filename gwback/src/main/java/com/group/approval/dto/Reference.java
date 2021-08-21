package com.group.approval.dto;

import java.util.Date;

import com.group.employee.dto.Employee;

public class Reference {

	private String documentNo;
	private Employee employee;
	private ApprovalStatus reStatus;
	
	
	public Reference() {
		super();
		// TODO Auto-generated constructor stub
	}


	public Reference(String documentNo, Employee employee, ApprovalStatus reStatus) {
		super();
		this.documentNo = documentNo;
		this.employee = employee;
		this.reStatus = reStatus;
	}


	public String getDocumentNo() {
		return documentNo;
	}


	public void setDocumentNo(String documentNo) {
		this.documentNo = documentNo;
	}


	public Employee getEmployee() {
		return employee;
	}


	public void setEmployee(Employee employeeRe) {
		this.employee = employeeRe;
	}


	public ApprovalStatus getReStatus() {
		return reStatus;
	}


	public void setReStatus(ApprovalStatus reStatus) {
		this.reStatus = reStatus;
	}


	@Override
	public String toString() {
		return "Reference [documentNo=" + documentNo + ", employee=" + employee + ", reStatus=" + reStatus + "]";
	}


	
}