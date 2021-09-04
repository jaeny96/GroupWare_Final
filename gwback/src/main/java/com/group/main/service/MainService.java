package com.group.main.service;

import java.io.FileInputStream;
import java.util.List;
import java.util.Properties;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.group.approval.dto.Document;
import com.group.board.dto.Board;
import com.group.calendar.dto.Schedule;
import com.group.employee.dto.Employee;
import com.group.employee.dto.Leave;
import com.group.exception.FindException;
import com.group.main.dao.MainDAO;

@Service
public class MainService {
	@Autowired
	private MainDAO dao;

	/**
	 * 로그인한 사원의 프로필을 조회한다
	 * @param id 로그인한 사원의 아이디
	 * @return 사원의 프로필(이름과 사번)
	 * @throws FindException
	 */
	public Employee showProfile(String id) throws FindException {
		return dao.selectById(id);
	}
	
	/**
	 * 로그인한다
	 * @param id 로그인할 아이디
	 * @param pwd 로그인할 비밀번호
	 * @return 로그인한 사원의 정보(사번,비밀번호,이름)
	 * @throws FindException
	 */
	public Employee login(String id, String pwd) throws FindException {
		Employee emp = dao.selectById(id);
		if (!emp.getPassword().equals(pwd)) {
			throw new FindException("로그인에 실패하였습니다");
		}
		return emp;
	}
	/**
	 * 관리자 로그인한다
	 * @param id 관리자계정
	 * @param pwd 관리자 계정 비밀번호
	 * @return 관리자 사원의 정보(사번,비밀번호,이름)
	 * @throws FindException
	 */
	public Employee loginAdmin(String id, String pwd) throws FindException {
		Employee emp = dao.selectByIdAdmin(id);
		
		 if(!emp.getPassword().equals(pwd)) {
			throw new FindException("권한이 없습니다.");
		}
		return emp;
	}
	
	/**
	 * 기안일이 오래된 순으로 결재예정 문서 5건을 조회한다
	 * @param id 로그인한 사원의 아이디
	 * @return 기안일이 오래된 순의 결재 예쩡 문서 5건
	 * @throws FindException
	 */
	public List<Document> showDocExpected(String id) throws FindException {
		return dao.selectDocument(id);
	}

	/**
	 * 최근 올라온 게시글 순으로 게시글 5건을 조회한다
	 * @return 최근 올라온 게시글 순의 게시글 5건
	 * @throws FindException
	 */
	public List<Board> showRecentBd() throws FindException {
		return dao.selectBoard();
	}

	/**
	 * 로그인한 사원의 휴가 정보를 조회한다
	 * @param id 로그인한 사원의 아이디
	 * @return 로그인한 사원의 휴가 정보
	 * @throws FindException
	 */
	public Leave showLeave(String id) throws FindException {
		return dao.selectLeave(id);
	}

	/**
	 * 오늘의 일정 중 시작 시간 순으로 5건을 조회한다
	 * @param emp 로그인한 사원의 사번 및 부서번호를 담고 있는 객체
	 * @return 오늘의 일정 중 시작 시간 순으로 5건
	 * @throws FindException
	 */
	public List<Schedule> showTodaySkd(Employee emp) throws FindException {
		return dao.selectSchedule(emp);
	}
}
