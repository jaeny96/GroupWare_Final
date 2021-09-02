package com.admin.empmanage.dao;

import java.util.List;

import com.group.employee.dto.Department;
import com.group.employee.dto.Employee;
import com.group.employee.dto.Job;
import com.group.employee.dto.Position;
import com.group.exception.AddException;
import com.group.exception.FindException;
import com.group.exception.RemoveException;
import com.group.exception.UpdateException;

public interface EmployeeManageDAO {
	
	/**
	 * 직원 추가
	 * @throws AddException
	 */
	public void addEmployee(Employee emp) throws AddException;

	/**
	 * 직원 삭제
	 * @throws RemoveException
	 */
	public void deleteEmployee(Employee emp) throws RemoveException;
	
	/**
	 * 직원 수정
	 * @throws UpdateException
	 */
	public void updateEmployee(Employee emp) throws UpdateException;
	
	/**
	 * 포지션 리스트
	 * @param po
	 * @throws FindException
	 */
	public List<Position> findPosition() throws FindException;  
	
	/**
	 * job 리스트
	 * @return
	 * @throws FindException
	 */
	public List<Job> findJob() throws FindException;  

	/**
	 * Department 리스트 
	 * @return
	 * @throws FindException
	 */
	public List<Department> findDepartment() throws FindException;  


}
