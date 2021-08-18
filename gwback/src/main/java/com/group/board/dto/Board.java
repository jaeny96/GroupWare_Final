package com.group.board.dto;

import java.sql.Timestamp;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.group.employee.dto.Employee;

public class Board {
	public String bdNo;

	// 게시판은 작성자를 가질 수 있다
	private Employee writer;
	private String bdTitle;
	private String bdContent;
	@JsonFormat(pattern = "yy/MM/dd", timezone = "Asia/Seoul")
	private Timestamp bdDate;

	// 게시판은 여러개의 댓글을 가질 수 있다
	public List<BoardComment> bdComments;

	public Board() {
	}

	public Board(String bdNo, Employee writer) {
		this(bdNo, writer, null, null, null, null);
	}

	public Board(String bdNo, Employee writer, String bdTitle, Timestamp bdDate) {
		this(bdNo, writer, bdTitle, null, bdDate, null);
	}

	public Board(String bdNo, Employee writer, String bdTitle, String bdContent, Timestamp bdDate,
			List<BoardComment> bdComments) {
		super();
		this.bdNo = bdNo;
		this.writer = writer;
		this.bdTitle = bdTitle;
		this.bdContent = bdContent;
		this.bdDate = bdDate;
		this.bdComments = bdComments;
	}

	public String getBdNo() {
		return bdNo;
	}

	public void setBdNo(String bdNo) {
		this.bdNo = bdNo;
	}

	public Employee getWriter() {
		return writer;
	}

	public void setWriter(Employee writer) {
		this.writer = writer;
	}

	public String getBdTitle() {
		return bdTitle;
	}

	public void setBdTitle(String bdTitle) {
		this.bdTitle = bdTitle;
	}

	public String getBdContent() {
		return bdContent;
	}

	public void setBdContent(String bdContent) {
		this.bdContent = bdContent;
	}

	public Timestamp getBdDate() {
		return bdDate;
	}

	public void setBdDate(Timestamp bdDate) {
		this.bdDate = bdDate;
	}

	public List<BoardComment> getBdComments() {
		return bdComments;
	}

	public void setBdComments(List<BoardComment> bdComments) {
		this.bdComments = bdComments;
	}

	@Override
	public String toString() {
		return "Board [bdNo=" + bdNo + ", writer=" + writer + ", bdTitle=" + bdTitle + ", bdContent=" + bdContent
				+ ", bdDate=" + bdDate + ", bdComments=" + bdComments + "]";
	}

}
