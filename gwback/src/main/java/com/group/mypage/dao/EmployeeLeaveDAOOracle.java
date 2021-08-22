package com.group.mypage.dao;
 
import javax.sql.DataSource;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.group.employee.dto.Department;
import com.group.employee.dto.Employee;
import com.group.employee.dto.Job;
import com.group.employee.dto.Leave;
import com.group.employee.dto.Position;
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
		Employee emp = new Employee();
			
		emp.setEmployeeId(id);
		try {
			session = sessionFactory.openSession();	
			EmployeeLeave empLeave = session.selectOne("com.group.employee.EmployeeLeaveMapper.selectById", emp);
			
//			Department d = new Department();
//			d.setDepartmentId(empLeave.getEmployee().getDepartment().getDepartmentId());
//			d.setDepartmentTitle(empLeave.getEmployee().getDepartment().getDepartmentTitle());
//			System.out.println(empLeave.getEmployee().getDepartment().getDepartmentId());
//			System.out.println(empLeave.getEmployee().getDepartment().getDepartmentTitle());
//			emp.setDepartment(d);
//			
//			Position p = new Position();
//			p.setPositionId(empLeave.getEmployee().getPosition().getPositionId());
//			p.setPositionTitle(empLeave.getEmployee().getPosition().getPositionTitle());
//			System.out.println(empLeave.getEmployee().getPosition().getPositionId());
//			System.out.println(empLeave.getEmployee().getPosition().getPositionTitle());
//			emp.setPosition(p);
//			
//			Job j = new Job();
//			j.setJobId(empLeave.getEmployee().getJob().getJobId());
//			j.setJobTitle(empLeave.getEmployee().getJob().getJobTitle());
//			System.out.println(empLeave.getEmployee().getJob().getJobId());
//			System.out.println(empLeave.getEmployee().getJob().getJobTitle());
//			emp.setJob(j);
//			
//			empLeave.setEmployee(emp);
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
			session.update("com.group.employee.EmployeeLeaveMapper.update", emp);
			System.out.println(emp.getEmployeeId()+"의 정보가 변경되었습니다.");
			session.commit();
		} catch (Exception e) {
			e.printStackTrace();
			throw new ModifyException(e.getMessage() + emp.getName()+"의 정보를 변경할 수 없습니다");
			
		} finally {
			session.close();
		}
	}
	

}
