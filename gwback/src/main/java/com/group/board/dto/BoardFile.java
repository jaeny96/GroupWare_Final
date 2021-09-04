package com.group.board.dto;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

public class BoardFile {
	private Board board;
	private MultipartFile bdFiles;
	
	
	
	public Board getBoard() {
		return board;
	}
	public void setBoard(Board board) {
		this.board = board;
	}
	public MultipartFile getBdFiles() {
		return bdFiles;
	}
	public void setBdFiles(MultipartFile bdFiles) {
		this.bdFiles = bdFiles;
	}
	@Override
	public String toString() {
		return "BoardFile [board=" + board + ", bdFiles=" + bdFiles + "]";
	}
	
	
	
	
	

}
