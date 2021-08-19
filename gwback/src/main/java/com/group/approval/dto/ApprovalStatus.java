package com.group.approval.dto;

public class ApprovalStatus {

	private String apStatusType;

	
	public ApprovalStatus() {
		super();
		// TODO Auto-generated constructor stub
	}

	public ApprovalStatus(String apStatusType) {
		super();
		this.apStatusType = apStatusType;
	}

	public String getApStatusType() {
		return apStatusType;
	}

	public void setApStatusType(String apStatusType) {
		this.apStatusType = apStatusType;
	}

	@Override
	public String toString() {
		return "ApprovalStatus [apStatusType=" + apStatusType + "]";
	}
}
