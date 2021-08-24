package com.group.approval.dao;

import java.util.List;

import org.apache.ibatis.session.SqlSession;

import com.group.approval.dto.Agreement;
import com.group.approval.dto.Approval;
import com.group.approval.dto.Document;
import com.group.approval.dto.DocumentType;
import com.group.approval.dto.Reference;
import com.group.exception.AddException;
import com.group.exception.FindException;
import com.group.employee.dto.Department;
import com.group.employee.dto.Employee;

public interface DocsWriteDAO {
	/**
	 * 1. 해당 문서 종류의 최고 숫자를 출력한다
	 * 
	 * @param document_type 문서 종류
	 * @return 해당 문서 종류의 최고 숫자
	 * @throws FindException
	 */
	int chkMaxNum(String document_type) throws FindException;

	/**
	 * 2-1. 사용자는 작성한 문서상세를 등록한다
	 * 
	 * @param d
	 * @throws AddException
	 */
	void draftDoc(SqlSession session, Document d) throws AddException;

	/**
	 * 2-2. 문서 기안 시 결재자를 등록한다
	 * 
	 * @param d
	 * @throws AddException
	 */
	void draftAp(SqlSession session, Approval ap) throws AddException;

	/**
	 * 2-3. 문서 기안 시 합의자를 등록한다
	 * 
	 * @param d
	 * @throws AddException
	 */
	void draftAg(SqlSession session, Agreement ag) throws AddException;

	/**
	 * 2-4. 문서 기안 시 참조자를 등록한다
	 * 
	 * @param d
	 * @throws AddException
	 */
	void draftRe(SqlSession session, Reference re) throws AddException;

	/**
	 * 2. 문서 기안 트랜잭션 처리한다
	 * 
	 * @param d
	 * @throws AddException
	 */
	void draft(Document d) throws AddException;

	/**
	 * 3. 전체 부서 목록을 조회한다
	 * 
	 * @return 전체 부서 목록
	 * @throws FindException
	 */
	List<Department> selectDept() throws FindException;

	/**
	 * 4. 부서별 사원 목록을 조회한다
	 * 
	 * @param deptId 부서번호
	 * @return 사원 목록
	 * @throws FindException
	 */
	List<Employee> selectEmpByDept(String deptId) throws FindException;
}