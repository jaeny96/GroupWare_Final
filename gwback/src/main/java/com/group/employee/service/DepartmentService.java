package com.group.employee.service;

import java.io.FileInputStream;
import java.util.List;
import java.util.Properties;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.group.employee.dao.DepartmentDAO;
import com.group.employee.dto.Department;
import com.group.exception.FindException;
@Service
public class DepartmentService {
	@Autowired
	private DepartmentDAO dao;
	
	/**
	 * 부서목록 및 부서별 사원수를 조회한다
	 * @return 부서목록 및 부서별 사원수
	 * @throws FindException
	 */
	public List<Department> showDept() throws FindException{
		return dao.selectDepAll();
	}

}
