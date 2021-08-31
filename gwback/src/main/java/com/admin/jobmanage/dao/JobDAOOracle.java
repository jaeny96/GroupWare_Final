package com.admin.jobmanage.dao;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.group.employee.dto.Job;
import com.group.exception.FindException;

@Repository("JobDAO")
public class JobDAOOracle implements JobDAO {

	@Autowired
	private SqlSessionFactory sessionFactory;
	
	
	@Override
	public List<Job> selectJobAll() throws FindException{
		SqlSession session = null;
		try {
			System.out.println("dao");
			session = sessionFactory.openSession();
			List<Job> jobList = session.selectList("com.group.employee.DepartmentMapper.selectJob");			
			return jobList;
		} catch (Exception e) {
			e.printStackTrace();
			throw new FindException(e.getMessage());	
		} finally {
			session.close();
		}
	}

}
