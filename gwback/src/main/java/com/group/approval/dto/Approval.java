package com.group.approval.dto;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.group.employee.dto.Employee;

public class Approval {

	private String documentNo;
	private int apStep;
	@JsonFormat(pattern = "yy-MM-dd", timezone = "Asia/Seoul")
	private Date apDate;
	private String apComment;
	private Employee employee;

	private Employee employeeAp;
	private ApprovalStatus apStatus;//aptype
	
	
	public Approval() {
		super();
	}

	 
	public Approval(String documentNo, int apStep, Employee employee, ApprovalStatus apStatus) {
		super();
		this.documentNo = documentNo;
		this.apStep = apStep;
		this.employee = employee;
		this.apStatus = apStatus;
	}
	
	public Approval(String documentNo, Employee employee, ApprovalStatus apStatus, int apStep, Date apDate,
			String apComment) {
		super();
		this.documentNo = documentNo;
		this.employee = employee;
		this.apStatus = apStatus;
		this.apStep = apStep;
		this.apDate = apDate;
		this.apComment = apComment;
	}


	public Employee getEmployeeAp() {
		return employeeAp;
	}

	public void setEmployeeAp(Employee employeeAp) {
		this.employeeAp = employeeAp;
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


	public ApprovalStatus getApStatus() {
		return apStatus;
	}


	public void setApStatus(ApprovalStatus apStatus) {
		this.apStatus = apStatus;
	}


	public int getApStep() {
		return apStep;
	}


	public void setApStep(int apStep) {
		this.apStep = apStep;
	}


	public Date getApDate() {
		return apDate;
	}


	public void setApDate(Date apDate) {
		this.apDate = apDate;
	}


	public String getApComment() {
		return apComment;
	}


	public void setApComment(String apComment) {
		this.apComment = apComment;
	}


	@Override
	public String toString() {
		return "Approval [documentNo=" + documentNo + ", employee=" + employee + ", apStatus=" + apStatus + ", apStep="
				+ apStep + ", apDate=" + apDate + ", apComment=" + apComment + "]";
	}
	
	
	
}
