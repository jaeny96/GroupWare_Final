package com.group.mypage.dao;

import static org.junit.jupiter.api.Assertions.*;

import org.apache.log4j.Logger;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.web.WebAppConfiguration;

import com.group.employee.dto.Employee;
import com.group.exception.FindException;
import com.group.exception.ModifyException;
import com.group.mypage.dto.EmployeeLeave;

@WebAppConfiguration
@ExtendWith(SpringExtension.class)

@ContextConfiguration(locations = { "file:src/main/webapp/WEB-INF/spring/root-context.xml",
      "file:src/main/webapp/WEB-INF/spring/appServlet/servlet-context.xml" })

class EmployeeLeaveDAOOracleTest {
	@Autowired
	private EmployeeLeaveDAO dao;
	
	private Logger log = Logger.getLogger(EmployeeLeaveDAOOracleTest.class.getName());
	
	//@Test
	void selectByIdTest() throws FindException {
		
		String id = "DEV001";
		EmployeeLeave empLeave= dao.selectById(id);
		
		String expectedName = "임창균";
		assertEquals(expectedName, empLeave.getEmployee().getName());
	}

		@Test
	   void updateTest() throws ModifyException{
	      String id = "CEO001";
	      String password = "ceo1234";
//	      String phoneNum="010-1234-1234";
	      
	      Employee emp = new Employee();
	      emp.setEmployeeId(id);
	      emp.setPassword(password);
//	      emp.setPhoneNumber(phoneNum);
	      dao.update(emp);
	   }

}
