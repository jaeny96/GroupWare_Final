package com.group.mypage.dao;

import com.group.employee.dto.Employee;
import com.group.exception.FindException;
import com.group.exception.ModifyException;
import com.group.mypage.dto.EmployeeLeave;

public interface EmployeeLeaveDAO {
	/**
	 * 자신의 모든 정보를 조회한다
	 * @return 자신의 모든 정보
	 * @throws SQLException 
	 */
	public EmployeeLeave selectById(String id) throws FindException;
	/**
	 * 자신의 정보를 수정한다
	 * @param emp 자신의 정보
	 */
	public void update(Employee emp) throws ModifyException;
}
