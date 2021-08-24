package com.group.approval.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.group.approval.dao.DocsWriteDAO;
import com.group.approval.dto.Document;
import com.group.exception.AddException;
import com.group.exception.FindException;
import com.group.employee.dto.Department;
import com.group.employee.dto.Employee;
@Service
public class DocsWriteService {
	@Autowired
	private DocsWriteDAO dao;
	
	/**
	 * 결재문서 기안
	 * 
	 * @param d
	 * @throws AddException
	 */
	public void complete(Document d) throws AddException {
		dao.draft(d);
	}

	/**
	 * 부서 리스트 조회
	 * @return 부서 리스트
	 * @throws FindException
	 */
	public List<Department> deptList() throws FindException{
		return dao.selectDept();
	}

	/**
	 * 부서 별 사원 리스트 조회
	 * @param deptId 조회할 부서번호
	 * @return 사원 리스트
	 * @throws FindException
	 */
	public List<Employee> empList(String deptId) throws FindException{
		return dao.selectEmpByDept(deptId);
	}
	
	/**
	 * 해당 문서 타입의 문서번호의 최대값을 구한다
	 * @param document_type 문서 타입
	 * @return 문서번호의 최대값
	 * @throws FindException
	 */
	public int chkMaxNum(String document_type) throws FindException {
		return dao.chkMaxNum(document_type);
	}

}