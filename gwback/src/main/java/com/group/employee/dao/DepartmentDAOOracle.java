package com.group.employee.dao;

import java.util.List;

import javax.sql.DataSource;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.group.employee.dto.Department;
import com.group.exception.FindException;
@Repository("DepartmentDAO")
public class DepartmentDAOOracle implements DepartmentDAO {
	
	@Autowired
	private DataSource ds;
	
	@Autowired
	private SqlSessionFactory sessionFactory;
	
	
	@Override
	public List<Department> selectDepAll() throws FindException{
		SqlSession session = null;
		try {
			session = sessionFactory.openSession();
			List<Department> depList = session.selectList("com.group.employee.DepartmentMapper.selectDep");			
			//session.commit();
			return depList;
		} catch (Exception e) {
			e.printStackTrace();
			throw new FindException(e.getMessage());	
		} finally {
			session.close();
		}
	}
}
