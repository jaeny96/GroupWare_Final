package com.group.employee.dto;

public class Position {
	private int positionId;
	private String positionTitle;
	
	public Position() {
		
	}
	
	public Position(int positionId, String positionTitle) {
		super();
		this.positionId = positionId;
		this.positionTitle = positionTitle;
	}

	public int getPositionId() {
		return positionId;
	}

	public void setPositionId(int positionId) {
		this.positionId = positionId;
	}

	public String getPositionTitle() {
		return positionTitle;
	}

	public void setPositionTitle(String positionTitle) {
		this.positionTitle = positionTitle;
	}

	@Override
	public String toString() {
		return "Position [positionId=" + positionId + ", positionTitle=" + positionTitle + "]";
	}

	
	
}
