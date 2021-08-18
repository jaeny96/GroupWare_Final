package com.group.main.service;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.group.employee.dto.Employee;
import com.group.exception.FindException;

@ExtendWith(SpringExtension.class)

@ContextConfiguration(locations = { "file:src/main/webapp/WEB-INF/spring/root-context.xml",
		"file:src/main/webapp/WEB-INF/spring/appServlet/servlet-context.xml" })

class MainServiceTest {
	@Autowired
	MainService service;
	@Test
	void test() throws FindException{
		String id = "DEV001";
		Employee emp = service.showProfile(id);
		String expectedPwd = "1";
		assertEquals(expectedPwd, emp.getPassword());
	}

}
