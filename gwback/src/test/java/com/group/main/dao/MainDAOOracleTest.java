package com.group.main.dao;

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


@WebAppConfiguration
@ExtendWith(SpringExtension.class)
@ContextConfiguration(locations = { "file:src/main/webapp/WEB-INF/spring/root-context.xml",
      "file:src/main/webapp/WEB-INF/spring/appServlet/servlet-context.xml" })

class MainDAOOracleTest {
	@Autowired
	private MainDAO dao;
	
	private Logger log = Logger.getLogger(MainDAOOracleTest.class.getName());
	@Test
	void loginTest() throws FindException {
		String id = "CEO001";
		Employee emp = dao.selectById(id);
		
		assertEquals("권보아", emp.getName());
	}

}
