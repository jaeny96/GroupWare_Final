package com.admin.empmanage.service;

import static org.junit.jupiter.api.Assertions.*;

import java.sql.Timestamp;

import org.apache.log4j.Logger;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.web.WebAppConfiguration;

import com.group.employee.dto.Department;
import com.group.employee.dto.Employee;
import com.group.employee.dto.Job;
import com.group.employee.dto.Position;
import com.group.exception.AddException;
import com.group.exception.UpdateException;
@WebAppConfiguration
@ExtendWith(SpringExtension.class)

@ContextConfiguration(locations = { "file:src/main/webapp/WEB-INF/spring/root-context.xml",
      "file:src/main/webapp/WEB-INF/spring/appServlet/servlet-context.xml" })

class EmployeeManageServiceTest {

	private Logger log = Logger.getLogger(EmployeeManageServiceTest.class.getName());

	@Autowired
	private EmployeeManageService service;
	//@Test
	public void addTest() throws AddException {
		Employee emp = new Employee();
		Department dep = new Department();
		dep.setDepartmentId("CEO");
		Job job = new Job();
		job.setJobId("Op");
		Position p = new Position();
		p.setPositionId(1);
		
		emp.setEmployeeId("CEO003");
		emp.setName("테스트");
		emp.setDepartment(dep);
		emp.setJob(job);
		emp.setPosition(p);
		emp.setPhoneNumber("010-1111-1112");
		emp.setEmail("이메일");
		emp.setHireDate(new Timestamp(0));
		System.out.println(new Timestamp(0));
		emp.setenabled(1);
		emp.setPassword("a");
		
		service.addEmp(emp);
		
	}
	
	@Test
	public void updateTest() throws UpdateException{
		Employee emp = new Employee();
		Department dep = new Department();
		dep.setDepartmentId("DEV");
		Job job = new Job();
		job.setJobId("Op");
		Position p = new Position();
		p.setPositionId(2);
		
		emp.setEmployeeId("CEO003");
		log.error(job);
		emp.setName("업데이트");
		emp.setDepartment(dep);
		emp.setJob(job);
		emp.setPosition(p);
		
		emp.setPhoneNumber("010-1111-1234");
		emp.setEmail("이메일");
		emp.setHireDate(new Timestamp(0));
		//System.out.println(new Timestamp(0));
		emp.setenabled(1);
		emp.setPassword("a");
		log.error("emp"+emp);
		
		service.modifyEmp(emp);

	}
	

}
