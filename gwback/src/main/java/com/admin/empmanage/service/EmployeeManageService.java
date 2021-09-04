package com.admin.empmanage.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.admin.empmanage.dao.EmployeeManageDAOOracle;
import com.group.employee.dto.Department;
import com.group.employee.dto.Employee;
import com.group.employee.dto.Job;
import com.group.employee.dto.Position;
import com.group.exception.AddException;
import com.group.exception.FindException;
import com.group.exception.UpdateException;

@Service
public class EmployeeManageService {

	@Autowired
	EmployeeManageDAOOracle dao;
	
	/**
	 * 직원 추가
	 * @param emp
	 * @throws AddException
	 */
	public void addEmp(Employee emp) throws AddException{
		dao.addEmployee(emp);
	}
	
	/**
	 * 직원 수정
	 * @param emp
	 * @throws UpdateException
	 */
	public void modifyEmp(Employee emp) throws UpdateException{
		dao.updateEmployee(emp);
	}
	
	/**
	 * 직책 보기 
	 * @return
	 * @throws FindException
	 */
	public List<Position> showPosition() throws FindException{
		return dao.findPosition();
	}
	
	/**
	 * job 리스트
	 * @return
	 * @throws FindException
	 */
	public List<String> showJob() throws FindException{
		ArrayList<String> list = new ArrayList<String>();
	
		for (int j = 0; j < dao.findJob().size(); j++) {
			String jobTitle = dao.findJob().get(j).getJobTitle();
			list.add(jobTitle);
		}
		return list;
		
		
	}

	/**
	 * Department 리스트 
	 * @return
	 * @throws FindException
	 */
	public List<Department> showDepartment() throws FindException{
		return dao.findDepartment();
	}


}
