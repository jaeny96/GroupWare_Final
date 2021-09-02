package com.admin.empmanage.dao;

import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.group.employee.dto.Department;
import com.group.employee.dto.Employee;
import com.group.employee.dto.Job;
import com.group.employee.dto.Position;
import com.group.exception.AddException;
import com.group.exception.FindException;
import com.group.exception.RemoveException;
import com.group.exception.UpdateException;

@Repository("EmployeeManageDAO")
public class EmployeeManageDAOOracle implements EmployeeManageDAO {
	
	@Autowired
	private DataSource ds;
	
	@Autowired
	private SqlSessionFactory sessionFactory;
	
	@Override
	//직원 추가
	public void addEmployee(Employee emp) throws AddException {	
		SqlSession session = null;
		try {
			session = sessionFactory.openSession();
			session.insert("com.admin.empmanage.EmployeeManageMapper.addEmployee", emp);
			session.commit();
			
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println(emp.getEmployeeId()+"의 정보를 추가할 수 없습니다.");
			throw new AddException(e.getMessage());	
		} finally {
			session.close();
		}
	}

	@Override
	//직원 삭제
	public void deleteEmployee(Employee emp) throws RemoveException {
		SqlSession session = null;
		try {
			session = sessionFactory.openSession();
			//session.delete 추가 
			//session.commit();
			
		} catch (Exception e) {
			e.printStackTrace();
			throw new RemoveException(e.getMessage());	
		} finally {
			session.close();
		}
	}

	@Override
	//직원 수정 
	public void updateEmployee(Employee emp) throws UpdateException {
		SqlSession session = null;
		try {
			session = sessionFactory.openSession();
			session.update("com.admin.empmanage.EmployeeManageMapper.updateEmployee",emp);
			session.commit();
			
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println(emp.getEmployeeId()+"의 정보를 수정할 수 없습니다.");
			throw new UpdateException(e.getMessage());
		} finally {
			session.close();
		}
	}

	@Override
	public List<Position> findPosition() throws FindException {
		List<Position> p = new ArrayList<Position>();
	//	Position p = new Position();
		SqlSession session = null;
		try {
			session = sessionFactory.openSession();
			p = session.selectList("com.admin.empmanage.EmployeeManageMapper.findPosition");
			return p;
		
		} catch (Exception e) {
			e.printStackTrace();
			throw new FindException(e.getMessage());
		} finally {
			session.close();
		}
	}

	@Override
	public List<Job> findJob() throws FindException {
		List<Job> p = new ArrayList<Job>();
		//	Position p = new Position();
			SqlSession session = null;
			try {
				session = sessionFactory.openSession();
				p = session.selectList("com.admin.empmanage.EmployeeManageMapper.findJob");
				return p;
			
			} catch (Exception e) {
				e.printStackTrace();
				throw new FindException(e.getMessage());
			} finally {
				session.close();
			}
	}

	@Override
	public List<Department> findDepartment() throws FindException {
		List<Department> p = new ArrayList<Department>();
		//	Position p = new Position();
			SqlSession session = null;
			try {
				session = sessionFactory.openSession();
				p = session.selectList("com.admin.empmanage.EmployeeManageMapper.findDepartment");
				return p;
			
			} catch (Exception e) {
				e.printStackTrace();
				throw new FindException(e.getMessage());
			} finally {
				session.close();
			}
	}

}
