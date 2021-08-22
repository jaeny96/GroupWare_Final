package com.group.employee.dao;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import org.apache.log4j.Logger;
import org.junit.jupiter.api.Test;
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
class EmployeeDAOOracleTest {


	@Autowired
	private EmployeeDAO dao;
	
	private Logger log = Logger.getLogger(EmployeeDAOOracleTest.class.getName());
	
	//@Test
	void testSelectAll() throws FindException {
	
	
		List<Employee> emp = dao.selectAll();
	
		//예상하는 결괏값
		String empId = "CEO001";
		String expectedEmpName = "권보아";
		String expectedDep = "CEO";
		
		assertEquals(empId, emp.get(0).getEmployeeId());	
	}
	
	@Test
	void testSelectByDep() throws FindException {
		

		List<Employee> emp = dao.selectByDep("DEV");
	
		String empId="DEV001";
		String expectedEmpName = "임창균";
		assertEquals(expectedEmpName, emp.get(0).getName());	
	
	}	
	
	//@Test
	void selectByWord() throws FindException {
		
		
		List<Employee> emp = dao.selectByWord("보아");
	
		//예상하는 결괏값
		String empId = "CEO001";
		String expectedEmpName = "권보아";
		String expectedDep = "CEO";
		
		assertEquals(expectedEmpName, emp.get(0).getName());	
	}
	
	//@Test
//	void selectInfoTest() throws FindException {
//		
//		Employee emp = new Employee();
//		emp.setName("권보아");
//		emp.setEmployeeId("CEO001");
////		Employee e = dao.selectInfo(emp);
//	
//		//예상하는 결괏값
//		String empId = "CEO001";
//		String expectedEmpName = "권보아";
//		String expectedDep = "CEO";
//		
//		assertEquals(expectedEmpName, emp.getName());	
//	}
//	
	
}
