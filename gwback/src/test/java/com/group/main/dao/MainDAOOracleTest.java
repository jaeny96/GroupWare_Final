package com.group.main.dao;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.group.employee.dto.Employee;
import com.group.employee.dto.Leave;
import com.group.exception.FindException;

@ExtendWith(SpringExtension.class)

@ContextConfiguration(locations = { "file:src/main/webapp/WEB-INF/spring/root-context.xml",
		"file:src/main/webapp/WEB-INF/spring/appServlet/servlet-context.xml" })
class MainDAOOracleTest {
	@Autowired
	MainDAO dao;
	
	@Test
	void selectByIdTest() throws FindException{
		Employee emp = dao.selectById("MSD002");
		String expectedPwd = "MSD0021234";
		assertEquals(expectedPwd, emp.getPassword());
	}
	
	@Test
	void selectLeaveTest() throws FindException{
		Leave leave = dao.selectLeave("MSD002");
		int expectedRemainDays = 10;
		assertEquals(expectedRemainDays, leave.getRemainDays());
	}

}
