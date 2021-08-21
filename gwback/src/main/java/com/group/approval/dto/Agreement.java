package com.group.approval.dto;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.group.employee.dto.Employee;

public class Agreement {

	private String documentNo;
	@JsonFormat(pattern = "yy-MM-dd", timezone = "Asia/Seoul")
	private Date apDate;
	private String apComment;
	private Employee employee;
	private ApprovalStatus approvalStatus;
	
	public Agreement() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	
	public Agreement(String documentNo, Employee employee, ApprovalStatus approvalStatus) {
		super();
		this.documentNo = documentNo;
		this.employee = employee;
		this.approvalStatus = approvalStatus;
	}


	public Agreement(String documentNo, Date apDate, String apComment, Employee employee,
			ApprovalStatus approvalStatus) {
		super();
		this.documentNo = documentNo;
		this.apDate = apDate;
		this.apComment = apComment;
		this.employee = employee;
		this.approvalStatus = approvalStatus;
	}

	public String getDocumentNo() {
		return documentNo;
	}

	public void setDocumentNo(String documentNo) {
		this.documentNo = documentNo;
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

	public ApprovalStatus getApprovalStatus() {
		return approvalStatus;
	}

	public void setApprovalStatus(ApprovalStatus approvalStatus) {
		this.approvalStatus = approvalStatus;
	}

	@Override
	public String toString() {
		return "Agreement [documentNo=" + documentNo + ", apDate=" + apDate + ", apComment=" + apComment + ", employee="
				+ employee + ", approvalStatus=" + approvalStatus + "]";
	}	
}
