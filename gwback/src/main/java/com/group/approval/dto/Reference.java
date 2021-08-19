package com.group.approval.dto;

import java.util.Date;

import com.group.employee.dto.Employee;

public class Reference {

	private Document documentNo;
	private Employee employeeId;
	private ApprovalStatus reApType;
	
	
	public Reference() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	public Reference(Document documentNo, Employee employeeId, ApprovalStatus reApType) {
		super();
		this.documentNo = documentNo;
		this.employeeId = employeeId;
		this.reApType = reApType;
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
	public ApprovalStatus getReApType() {
		return reApType;
	}
	public void setReApType(ApprovalStatus reApType) {
		this.reApType = reApType;
	}
	
	@Override
	public String toString() {
		return "Reference [documentNo=" + documentNo + ", employeeId=" + employeeId + ", reApType=" + reApType + "]";
	}
	
}
