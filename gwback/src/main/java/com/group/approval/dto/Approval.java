package com.group.approval.dto;

import java.util.Date;

import com.group.employee.dto.Employee;

public class Approval {

	private Document documentNo;
	private Employee employeeId;
	private ApprovalStatus apType;
	private int apStep;
	private Date apApDate;
	private String apApComment;
	
	
	public Approval() {
		super();
	}
	
	
	public Approval(Document documentNo, Employee employeeId, ApprovalStatus apType, int apStep, Date apApDate,
			String apApComment) {
		super();
		this.documentNo = documentNo;
		this.employeeId = employeeId;
		this.apType = apType;
		this.apStep = apStep;
		this.apApDate = apApDate;
		this.apApComment = apApComment;
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
	public ApprovalStatus getApType() {
		return apType;
	}
	public void setApType(ApprovalStatus apType) {
		this.apType = apType;
	}
	public int getApStep() {
		return apStep;
	}
	public void setApStep(int apStep) {
		this.apStep = apStep;
	}
	public Date getApApDate() {
		return apApDate;
	}
	public void setApApDate(Date apApDate) {
		this.apApDate = apApDate;
	}
	public String getApApComment() {
		return apApComment;
	}
	public void setApApComment(String apApComment) {
		this.apApComment = apApComment;
	}

	@Override
	public String toString() {
		return "Approval [documentNo=" + documentNo + ", employeeId=" + employeeId + ", apType=" + apType + ", apStep="
				+ apStep + ", apApDate=" + apApDate + ", apApComment=" + apApComment + "]";
	}
}
