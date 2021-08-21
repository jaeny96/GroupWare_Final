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
	private ApprovalStatus apStatus;//aptype
	
	public Approval() {
		super();
		// TODO Auto-generated constructor stub
	}
	public Approval(String documentNo, int apStep, Date apDate, String apComment, Employee employeeAp,
			ApprovalStatus apStatus) {
		super();
		this.documentNo = documentNo;
		this.apStep = apStep;
		this.apDate = apDate;
		this.apComment = apComment;
		this.employee = employeeAp;
		this.apStatus = apStatus;
	}
	public String getDocumentNo() {
		return documentNo;
	}
	public void setDocumentNo(String documentNo) {
		this.documentNo = documentNo;
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
	@Override
	public String toString() {
		return "Approval [documentNo=" + documentNo + ", apStep=" + apStep + ", apDate=" + apDate + ", apComment="
				+ apComment + ", employee=" + employee + ", apStatus=" + apStatus + "]";
	}
	
	
	
	
	
	
	
}
