package com.group.calendar.dto;

import java.sql.Timestamp;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.group.employee.dto.Employee;

public class Schedule {

	public int skdNo;
	public Employee skdId;
	public ScheduleType skdType;
	public String skdTitle;
	public String skdContent;
	@JsonFormat(pattern = "yyyy-MM-dd hh:mm", timezone = "Asia/Seoul")
	public Timestamp skdDate;
	@JsonFormat(pattern = "yyyy-MM-dd hh:mm", timezone = "Asia/Seoul")
	public Timestamp skdStartDate;
	@JsonFormat(pattern = "yyyy-MM-dd hh:mm", timezone = "Asia/Seoul")
	public Timestamp skdEndDate;
	public String skdShare;

	public Schedule() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Schedule(Employee skdId, String skdTitle, String skdContent) {
		super();
		this.skdId = skdId;
		this.skdTitle = skdTitle;
		this.skdContent = skdContent;
	}

	public Schedule(Employee skdId, Timestamp skdStartDate) {
		super();
		this.skdId = skdId;
		this.skdStartDate = skdStartDate;
	}

	public Schedule(int skdNo, Employee skdId, ScheduleType skdType, String skdTitle, String skdContent,
			Timestamp skdDate, Timestamp skdStartDate, Timestamp skdEndDate, String skdShare) {
		super();
		this.skdNo = skdNo;
		this.skdId = skdId;
		this.skdType = skdType;
		this.skdTitle = skdTitle;
		this.skdContent = skdContent;
		this.skdDate = skdDate;
		this.skdStartDate = skdStartDate;
		this.skdEndDate = skdEndDate;
		this.skdShare = skdShare;

	}

	public int getSkdNo() {
		return skdNo;
	}

	public void setSkdNo(int skdNo) {
		this.skdNo = skdNo;
	}

	public Employee getSkdId() {
		return skdId;
	}

	public void setSkdId(Employee skdId) {
		this.skdId = skdId;
	}

	public ScheduleType getSkdType() {
		return skdType;
	}

	public void setSkdType(ScheduleType skdType) {
		this.skdType = skdType;
	}

	public String getSkdTitle() {
		return skdTitle;
	}

	public void setSkdTitle(String skdTitle) {
		this.skdTitle = skdTitle;
	}

	public String getSkdContent() {
		return skdContent;
	}

	public void setSkdContent(String skdContent) {
		this.skdContent = skdContent;
	}

	public Timestamp getSkdDate() {
		return skdDate;
	}

	public void setSkdDate(Timestamp skdDate) {
		this.skdDate = skdDate;
	}

	public Timestamp getSkdStartDate() {
		return skdStartDate;
	}

	public void setSkdStartDate(Timestamp skdStartDate) {
		this.skdStartDate = skdStartDate;
	}

	public Timestamp getSkdEndDate() {
		return skdEndDate;
	}

	public void setSkdEndDate(Timestamp skdEndDate) {
		this.skdEndDate = skdEndDate;
	}

	public String getSkdShare() {
		return skdShare;
	}

	public void setSkdShare(String skdShare) {
		this.skdShare = skdShare;
	}

	@Override
	public String toString() {
		return "Schedule [skdNo=" + skdNo + ", skdId=" + skdId + ", skdType=" + skdType + ", skdTitle="
				+ skdTitle + ", skdContent=" + skdContent + ", skdDate=" + skdDate + ", skdStartDate="
				+ skdStartDate + ", skdEndDate=" + skdEndDate + ", skdShare=" + skdShare + "]";
	}

}