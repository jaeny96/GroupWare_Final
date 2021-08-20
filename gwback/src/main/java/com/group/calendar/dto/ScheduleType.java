package com.group.calendar.dto;



public class ScheduleType {
	   public String skdType;

	public ScheduleType(String skdType) {
		super();
		this.skdType = skdType;
	}

	

	public ScheduleType() {
		super();
		// TODO Auto-generated constructor stub
	}

	public String getSkdType() {
		return skdType;
	}

	public void setSkdType(String skdType) {
		this.skdType = skdType;
	}
	@Override
	   public String toString() {
	      return skdType;
	   }
	   
}