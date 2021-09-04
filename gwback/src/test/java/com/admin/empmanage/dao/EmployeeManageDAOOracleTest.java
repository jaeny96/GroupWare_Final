package com.admin.empmanage.dao;

import static org.junit.jupiter.api.Assertions.*;

import java.sql.Timestamp;
import java.util.List;

import org.apache.log4j.Logger;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.web.WebAppConfiguration;

import com.group.employee.dao.EmployeeDAO;
import com.group.employee.dto.Department;
import com.group.employee.dto.Employee;
import com.group.employee.dto.Job;
import com.group.employee.dto.Position;
import com.group.exception.AddException;
import com.group.exception.FindException;
import com.group.exception.UpdateException;

@WebAppConfiguration
@ExtendWith(SpringExtension.class)

@ContextConfiguration(locations = { "file:src/main/webapp/WEB-INF/spring/root-context.xml",
      "file:src/main/webapp/WEB-INF/spring/appServlet/servlet-context.xml" })
class EmployeeManageDAOOracleTest {

	
	private Logger log = Logger.getLogger(EmployeeManageDAOOracleTest.class.getName());

	@Autowired
	private EmployeeManageDAOOracle dao;
	
	
//	@Test
	public void p() throws FindException{
		log.error(dao.findPosition());
		
	}
	
	//@Test
	public void j() throws FindException{
		log.error(dao.findJob());
	}
	
	//@Test
	public void d() throws FindException{
		log.error(dao.findDepartment());
	}
	
	
	//@Test
	public void addTest() throws AddException {
		Employee emp = new Employee();
		Department dep = new Department();
		dep.setDepartmentId("CEO");
		Job job = new Job();
		job.setJobId("Op");
		Position p = new Position();
		p.setPositionId(1);
		
		emp.setEmployeeId("CEO002");
		emp.setName("테스트");
		emp.setDepartment(dep);
		emp.setJob(job);
		emp.setPosition(p);
		emp.setPhoneNumber("010-1111-1111");
		emp.setEmail("이메일");
		emp.setHireDate(new Timestamp(0));
		System.out.println(new Timestamp(0));
		emp.setenabled(1);
		emp.setPassword("a");
		
		dao.addEmployee(emp);
		
	}
	
	@Test
	//문제 : 따로 하면 하나씩 수정은 되는데 한꺼번에 하면 수정이 안 됨
	//문제 2 : position.positionId!=null에서, 얘네가 null일 경우 null 에러가 뜸
	public void updateTest() throws UpdateException{
		Employee emp = new Employee();
		Department dep = new Department();
		dep.setDepartmentId("DEV");
		Job job = new Job();
		job.setJobId("자바개발");
		Position p = new Position();
		p.setPositionId(2);
		
		emp.setEmployeeId("CEO003");
		log.error(job);
		emp.setName("수정수정2");
		emp.setDepartment(dep);
		emp.setJob(job);
		emp.setPosition(p);
		
		emp.setPhoneNumber("010-1111-1112");
		emp.setEmail("이메일");
		emp.setHireDate(new Timestamp(0));
		//System.out.println(new Timestamp(0));
		emp.setenabled(1);
		emp.setPassword("a");
		log.error("emp"+emp);
		dao.updateEmployee(emp);

	}

}
