package com.group.approval.dto;

import java.util.Date;

import com.group.employee.dto.Employee;

public class Reference {

	private String documentNo;
	private Employee employee;
	private ApprovalStatus approvalStatus;
	
	
	public Reference() {
		super();
		// TODO Auto-generated constructor stub
	}


	public Reference(String documentNo, Employee employee, ApprovalStatus approvalStatus) {
		super();
		this.documentNo = documentNo;
		this.employee = employee;
		this.approvalStatus = approvalStatus;
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


	public void setEmployee(Employee employee) {
		this.employee = employee;
	}


	public ApprovalStatus getApprovalStatus() {
		return approvalStatus;
	}


	public void setApprovalStatus(ApprovalStatus approvalStatus) {
		this.approvalStatus = approvalStatus;
	}


	@Override
	public String toString() {
		return "Reference [document=" + documentNo + ", employee=" + employee + ", approvalStatus=" + approvalStatus
				+ "]";
	}
	
	
}
