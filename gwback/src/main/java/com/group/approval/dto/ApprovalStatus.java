package com.group.approval.dto;

public class ApprovalStatus {

	private String apType;

	
	public ApprovalStatus() {
		super();
		// TODO Auto-generated constructor stub
	}

	public ApprovalStatus(String apType) {
		super();
		this.apType = apType;
	}

	

	public String getApType() {
		return apType;
	}

	public void setApType(String apType) {
		this.apType = apType;
	}

	@Override
	public String toString() {
		return "ApprovalStatus [apStatusType=" + apType + "]";
	}
}