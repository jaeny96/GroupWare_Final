package com.group.employee.dao;

import java.util.List;

import javax.sql.DataSource;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;


import com.group.employee.dto.Employee;
import com.group.exception.FindException;

@Repository("EmployeeDAO")
public class EmployeeDAOOracle implements EmployeeDAO {
	@Autowired
	private DataSource ds;
	
	@Autowired
	private SqlSessionFactory sessionFactory;
	
	
	@Override
	public List<Employee> selectAll() throws FindException{
		SqlSession session = null;
		try {
			session = sessionFactory.openSession();
			List<Employee> empList = session.selectList("com.group.employee.EmployeeMapper.selectAll");			
			//session.commit();
			return empList;
		} catch (Exception e) {
			e.printStackTrace();
			throw new FindException(e.getMessage());	
		} finally {
			session.close();
		}
		
	}

	@Override
	public List<Employee> selectByDep(String dep_id) throws FindException{
		SqlSession session = null;
		try {
			session = sessionFactory.openSession();
			List<Employee> empList = session.selectList("com.group.employee.EmployeeMapper.selectByDep", dep_id);			
		//	session.commit();
			return empList;
		} catch (Exception e) {
			e.printStackTrace();
			throw new FindException(e.getMessage());
		}finally {
			session.close();
		}

	}

	@Override
	public List<Employee> selectByWord(String word) throws FindException{
		SqlSession session = null;
		try {
			session = sessionFactory.openSession();
			List<Employee> empList = session.selectList("com.group.employee.EmployeeMapper.selectByWord", word);			
			//session.commit();
			return empList;
		} catch (Exception e) {
			e.printStackTrace();
			throw new FindException(e.getMessage());
		}finally {
			session.close();
		}
	}

	@Override
	public Employee selectInfo(String id) throws FindException{
		SqlSession session = null;
		try {
			session = sessionFactory.openSession();
			Employee empResult = session.selectOne("com.group.employee.EmployeeMapper.selectInfo", id);			
			//session.commit();
			return empResult;
		} catch (Exception e) {
			e.printStackTrace();
			throw new FindException(e.getMessage());
		}finally {
			session.close();
		}
	}
}
