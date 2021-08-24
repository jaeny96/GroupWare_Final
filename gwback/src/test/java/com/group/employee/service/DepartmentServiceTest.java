package com.group.employee.service;

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
import com.group.employee.service.DepartmentService;
import com.group.exception.FindException;

@WebAppConfiguration
@ExtendWith(SpringExtension.class)
@ContextConfiguration(locations = { "file:src/main/webapp/WEB-INF/spring/root-context.xml",
      "file:src/main/webapp/WEB-INF/spring/appServlet/servlet-context.xml" })
class DepartmentServiceTest {

	@Autowired
	private DepartmentService service;
	
	private Logger log = Logger.getLogger(DepartmentServiceTest.class.getName());

	@Test
	void selectAllDep() throws FindException {
	List<Department> list = service.showDept();
				
	String dep = "DEV";
	assertEquals(dep, list.get(2).getDepartmentId());
	}
}
