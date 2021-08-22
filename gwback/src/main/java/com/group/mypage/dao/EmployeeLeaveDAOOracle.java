package com.group.mypage.dao;
 
import javax.sql.DataSource;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;


import com.group.employee.dto.Employee;
import com.group.exception.FindException;
import com.group.exception.ModifyException;
import com.group.mypage.dto.EmployeeLeave;

@Repository("EmployeeLeaveDAO")
public class EmployeeLeaveDAOOracle implements EmployeeLeaveDAO {
	
	@Autowired
	private DataSource ds;
	
	@Autowired
	private SqlSessionFactory sessionFactory;
	
	public EmployeeLeave selectById(String id) throws FindException {
		SqlSession session = null;
		try {
			session = sessionFactory.openSession();	
			EmployeeLeave empLeave = session.selectOne("com.group.employeeLeave.EmployeeLeaveMapper.selectById", id);
			System.out.println(empLeave);
			return empLeave;
		} catch (Exception e) {
			e.printStackTrace();
			throw new FindException(e.getMessage());	
		} finally {
			session.close();
		}
	}

	public void update(Employee emp) throws ModifyException {
		SqlSession session = null;
		try {
			session = sessionFactory.openSession();	
			session.update("com.group.employee.EmployeeLeaveMapper.update",emp);
			session.commit();
		} catch (Exception e) {
			e.printStackTrace();
			throw new ModifyException(e.getMessage() + emp.getName()+"의 정보를 변경할 수 없습니다");
			
		} finally {
			session.close();
		}
	}
	

}
