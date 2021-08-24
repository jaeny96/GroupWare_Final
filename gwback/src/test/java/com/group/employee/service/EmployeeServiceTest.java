package com.group.employee.service;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;


import java.util.List;

import org.apache.log4j.Logger;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.web.WebAppConfiguration;

import com.group.employee.dto.Employee;
import com.group.exception.FindException;
@WebAppConfiguration
@ExtendWith(SpringExtension.class)

@ContextConfiguration(locations = { "file:src/main/webapp/WEB-INF/spring/root-context.xml",
      "file:src/main/webapp/WEB-INF/spring/appServlet/servlet-context.xml" })
class EmployeeServiceTest {

	@Autowired
	private EmployeeService service;
	
	private Logger log = Logger.getLogger(EmployeeServiceTest.class.getName());
	
	
	//@Test
	void selectAllServiceTest() throws FindException {
		
		List<Employee> emp = service.showAll();
		String empId = "CEO001";
		assertEquals(empId, emp.get(0).getEmployeeId());
	}

	//@Test
	void showByDeptServiceTest() throws FindException {
		String dept = "CEO";
		List<Employee> emp = service.showByDept(dept);
		
		String expectedEmpName = "권보아";
		assertEquals(expectedEmpName, emp.get(0).getName());	
	}
	
	//@Test
	void searchEmpServiceTest() throws FindException {
		List<Employee> emp = service.searchEmp("보아");
		
		String expectedEmpName = "권보아";
		assertEquals(expectedEmpName, emp.get(0).getName());	
	}

	@Test
	void showDetailServiceTest() throws FindException{
		Employee emp = new Employee();
		emp.setName("권보아");
		emp.setEmployeeId("CEO001");

		Employee e  =service.showDetail(emp);
		
		String expectedEmpName = "권보아";
		assertEquals(expectedEmpName, emp.getName());	
	}
}
