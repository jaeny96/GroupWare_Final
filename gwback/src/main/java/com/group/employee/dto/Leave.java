package com.group.employee.dto;

import java.util.Date;

public class Leave {
	private String employeeId;
	private int grantDays;
	private int useDays;
	private int remainDays;
	private Date grantYear;
	
	public Leave() {
		
	}
	
	public Leave(String employeeId, int grantDays, int useDays, int remainDays, Date grantYear) {
		super();
		this.employeeId = employeeId;
		this.grantDays = grantDays;
		this.useDays = useDays;
		this.remainDays = remainDays;
		this.grantYear = grantYear;
	}

	public String getEmployeeId() {
		return employeeId;
	}

	public void setEmployeeId(String employeeId) {
		this.employeeId = employeeId;
	}

	public int getGrantDays() {
		return grantDays;
	}

	public void setGrantDays(int grantDays) {
		this.grantDays = grantDays;
	}

	public int getUseDays() {
		return useDays;
	}

	public void setUseDays(int useDays) {
		this.useDays = useDays;
	}

	public int getRemainDays() {
		return remainDays;
	}

	public void setRemainDays(int remainDays) {
		this.remainDays = remainDays;
	}

	public Date getGrantYear() {
		return grantYear;
	}

	public void setGrantYear(Date grantYear) {
		this.grantYear = grantYear;
	}

	@Override
	public String toString() {
		return "Leave [employeeId=" + employeeId + ", grantDays=" + grantDays + ", useDays=" + useDays
				+ ", remainDays=" + remainDays + ", grantYear=" + grantYear + "]";
	}
	
}