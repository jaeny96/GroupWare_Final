package com.group.employee.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.group.employee.dao.EmployeeDAO;
import com.group.employee.dto.Employee;
import com.group.exception.FindException;
@Service
public class EmployeeService {

	@Autowired
	private EmployeeDAO dao;

	/**
	 * 사원 전체를 조회한다
	 * @return 사원 전체 목록
	 * @throws FindException
	 */
	public List<Employee> showAll() throws FindException {
		return dao.selectAll();
	}

	/**
	 * 부서별 사원 목록을 조회한다
	 * @param dept 특정 부서번호
	 * @return 부서별 사원 목록
	 * @throws FindException
	 */
	public List<Employee> showByDept(String dept) throws FindException {
		return dao.selectByDep(dept);
	}

	/**
	 * 사원을 검색한다
	 * @param word 검색 단어
	 * @return 특정 검색 단어에 해당하는 사원 목록
	 * @throws FindException
	 */
	public List<Employee> searchEmp(String word) throws FindException {
		return dao.selectByWord(word);
	}

	/**
	 * 특정 사원의 상세 정보를 조회한다
	 * @param emp 특정 사원의 사번 및 이름을 담고 있는 객체
	 * @return 사원의 상세 정보
	 * @throws FindException
	 * 클릭한 객체의 이름과 사번을 검색해와야함
	 */
	public Employee showDetail(String id) throws FindException {
		return dao.selectInfo(id);
	}
}
