package com.group.board.dto;

import java.sql.Timestamp;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.group.employee.dto.Employee;

public class BoardComment {
	private String bdNo;
	private int cmNo;
	private Employee cmWriter;
	@JsonFormat(pattern = "yy/MM/dd", timezone = "Asia/Seoul")
	private Timestamp cmDate;
	private String cmContent;

	public BoardComment() {
	}

	public BoardComment(String bdNo, Employee cmWriter, String cmContent) {
		this(bdNo, 0, cmWriter, null, cmContent);
	}

	public BoardComment(String bdNo, int cmNo, Employee cmWriter, Timestamp cmDate, String cmContent) {
		super();
		this.bdNo = bdNo;
		this.cmNo = cmNo;
		this.cmWriter = cmWriter;
		this.cmDate = cmDate;
		this.cmContent = cmContent;
	}

	public String getBdNo() {
		return bdNo;
	}

	public void setBdNo(String bdNo) {
		this.bdNo = bdNo;
	}

	public int getCmNo() {
		return cmNo;
	}

	public void setCmNo(int cmNo) {
		this.cmNo = cmNo;
	}

	public Employee getCmWriter() {
		return cmWriter;
	}

	public void setCmWriter(Employee cmWriter) {
		this.cmWriter = cmWriter;
	}

	public Timestamp getCmDate() {
		return cmDate;
	}

	public void setCmDate(Timestamp cmDate) {
		this.cmDate = cmDate;
	}

	public String getCmContent() {
		return cmContent;
	}

	public void setCmContent(String cmContent) {
		this.cmContent = cmContent;
	}

	@Override
	public String toString() {
		return "BoardComment [bdNo=" + bdNo + ", cmNo=" + cmNo + ", cmWriter=" + cmWriter + ", cmDate=" + cmDate
				+ ", cmContent=" + cmContent + "]";
	}

}
