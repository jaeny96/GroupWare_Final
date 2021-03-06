package com.group.main.dao;

import java.util.List;

import com.group.approval.dto.Document;
import com.group.board.dto.Board;
import com.group.calendar.dto.Schedule;
import com.group.employee.dto.Employee;
import com.group.employee.dto.Leave;
import com.group.exception.FindException;

public interface MainDAO {
	/**
	 * 나의 정보를 조회한다
	 * @param id 나의 아이디
	 * @return 조회한 나의 정보
	 * 로그인, 프로필정보에 사용예정
	 */
	public Employee selectById(String id) throws FindException;
	/**
	 * 관리자 정보를 조회한다
	 * @param id 나의 아이디
	 * @return 조회한 나의 정보
	 * 로그인, 프로필정보에 사용예정
	 */
	public Employee selectByIdAdmin(String id) throws FindException;
	/**
	 * 나의 휴가 정보를 조회한다
	 * @param id 나의 아이디
	 * @return 조회한 나의 휴가 정보
	 */
	public Leave selectLeave(String id) throws FindException;
	/**
	 * 기안일자가 오래된 결제 예정해야하는 문서목록 최대 5건을 조회한다
	 * @param id 나의 아이디
	 * @return 결제 예정 문서 목록
	 */
	public List<Document> selectDocument(String id) throws FindException;
	/**
	 * 최근 올라온 게시글 5건을 조회한다
	 * @return 게시글 목록
	 */
	public List<Board> selectBoard() throws FindException;
	/**
	 * 오늘 예정된 나의 일정 목록(팀+개인) 최대 5건을 조회한다
	 * @param emp 나의 정보(사번,부서번호)
	 * @return 오늘 일정 목록
	 */
	public List<Schedule> selectSchedule(Employee emp) throws FindException;

	
}
