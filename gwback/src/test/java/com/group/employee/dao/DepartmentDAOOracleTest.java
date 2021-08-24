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

import com.group.employee.dto.Department;
import com.group.exception.FindException;
@WebAppConfiguration
@ExtendWith(SpringExtension.class)

@ContextConfiguration(locations = { "file:src/main/webapp/WEB-INF/spring/root-context.xml",
      "file:src/main/webapp/WEB-INF/spring/appServlet/servlet-context.xml" })

class DepartmentDAOOracleTest {

	@Autowired
	private DepartmentDAO dao;
	
	private Logger log = Logger.getLogger(DepartmentDAOOracleTest.class.getName());

	@Test
	void selectAllDep() throws FindException {
		List<Department> list = dao.selectDepAll();
		
	String dep = "DEV";
	assertEquals(dep, list.get(2).getDepartmentId());
	}
}
